/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package singlethread.client;

import reactor.AbstractClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: NioClient.java, v 0.1 2018-09-06 上午10:01 liuzhigang04 $$
 */
public class NioClient extends AbstractClient {
    public NioClient(String ip, int port) {

        super(ip, port);
    }

    @Override
    public void start() {
        new ClientHandler(selector).process();
    }

    @Override
    public void close() {
        try {
            if (selector.isOpen()) {
                selector.close();
            }

            if (clientChannel.isOpen()) {
                clientChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketChannel getChannel() {
        return clientChannel;
    }

    public Selector getSelector() {
        return selector;
    }
}