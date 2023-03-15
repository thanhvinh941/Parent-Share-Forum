package com.se1.systemservice.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.se1.systemservice.common.utils.CommonUtils;
import com.se1.systemservice.payload.ChatRequest;
import com.se1.systemservice.service.CommonService;
import com.se1.systemservice.service.UserContactService;
import com.se1.systemservice.service.WebsocketService;

@Controller
@RequestMapping("/system/ws")
public class WebsocketController {

	@Autowired
	private WebsocketService websocketService;

	@Autowired
	private UserContactService contactService;

	@Autowired
	private CommonService commonService;

	@MessageMapping("/chat/{topicId}")
	public void sendChat(@RequestHeader(required = false, name = "user_detail") String userDetail,
			@DestinationVariable String topicId, ChatRequest request) throws Exception {
		String content = request.getContent();

		if (request.isFile()) {
			String[] contentArray = content.split(",");

			String imageName = CommonUtils.getFileName(contentArray[0].split("/")[1]);
			byte[] imageByte = Base64.getDecoder().decode(contentArray[1].trim());
			InputStream inputStream = new ByteArrayInputStream(imageByte);

			commonService.saveFile("/chat", imageName, inputStream);
		}

		websocketService.sendMessageChat(topicId, request);
	}

	@MessageMapping("/comment/{topicId}")
	public void sendComment() {

	}

	@MessageMapping("/contact/{topicId}")
	public void sendContact(@DestinationVariable String topicId, ChatRequest request) {
		websocketService.sendContact(topicId, request);
	}

	@MessageMapping("/user/{topicId}")
	public void sendUser(@DestinationVariable String topicId, ChatRequest request) {

		websocketService.sendContact(topicId, request);
	}

	@MessageMapping("/userContact/{userId}")
	public void updateUserContact(@DestinationVariable Integer userId, String action) {
		boolean isChoose = action.equals("CONNECT");
		System.out.println(userId + "/" + action);
		contactService.updateUserChoose(userId, isChoose);
	}
	@MessageMapping("/user/54f2e54b-f4fb-4f1e-8358-624a2f4017d7")
	public void sendPrivateUser(@Payload ChatRequest request) {
		System.out.println(request);
		websocketService.sendMessageChat("54f2e54b-f4fb-4f1e-8358-624a2f4017d7", request);
	}
}
