/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package mutiplethread.server;

import reactor.Handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: WorkerThread.java, v 0.1 2018-09-11 下午2:42 liuzhigang04 $$
 */
public class WorkerThread extends Handler implements Runnable {

    private Selector selector;

    private List<SocketChannel> registerList = new ArrayList<SocketChannel>();

    private ReentrantLock object = new ReentrantLock();

    public WorkerThread() {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(SocketChannel socketChannel) {
        if(socketChannel != null ) {
            try {
                object.lock();
                registerList.add(socketChannel);
            } finally {
                object.unlock();
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            Set<SelectionKey> ops = null;
            try {
                selector.select(1500);
                ops = selector.selectedKeys();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            super.handler(ops, selector);
            registEvent();
        }
    }


    private void registEvent() {
        //注册事件
        if(!registerList.isEmpty()) {
            try {
                object.lock();
                for (Iterator<SocketChannel> it = registerList.iterator(); it.hasNext();) {
                    SocketChannel sc = it.next();
                    try {
                        sc.register(selector, SelectionKey.OP_READ);
                    } catch(Throwable e) {
                        e.printStackTrace();//ignore
                    }
                    it.remove();
                }

            } finally {
                object.unlock();
            }
        }
    }
}