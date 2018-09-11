/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package server;

import mutiplethread.server.NioServer;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: MutipleThreadServer.java, v 0.1 2018-09-11 下午3:36 liuzhigang04 $$
 */
public class MutipleThreadServer {
    public static void main(String[] args) {
        new NioServer(8010).start();
    }
}