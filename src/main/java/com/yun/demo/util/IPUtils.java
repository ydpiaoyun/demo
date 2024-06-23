package com.yun.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP地址
 *
 * @author 
 */
public class IPUtils {
	private static Logger logger = LoggerFactory.getLogger(IPUtils.class);
    private final static String UNKNOWN_STR = "unknown";

	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("X-Forwarded-For");
            if (isEmptyIP(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (isEmptyIP(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (isEmptyIP(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (isEmptyIP(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (isEmptyIP(ip)) {
                    ip = request.getRemoteAddr();
                    if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                        // 根据网卡取本机配置的IP
                        ip = getLocalAddr();
                    };
                }
            }else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = ips[index];
                    if (!isEmptyIP(ip)) {
                        ip = strIp;
                        break;
                    }
                }
            }
        } catch (Exception e) {
        	logger.error("IPUtils ERROR ", e);
        }
        return ip;
    }

    private static boolean isEmptyIP(String ip) {
        if (StringUtils.isEmpty(ip) || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }

    /**
     * 获取本机的IP地址
     */
    public static String getLocalAddr() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("InetAddress.getLocalHost()-error", e);
        }
        return "";
    }
	
}