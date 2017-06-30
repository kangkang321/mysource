package com.neusoft.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.slf4j.MDC;

/**
 * 类TraceUtils.java的实现描述：日誌有關
 * 
 * @author Administrator 2017年6月14日 下午4:55:46
 */
public class TraceUtils {

    public TraceUtils(){
    }

    public static void beginTrace() {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);
        try {
            MDC.put("computer", InetAddress.getLocalHost().toString());
        } catch (IllegalArgumentException | UnknownHostException e) {
            e.printStackTrace();
        }
        MDC.put("version", Version.getVersion());

    }

    public static void beginTrace(String traceId) {
        MDC.put("traceId", traceId);
    }

    public static void endTrace() {
        MDC.remove("traceId");
    }

    public static String getTraceIdKey() {
        return (String) MDC.get("traceId");
    }

    public static final String TRACE_KEY = "traceId";
}
