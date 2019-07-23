package com.freecode.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-07-23 16:38
 */
public class NetUtil {
    public static String hostName() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostName();
        } catch (Exception e) {
            // TODO: 2019/7/23
            System.out.println(e);
        }
        return "";
    }

    public static String ipAddr() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        return address.getHostAddress();
    }

}
