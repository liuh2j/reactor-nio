/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: AbstractServer.java, v 0.1 2018-09-06 上午10:34 liuzhigang04 $$
 */
public abstract class AbstractServer implements Endpoint {
    protected String ip;

    protected int port;

    //reactor中selector
    protected Selector selector;

    //用来接收客户端连接
    protected ServerSocketChannel serverSocketChannel;

    public AbstractServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        init();
        start();
    }

    public AbstractServer(int port) {
        this.port = port;
        init();
        start();
    }

    private void init() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (selector.isOpen()) {
                selector.close();
            }

            if (serverSocketChannel.isOpen()) {
                serverSocketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}