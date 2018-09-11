/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package presstest;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import singlethread.client.NioClient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: SingleThread.java, v 0.1 2018-09-06 下午4:00 liuzhigang04 $$
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10000, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@Threads(20)
public class SingleThread {
    private static SocketChannel clientChannel;
    private static Selector selector;
    private static NioClient nioClient;

    @Setup
    public void init() {
        nioClient = new NioClient("127.0.0.1", 8090);
        clientChannel = nioClient.getChannel();
        selector = nioClient.getSelector();
        try {
            clientChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    public void test() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("java nio".getBytes());
            buffer.flip();
            clientChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TearDown
    public void destroy() {
        System.out.println("开始关闭chanel!");
        nioClient.close();
    }
}