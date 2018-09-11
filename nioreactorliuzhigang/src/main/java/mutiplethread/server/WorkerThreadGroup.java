/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package mutiplethread.server;

import constants.ThreadConstants;

import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: WorkerThreadGroup.java, v 0.1 2018-09-11 下午2:48 liuzhigang04 $$
 */
public class WorkerThreadGroup {
    //负载均衡
    private static final AtomicInteger request = new AtomicInteger();
    private WorkerThread[] workerThreads;

    public WorkerThreadGroup() {
        this(ThreadConstants.DEFAULT_WORKER_THREAD_COUNT);
    }

    public WorkerThreadGroup(int threadCount) {
        workerThreads = new WorkerThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            workerThreads[i] = new WorkerThread();
            new Thread(workerThreads[i]).start();
        }
    }

    public void dispatch(SocketChannel socketChannel) {
        if(socketChannel != null ) {
            this.workerThreads[ request.getAndIncrement() %  workerThreads.length].register(socketChannel);
        }
    }
}