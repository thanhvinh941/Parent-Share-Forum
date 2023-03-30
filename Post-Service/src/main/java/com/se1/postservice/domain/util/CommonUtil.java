package com.se1.postservice.domain.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import com.se1.postservice.common.SCMConstant;

public class CommonUtil {

	public static final SimpleDateFormat dateFormatYYYYMMDDHHMMSS = new SimpleDateFormat(SCMConstant.DATE_YYYYMMDD_HHMMSS);
	
	public static String camelToSnake(String str) {
		String result = "";

		char c = str.charAt(0);
		result = result + Character.toLowerCase(c);

		for (int i = 1; i < str.length(); i++) {

			char ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				result = result + '_';
				result = result + Character.toLowerCase(ch);
			}

			else {
				result = result + ch;
			}
		}

		return result;
	}

	public static String convertObjectToValueSql(Object value) {
		String valueStr = "";
		if (Objects.isNull(value)) {
			return valueStr;
		} else if (value instanceof Integer || value instanceof Long) {
			valueStr = String.format("%d", value);
		} else if (value instanceof String) {
			valueStr = String.format("'%s'", value);
		} else if (value instanceof Date) {
			valueStr = String.format("'%s'", dateFormatYYYYMMDDHHMMSS.format(value));
		} else if (value instanceof Boolean) {
			Integer booleanValue = value.equals(new Boolean(true)) ? 1 : 0;
			valueStr = String.format("%d", booleanValue);
		}
		
		return valueStr;
	}
}
