/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package singlethread.server;

import reactor.AbstractServer;

import java.io.IOException;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: NioServer.java, v 0.1 2018-09-06 上午10:34 liuzhigang04 $$
 */
public class NioServer extends AbstractServer {

    public NioServer(int port) {
        super(port);
    }

    @Override
    public void start() {
        new Reactor(selector, serverSocketChannel).run();
    }

    @Override
    public void close() {
        super.close();
    }
}