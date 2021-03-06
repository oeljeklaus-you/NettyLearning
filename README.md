# Netty in Action
## Netty-异步和事件驱动
 Netty 是一款异步的事件驱动的网络应用程序框架，支持快速地开发可维护的高性能的

 面向协议的服务器和客户端。
### Java网络编程
 一般的Java网络编程:
![一般的网络编程](img/一般的网络编程.png)
 ServerSocket上的accept()方法将会一直阻塞到一个连接建立，随后返回一个新的Socket用于客户端和服务器之间的通信。

 该ServerSocket将继续监听传入的连接。

 BufferedReader和PrintWriter都衍生自Socket的输入输出流。前者从一个

 字符输入流中读取文本，后者打印对象的格式化的表示到文本输出流。readLine()方法将会阻塞，直到在处一个由换行符或者回车符结尾的字符串被读取。

 客户端的请求已经被处理 。
 
 这段代码片段将只能同时处理一个连接，要管理多个并发客户端，需要为每个新的客户端
   
 Socket创建一个新的Thread。
 
 让我们考虑一下这种方案的影响。第一，在任何时候都可能有大量的线程处于休眠状态，只是等待输入或者输出数据就绪，这可能算是一种资源浪费。
 
 第二，需要为每个线程的调用栈都分配内存，其默认值大小区间为64KB到1MB，具体取决于操作系统。
 
 第三，即使Java虚拟机(JVM)在物理上可以支持非常大数量的线程，但是远在到达该极限之前，上下文切换所带来的开销就会带来麻烦。
 
#### Java NIO
 新的还是非阻塞的
 
 NIO 最开始是新的输入/输出(New Input/Output)的英文缩写，但是，该Java API已经出现足够长的时间了，
 
 不再是“新的”了，因此，如今大多数的用户认为 NIO 代表非阻塞 I/O(Non-blocking I/O)，
 
 而阻塞 I/O(blocking I/O)是旧的输入/输出(old input/output，OIO)。
 
 你也可能遇到它被称为普通 I/O(plain I/O)的时候。
 
## 第二章 开发第一个Netty服务器
### EchoServer开发
 EchoServer进行开发,详情见代码
### EchoClient开发
 EchoClient进行开发,详情见代码
## 第六章 Java的序列化
### 通过Java的序列化来进行通信
 使用ObjectDecoder和ObjectEncoder进行解码和避免
### 服务端开发
 对于ObjectDecoder可能存在拆包和粘包的问题,加你设置objectSize的大小为Integer.MAX_SIZE
### 客户端开发
 在pipeline中添加解码和编码器
 
## 第七章 Protobuf序列化
### Protobuf的使用
 使用Protobuf进行序列化,具有跨平台和语言无关,序列化后小等特点
 
 1.安装protobuf
   brew install protobuf
   
 2.编辑SubscribeReq.proto
   ![proto示例](img/proto示例.png)
 
 3.运行protoc命令
   protoc -I=./ --java_out=../src/main/java/ ./SubscribeReq.proto 
### Protobuf序列化
 使用Protobuf进行序列化,开发服务器端与客户端
### 客户端和服务端开发
 与第六章相似主要是解码编码起的使用
 
## 第八章 MarShalling序列化
### JBoss进行序列化
 MarShalling完全兼容JDK序列化,对JBoss的解码编码类库进行封装
### 保留Java序列化的优势
 完全兼容JDK序列化,通过Netty的Marshalling进行解编类
### 客户端和服务端开发
  与第六章相似主要是解码编码起的使用
 
## 第九章 Http协议开发应用
 