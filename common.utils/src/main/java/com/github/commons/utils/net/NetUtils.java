package com.github.commons.utils.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetUtils {
	private static final Logger log = LoggerFactory.getLogger(NetUtils.class);

	private static final String COLON = ":";
	private static final String SEPARATIVE = "_";

	/**
	 * @return 本机IP
	 * @throws java.net.SocketException
	 */
	public static String getIPAddress() {
		String localip = StringUtils.EMPTY;// 本地IP，如果没有配置外网IP则返回它
		String netip = StringUtils.EMPTY;// 外网IP

		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			log.error("Get Ip exception.", e);
			return StringUtils.EMPTY;
		}
		if (netInterfaces != null) {
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(COLON) == -1) {// 外网IP
						netip = ip.getHostAddress();
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(COLON) == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
		}
		if (StringUtils.isNotBlank(netip)) {
			return netip;
		} else {
			return localip;
		}
	}
}
