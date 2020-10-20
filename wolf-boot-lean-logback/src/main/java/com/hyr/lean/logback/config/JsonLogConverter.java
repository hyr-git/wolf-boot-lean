package com.hyr.lean.logback.config;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @auther anumbrella
 */

public class JsonLogConverter extends ClassicConverter {

    private JSONObject object = new JSONObject();

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        try {
			object.put("msg",iLoggingEvent.getMessage());
			object.put("level", iLoggingEvent.getLevel().levelStr);
	        object.put("threadName", iLoggingEvent.getThreadName());
	        object.put("method", iLoggingEvent.getLoggerName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return object.toString();
    }
}