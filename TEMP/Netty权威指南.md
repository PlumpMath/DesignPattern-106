# Netty权威指南

## NIO入门

### 传统的BIO编程

网络编程的基本模型是Client/Server模型，也就是两个进程之间进行相互通信，其中服务端提供位置信息（绑定的IP地址和监听端口），客户端通过连接操作向服务端监听的地址发起连接请求，通过三次握手建立连接，如果连接建立成功，双方就可以通过网络套接字（Socket）进行通信。

在基于传统同步阻塞模型开发中，ServerSocket负责绑定IP地址，启动监听端口；Socket负责发起连接操作。连接成功之后，双方通过输入和输出流进行同步阻塞式通信。

#### TimeServer

```java
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class TimeServer{
  public static void main(String[] args) throws IOException{
    int port=8080;
    if(args!=null&&args.length>0){
      try{
        port=Integer.valueOf(args[0]);
      }catch(NumberFormatException e){
        //采用默认值
      }
    }
    ServerSocket server=null;
    try{
      server=new ServerSocket(port);
      System.out.println("The time server is start in port: "+port);
      Socket socket=null;
      while(true){
        socket=server.accept();
        new Thread(new TimeServerHandler(socket)).start();
      }
    }finally{
      if(server!=null){
        System.out.println("The time server close");
        server.close();
        server=null;
      }
    }
  }
}
```

#### TimeServerHandler

```java
public class TimeServerHandler implements Runnable{
  private Socket socket;
  public TimeServerHandler(Socket socket){
    this.socket=socket;
  }
  @Override
  public void run(){
    BufferedReader in =null;
    PrintWriter out=null;
    try{
      in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      out=new PrintWriter(this.socket.getOutputStream(),true);
      String currentTime=null;
      String body=null;
      while(true){
        body=in.readLine();
        if(body==null)
          break;
        System.out.println("The time server receive order: "+body);
        currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new java.util.Date(
        System.currentTimeMillis()).toString():"BAD ORDER";
        out.println(currentTime);
      }
    }catch(Exception e){
      if(in!=null){
        try{
          in.close();
        }catch(IOException e1){
          e1.printStackTrace();
        }
      }
      if(out!=null){
        out.close();
        out=null;
      }
      if(this.socket!=null){
        try{
          this.socket.close();
        }catch(IOException e1){
          e1.printStackTrace();
        }
        this.socket=null;
      }
    }
  }
}
```

#### TimeClient

```java
public class TimeClient{
  public static void main(String[] args){
    int port=8080;
    if(args!=null&&args.length>0){
      try{
        port=Integer.valueOf(args[0]);
      }catch(NumberFormatException e){
      }
    }
    Socket socket=null;
    BufferedReader in=null;
    PrintWriter out=null;
    try{
      socket=new Socket("127.0.0.1",port);
      in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out=new PrintWriter(socket.getOutputStream(),true);
      out.println("QUERY TIME ORDER");
      System.out.println("Send order 2 server succeed.");
      String resp=in.readLine();
      System.out.println("Now is: "+resp);
    }catch(Exception e){
    }finally{
      if(out!=null){
        out.close();
        out=null;
      }
      if(in!=null){
        try{
          in.close();
        }catch(IOException e){
          e.printStackTrace();
        }
        in=null;
      }
      if(socket!=null){
        try{
          socket.close();
        }catch(IOException e){
          e.printStackTrace();
        }
        socket=null;
      }
    }
  }
}
```

BIO主要的问题在于每当有一个新的客户请求接入时，服务器必须创建一个新的线程处理新接入的客户端链路，一个线程只能处理一个客户端连接。在高性能服务器应用领域，往往需要面向成千上万个客户端的并发连接，这种模型显然无法满足高性能、高并发接入的场景。

### 伪异步I/O编程

#### TimeServer

```java
public class TimeServer{
  public static void main(String[] args) throws IOException{
    int port=8080;
    if(args!=null&&args.length>0){
      try{
        port=Integer.valueOf(args[0]);
      }catch(NumberFormatException e){
        //采用默认值
      }
    }
    ServerSocket server=null;
    try{
      server=new ServerSocket(port);
      System.out.println("The time server is start in port: "+port);
      Socket socket=null;
      TimeServerHandlerExecutePool singleExecutor=new TimeServerHandlerExecutePool(50,10000);
      while(true){
        socket=server.accept();
        singleExecutor.execute(new TimeServerHandler(socket));
      }
    }finally{
      if(server!=null){
        System.out.println("The time server close");
        server.close();
        server=null;
      }
    }
  }
}
```

#### TimeServerHandlerExecutePool

```java
public class TimeServerHandlerExecutePool{
  private ExecutorService executor;
  public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize){
    executor=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxPoolSize,120L,TimeUnit.SECONDS,new ArrayBlockingQueue<java.lang.Runnable>(queueSize));
  }
  public void execute(java.lang.Runnable task){
    executor.execute(task);
  }
}
```

伪异步I/O实际上仅仅只是对之前I/O线程模型的一个简单优化，它无法从根本上解决同步I/O导致的通信线程阻塞问题。如果通信对方返回应答时间过长，会引起的级联故障可能有：

1. 服务端处理缓慢，返回应答消息耗费60s，平时只需要10ms
2. 采用伪异步I/O的线程正在读取故障服务节点的相应，由于读取输入流是阻塞的，因此，它将会被同步阻塞60s。
3. 加入所有的可用线程都被故障服务器阻塞，那后续所有的I/O消息都将在队列中排队。
4. 由于线程池采用阻塞队列实现，当队列积满后，后续入列的操作将被阻塞。
5. 由于前端只有一个Acceptor线程接入客户端接入，它被阻塞在线程池的同步阻塞队列之后，新的客户端请求消息将被拒绝，客户端会发生大量的连接超时。
6. 由于几乎所有的连接都超时，调用者会认为系统已经崩溃，无法接收新的请求消息。

### NIO编程

#### NIO类库简介

##### 缓冲区 Buffer

在NIO库中，所有数据都是用缓冲区处理的。在读取数据时，它是直接读到缓冲区中的；在写入数据时，写入到缓冲区中。在任何时候访问NIO中的数据，都是通过缓冲区进行操作。

每一种Java基本类型（除了Boolean）都有对应的一种缓冲区：

- ByteBuffer：字节缓冲区
- CharBuffer：字符缓冲区
- ShortBuffer：短整型缓冲区
- IntBuffer：整型缓冲区
- LongBuffer：长整型缓冲区
- FloatBuffer：浮点型缓冲区
- DoubleBuffer：双精度浮点型缓冲区

每一个Buffer类都Buffer接口的一个子实例。除了ByteBuffer，每一个Buffer类都有完全一样的操作，只是它们处理的数据类型不一样。因为大多数标准I/O操作都是用ByteBuffer，所以它除了具有一般缓冲区的操作之外还提供了一些特有的操作，方便网络读写。

##### 通道 Channel

Channel是一个通道，可以通过它读取和写入数据，它就像自来水管一样，网络数据通过Channel读取和写入。通道与流的不同之处在于通道是双向的，流只是在一个方向上移动（一个流必须是InputStream或者OutputStream的子类），而且通道可以用于读、写或同时用于读写。

##### 多路复用器 Selector

多路复用器提供选择已经就绪的任务的能力。简单来讲，Selector会不断地轮询注册在其上的Channel，如果某个Channel上面有新的TCP连接接入，读和写事件，这个Channel就处于就绪状态，会被Selector轮询出来，然后通过SelectionKey可以获取就绪Channel的集合，进行后续的I/O操作。

#### NIO服务端序列图

NIO服务端的主要创建过程：

1. 打开ServerSocketChannel，用于监听客户端的连接，它是所有客户端连接的父管道：

   ```java
   ServerSocketChannel acceptorSvr=ServerSocketChannel.open();
   ```

2. 绑定监听端口，设置为非阻塞模式：

   ```java
   acceptorSvr.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"),port));
   acceptorSvr.configureBlocking(false);
   ```

3. 创建Reactor线程，创建多路复用器并启动线程：

   ```java
   Selector selector=Selector.open();
   new Thread(new ReactorTask()).start();
   ```

4. 将ServerSocketChannel注册到Reactor线程的多路复用器Selector上，监听ACCEPT事件：

   ```java
   SelectionKey key=acceptorSvr.register(selector,SelectionKey.OP_ACCEPT,ioHandler);
   ```

5. 多路复用器在线程run方法的无线循环体内轮询准备就绪的Key：

   ```java
   int num=selector.select();
   Set selectedKeys=selector.selectedKeys();
   Iterator it=selectedKeys.iterator();
   while(it.hasNext()){
     SelectionKey key=(SelectionKey)it.next();
     //...deal with I/O event...
   }
   ```

6. 多路复用器监听到有新的客户端接入，处理新的接入请求，完成TCP三次握手，建立物理链路：

   ```java
   SocketChannel channel=svrChannel.accept();
   ```

7. 设置客户端链路为非阻塞模式：

   ```java
   channel.configureBlocking(false);
   channel.socket().setReuseAddress(true);
   ...
   ```

8. 将新接入的客户端连接注册到Reactor线程的多路复用器上，监听读操作，用来读取客户端发送的网络消息：

   ```java
   SelectionKey key=socketChannel.register(selector,SelectionKey.OP_READ,ioHandler);
   ```

9. 异步读取客户端请求消息到缓冲区：

   ```java
   int readNumber=channel.read(receivedBuffer);
   ```

10. 对ByteBuffer进行编码，如果有半包消息指针reset，继续读取后续的报文，讲解码成功的消息封装成Task，投递到业务线程池中，进行业务逻辑编排：

    ```java
    Object message=null;
    while(buffer.hasRemain()){
      byteBuffer.mark();
      Object message=decode(byteBuffer);
      if(message==null){
        byteBuffer.reset();
        break;
      }
      messageList.add(message);
    }
    if(!byteBuffer.hasRemain())
      byteBuffer.clear();
    else
      byteBuffer.compact();
    if(messageList!=null&!messageList.isEmpty()){
      for(Object messageE:messageList){
        handlerTask(messageE);
      }
    }
    ```

11. 将POJO对象encode成ByteBuffer，调用SocketChannel的异步write接口，将消息异步发送给客户端：

    ```java
    socketChannel.write(buffer);
    ```

#### NIO创建的TimeServer源码分析

MultiplexerTimeServer

```java
public class MultiplexerTimeServer implements Runnable{
  private Selector selector;
  private ServerSocketChannel servChannel;
  private volatile boolean stop;
  public MultiplexerTimeServer(int port){
    try{
      selector = Selector.open();
      servChannel=ServerSocketChannel.open();
      servChannel.configureBlocking(false);
      servChannel.socket().bind(new InetSocketAddress(port),1024);
      servChannel.register(selector,SelectionKey.OP_ACCEPT);
      System.out.println("The time server is start in port: "+port);
    }catch(IOException e){
      e.printStackTrace();
      System.exit(1);
    }
  }
  
  public void stop(){
    this.stop=true;
  }
  
  @Override
  public void run(){
    while(!stop){
      try{
        selector.select(1000);
        Set<SelectionKey> selectedKeys=selector.selectedKeys();
        Iterator<SelectionKey> it=selectedKeys.iterator();
        SelectionKey key=null;
        while(it.hasNext()){
          key=it.next();
          it.remove();
          try{
            handleInput(key);
          }catch(Exception e){
            if(key!=null){
              key.cancel();
              if(key.channel()!=null)
                key.channel().close();
            }
          }
        }
      }catch(Throwable t){
        t.printStackTrace();
      }
    }
    if(selector !=null){
      try{
        selector.close();
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }
  
  private void handleInput(SelectionKey key) throws IOException{
    if(key.isValid()){
     if(key.isAcceptable()){
       ServerSocketChannel ssc=(ServerSocketChannel)key.channel();
       SocketChannel sc=ssc.accept();
       sc.configureBlocking(false);
       sc.register(selector,SelectionKey.OP_READ);
     }
     if(key.isReadable()){
       SocketChannel sc=(SocketChannel)key.channel();
       ByteBuffer readBuffer=ByteBuffer.allocate(1024);
       int readBytes=sc.read(readBuffer);
       if(readBytes>0){
         readBuffer.flip();
         byte[] bytes=new byte[readBuffer.remaining()];
         readBuffer.get(bytes);
         String body=new String(bytes,"UTF-8");
         System.out.println("The time server receive order: "+body);
         String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new java.util.Date(System.currentTimeMillis()).toString():"BAD ORDER";
         doWrite(sc,currentTime);
       }else if(readBytes<0){
         key.cancel();
         sc.close();
       }else{}
      }
    }
  }
  
  private void doWrite(SocketChannel channel,String response) throws IOException{
    if(response!=null && response.trim().length()>0){
      byte[] bytes=response.getBytes();
      ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
      writeBuffer.put(bytes);
      writeBuffer.flip();
      channel.write(writeBuffer);
    }
  }
}
```



#### NIO客户端序列图

#### NIO创建的TimeClient源码分析

### AIO编程

#### AIO创建的TimeServer源码分析

#### AIO创建的TimeClient源码分析

#### AIO版本时间服务器运行结果

### 4种I/O的对比

### 选择Netty的理由

