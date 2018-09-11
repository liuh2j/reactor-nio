/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package mutiplethread.server;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: Acceptor.java, v 0.1 2018-09-11 下午2:36 liuzhigang04 $$
 */
public class Acceptor implements Runnable {

    private WorkerThreadGroup workerThreadGroup;
    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        workerThreadGroup = new WorkerThreadGroup();
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        Set<SelectionKey> ops = null;
        while (true) {
            try {
                selector.select(); //阻塞等待
                ops = selector.selectedKeys();
            } catch (Throwable e) {
                e.printStackTrace();
                break;
            }

            for (Iterator<SelectionKey> it = ops.iterator(); it.hasNext();) {
                SelectionKey key = it.next();
                it.remove();

                try {
                    if (key.isAcceptable()) {
                        System.out.println("收到客户端的连接请求。。。");
                        ServerSocketChannel serverSc = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverSc.accept();
                        clientChannel.configureBlocking(false);
                        workerThreadGroup.dispatch(clientChannel);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}