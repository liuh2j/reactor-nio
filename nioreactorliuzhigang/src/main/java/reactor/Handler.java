/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package reactor;

import java.nio.ByteBuffer;
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
 * @version $Id: Handler.java, v 0.1 2018-09-11 下午3:42 liuzhigang04 $$
 */
public abstract class Handler {

    public void handler(Set<SelectionKey> ops, Selector selector) {
        //处理相关事件
        for (Iterator<SelectionKey> it = ops.iterator(); it.hasNext(); ) {
            SelectionKey key = it.next();
            it.remove();

            try {
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSc = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = serverSc.accept();
                    clientChannel.configureBlocking(false);
                    //向选择器注册读事件，客户端向服务端发送数据准备好后，再处理
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("收到客户端的连接请求。。。");
                } else if (key.isWritable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buf = (ByteBuffer) key.attachment();
                    buf.flip();
                    clientChannel.write(buf);
                    //重新注册读事件
                    clientChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int count = clientChannel.read(buf);
                    if (count > 0) {
                        buf.flip();
                        byte[] response = new byte[buf.remaining()];
                        buf.get(response);
                        System.out.println("接收到客户端的请求内容为：" + new String(response));
                        buf.flip();
                    }
                    clientChannel.register(selector, SelectionKey.OP_WRITE, buf);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}