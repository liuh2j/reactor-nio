/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package server;


import singlethread.server.NioServer;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: SingleThreadServer.java, v 0.1 2018-09-11 下午3:35 liuzhigang04 $$
 */
public class SingleThreadServer {
    public static void main(String[] args) {
        new NioServer(8090).start();
    }
}