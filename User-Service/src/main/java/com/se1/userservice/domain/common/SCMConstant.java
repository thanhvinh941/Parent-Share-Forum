package com.se1.userservice.domain.common;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.se1.userservice.domain.model.AuthorizationItem;

public interface SCMConstant {

	public static final String CONTACT_REQUEST = "request";
	public static final String CONTACT_FRIEND = "friend";
	public static final String CONTACT_UNFRIEND = "unfriend";
	public static final String Notification_QUEUE = "Notification-Queue";
	public static final String Notification_EXCHANGE = "Notification-Exchange";
	public static final String Notification_ROUTING_KEY = "Notification-Routing-Key";
	public static final String DATE_YYYYMMDD_HHMMSS = "yyyy-MM-dd hh:mm:ss";

	// RABBIT ACTION
	public static final String SYSTEM_CONTACT = "contact";
	public static final String USER_UPDATE_STATUS = "update-status";

	public static final Byte VALID_FLG = 1;
	public static final Byte STATUS_OFF = 1;
	public static final Byte STATUS_ON = 2;
	public static final Byte DEL_FLG_OFF = 0;

	// ControllerName
	public static final String TOPIC_TAG_CONTROLLER = "TopicTag";
	public static final String USER_CONTROLLER = "User";
	public static final String GROUP_ROLE_CONTROLLER = "Group";
	public static final String POST_CONTROLLER = "Post";
	public static final String LOG_USER_CONTROLLER = "LogUser";

	public static Map<Integer, String> getAllAuthorItem() {
		List<AuthorizationItem> authorizationItems = List.of(AuthorizationItem.values());
		return authorizationItems.stream().collect(Collectors.toMap(AuthorizationItem::getId, AuthorizationItem::name));
	}

	public static Map<Integer, String> getAllAuthorItemByIds(List<String> ids) {
		List<AuthorizationItem> authorizationItems = List.of(AuthorizationItem.values());
		return authorizationItems.stream().filter(author -> {
			var id = author.getId().toString();
			boolean isValid = ids.contains(id);
			return isValid;
		}).collect(Collectors.toMap(AuthorizationItem::getId, AuthorizationItem::name));
	}
}
