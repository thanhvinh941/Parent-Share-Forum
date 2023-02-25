var stompClient = null;



function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect(userId) {
	var user = null;
	
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, async function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.send("/app/userContact/" + userId , {} , "CONNECT")
        
        console.log("user after call api :" +user);
       	await $.get("http://localhost:8080/system/user-contact/" + userId, function (data, status) {
			console.log(data);
			user = data;
		})
		console.log("user befor call api: "+user);
        stompClient.subscribe('/topic/chat/54f2e54b-f4fb-4f1e-8358-624a2f4017d7' , function (greeting) {
            showGreeting(message)
        });
		
    });
}

function userConnect(userId){
    userContacts = userContacts.forEach(x=> {
        if(x.userId == userId){
            x.status = 1;
            x.isChoose = true;
        }
    })
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/user/54f2e54b-f4fb-4f1e-8358-624a2f4017d7", {}, JSON.stringify({'userId': '1', 'content': $("#message").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect($("#userContact").val()); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});