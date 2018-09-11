/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package singlethread.server;

import reactor.Handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 * <p>
 *     1、Reactor响应I/O事件，分发到合适的Handler处理。
       2、Handler执行非阻塞的动作。
    </p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: Reactor.java, v 0.1 2018-09-06 上午9:23 liuzhigang04 $$
 */
public class Reactor extends Handler implements Runnable {
    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    public Reactor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        Set<SelectionKey> ops = null;
        while (true) {
            try {
                selector.select(); //如果没有感兴趣的事件到达，阻塞等待
                ops = selector.selectedKeys();
            } catch (Throwable e) {
                System.out.println("出错了！");
                e.printStackTrace();
                break;
            }
            super.handler(ops, selector);
        }
    }
}