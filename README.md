### 机器配置
```text
型号名称：	MacBook Pro
型号标识符：	MacBookPro11,4
处理器名称：	Intel Core i7
处理器速度：	2.2 GHz
处理器数目：	1
核总数：	4
L2 缓存（每个核）：	256 KB
L3 缓存：	6 MB
内存：	16 GB
```

### 压测结果
```text
reactor单线程模式qps:296487.498 ± 2961.500  ops/s
reactor多线程模式qps:274761.854 ± 2758.986  ops/s
```


##### 10个线程：
```text
reactor单线程模式qps:446154.189 ± 16518.704  ops/s
reactor多线程模式qps:467435.142 ± 17423.643  ops/s
```


##### 20个线程
```text
reactor单线程模式qps:1483524.205 ± 39975.911  ops/s
reactor多线程模式qps:1577175.375 ± 43209.928  ops/s
```

### 总结
```text
1、如果只有一个客户端连接，那么reactor单线程模式的qps比较高,处理能力也比较高；
2、如果只有多个客户端连接，那么reactor多线程模式的qps比较高,相应并发处理能力也比较高；
```
