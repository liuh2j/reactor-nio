package reactor; /**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: reactor.Endpoint.java, v 0.1 2018-09-06 上午10:19 liuzhigang04 $$
 */
public interface Endpoint {
    /**
     * 启动
     */
    void start();

    /**
     * 关闭
     */
    void close();
}