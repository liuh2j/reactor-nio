/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package singlethread.client;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: ClientHandler.java, v 0.1 2018-09-06 上午10:11 liuzhigang04 $$
 */
public class ClientHandler implements Runnable {

    private final Selector selector;

    public ClientHandler(Selector selector) {
        this.selector = selector;
    }

    public void run() {
        process();
    }

    public void process() {
        while(true) {
            try {
                selector.select();
                Set<SelectionKey> ops = selector.selectedKeys();
                for (Iterator<SelectionKey> it = ops.iterator(); it.hasNext();) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isConnectable()) {
                        System.out.println("client connect");
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 判断此通道上是否正在进行连接操作。
                        // 完成套接字通道的连接过程。
                        if (sc.isConnectionPending()) {
                            if ( sc.finishConnect()) {
                                System.out.println("完成连接!");
                                return;
//                                sc.register(selector, SelectionKey.OP_READ);
//                                ByteBuffer buffer = ByteBuffer.allocate(1024);
//                                buffer.put("Hello,Server".getBytes());
//                                buffer.flip();
//                                sc.write(buffer);
                            } else {
                                //连接失败，进程退出
                                System.exit(1);
                            }                        }
                    } else if (key.isWritable()) {
                        System.out.println("客户端写");
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.put("hello server.".getBytes());
                        buffer.flip();
                        sc.write(buffer);
                    } else if (key.isReadable()) {
                        System.out.println("1");
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int count = sc.read(buffer);
                        if (count > 0) {
                            buffer.flip();
                            byte[] response = new byte[buffer.remaining()];
                            buffer.get(response);
                            System.out.println(new String(response));
                        }
                    }
                }
            } catch(Throwable e) {
                e.printStackTrace();
            }
        }
    }
}