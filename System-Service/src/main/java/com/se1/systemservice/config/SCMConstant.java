package com.se1.systemservice.config;

public interface SCMConstant {

	public static final String CONTACT_REQUEST = "request";
	public static final String CONTACT_FRIEND = "friend";
	public static final String CONTACT_UNFRIEND = "unfriend";
	public static final String Notification_QUEUE = "Notification-Queue";
	public static final String Notification_EXCHANGE = "Notification-Exchange";
	public static final String Notification_ROUTING_KEY = "Notification-Routing-Key";
	public static final String MQ_REQUEST_CHAT_ACTION_CREATE = "create";

	// RABBIT ACTION
	public static final String SYSTEM_CONTACT = "contact";
	public static final String SYSTEM_CHAT = "chat";
	public static final String SYSTEM_CHAT_STATUS = "chat-status";
	public static final String SYSTEM_NOTIFY = "notify";
	public static final String USER_UPDATE_STATUS = "update-status";

	// Notify action
	public static final String SUBSCRIBER = "subscribed";
	public static final String POST = "post";
	public static final String COMMENT = "comment";
	
	// USER TOPIC ACTION
	public static final String CHAT = "new-message";
	
	public static String getContactActionByStatus(int status) {
		String action = "";

		switch (status) {
		case 1:
			action = "request-friend";
			break;
		case 2:
			action = "accept-friend";
			break;
		default:
			break;
		}

		return action;
	}
	
}
