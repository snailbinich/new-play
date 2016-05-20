package com.izhaoyan.playground;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by zhaoyan on 2016/5/20.
 */
public class Test {
    public static void main(String[] args) {

    }

    public void testGetServerIp() throws UnknownHostException {
        InetAddress address = getServerIp("ks3-cn-beijing.ksyun.com");
//        InetAddress address = getServerIp("www.baidu.com");
        System.out.println(address.getHostAddress().toString());
    }

    public static InetAddress getServerIp(String host) throws UnknownHostException {
        return InetAddress.getByName(host);
    }
}
