package com.sinog2c.util.common;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	public static void main(String[] args) {
		String localIP = getLocalIP();
		System.out.println("localIP="+localIP);
		//
		String osName = getOSName();
		System.out.println("osName="+osName);
		if (osName.contains("win") || osName.contains("Win")) {
			System.out.println(true);
		}
		
		long n = 30*24*60*60*1000;
		long nl = 30*24*60*60*1000L;
		long nl2 = 30*24*60*608*1000*1000L;
		System.out.println("n="+n);
		System.out.println("nl="+nl);
		System.out.println("nl2="+nl2);
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.MONTH, 1); // 加一个月
		cal.add(Calendar.DATE, -1); // 减一天
		//
		Date nextM = cal.getTime();
		System.out.println(nextM);
	}
	
	public static String getClientIP(HttpServletRequest request){
		String ip = "";
		if(null != ip){
			//
			ip = request.getRemoteAddr();
		}
//		String localhostip = request.getRemoteAddr();
//		if (request.getHeader("x-forwarded-for") != null) { 
//			localhostip = request.getHeader("x-forwarded-for");
//	    } 
		//
		return ip;
	}
	
	/**
	 * 获取客户端的主机名
	 * @param request
	 * @return
	 */
	public static String getClientHost(HttpServletRequest request){
		String clientHostName = request.getRemoteHost();
		return clientHostName;
	}
	
	public static String getWinClientIP(HttpServletRequest request){
		String ip = "";
		if(null != ip){
			//
			ip = request.getRemoteAddr();
		}
		
		if (request.getHeader("x-forwarded-for") != null) { 
			ip = request.getHeader("x-forwarded-for");
	    } 
		//
		return ip;
	}

	public static String getLocalIP() {
		if (isWin()) {
			return getWinIP();
		} else {
			return getLinuxIP();
		}
	}

	// 未验证
	public static String getLinuxIP() {
		String ip = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();

			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			//
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				System.out.println(netInterface.getName());
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					addr = (InetAddress) addresses.nextElement();
					//  是IPV4地址
					if (addr != null && addr instanceof Inet4Address) {
						ip = addr.getHostAddress();
						return ip;
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		//
		//
		return ip;
	}

	/**
	 * 获取 windows 的IP
	 * 
	 * @return
	 */
	public static String getWinIP() {
		String ip = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			//
			ip = addr.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//
		return ip;
	}

	/**
	 * 判断是否是windows系统
	 * 
	 * @return
	 */
	public static boolean isWin() {
		String osName = getOSName();
		return isWin(osName);
	}

	public static boolean isWin(String osName) {
		if (null == osName || osName.isEmpty()) {
			return false;
		}
		//
		//if (osName.matches("win|Win|Windows|windows")) {
		if (osName.contains("win") || osName.contains("Win")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取操作系统名
	 * 
	 * @return
	 */
	public static String getOSName() {
		Properties properties = System.getProperties();
		//
		String osName = properties.getProperty("os.name");
		//
		return osName;
	}
}
