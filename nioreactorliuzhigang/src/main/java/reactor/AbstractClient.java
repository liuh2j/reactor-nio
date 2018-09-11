package reactor; /**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: reactor.AbstractClient.java, v 0.1 2018-09-06 上午10:19 liuzhigang04 $$
 */
public abstract class AbstractClient implements Endpoint {
    protected SocketChannel clientChannel;
    protected Selector selector;

    public AbstractClient(String ip, int port) {
        try {
            clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);

            selector = Selector.open();
            clientChannel.register(selector, SelectionKey.OP_CONNECT);
            clientChannel.connect(new InetSocketAddress(ip, port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }
}