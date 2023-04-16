package com.se1.userservice.domain.common;

public interface SCMConstant {

	public static final String CONTACT_REQUEST = "request";
	public static final String CONTACT_FRIEND = "friend";
	public static final String CONTACT_UNFRIEND = "unfriend";
	public static final String Notification_QUEUE = "Notification-Queue";
    public static final String Notification_EXCHANGE = "Notification-Exchange";
    public static final String Notification_ROUTING_KEY = "Notification-Routing-Key";
    
    //RABBIT ACTION
    public static final String SYSTEM_CONTACT = "contact";
    public static final String USER_UPDATE_STATUS = "update-status";
    
    public static final Byte VALID_FLG = 1;
}
