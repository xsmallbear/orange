package com.bearcurb.orange.server;

import com.bearcurb.orange.client.IServer;
import com.bearcurb.orange.protocol.OrangeServerProtocolDecoder;
import com.bearcurb.orange.protocol.OrangeServerProtocolEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class OrangeServer implements IServer {

  private String host;
  private int port;
  private Channel serverChannel;
  private ServerHandle serverHandler = new ServerHandle();
  private EventLoopGroup bossGroup = new NioEventLoopGroup();
  private EventLoopGroup workerGroup = new NioEventLoopGroup();
  private ServerBootstrap serverBootstrap = new ServerBootstrap();

  public OrangeServer(String host, int port) {
    this.host = host;
    this.port = port;
    init();
  }

  public void addHandle(String serviceName, OrangeHandle handle) {
    serverHandler.addHandle(serviceName, handle);
  }

  public void addIntercept(String[] excludes, OrangeIntercept intercept) {
    serverHandler.addIntercept(excludes, intercept);
  }

  public void init() {
    serverBootstrap.group(bossGroup, workerGroup)
      .channel(NioServerSocketChannel.class)
      .handler(new LoggingHandler(LogLevel.INFO))
      .childHandler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
          ChannelPipeline pipeline = ch.pipeline();
          pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
          pipeline.addLast(new LineBasedFrameDecoder(1024));
          pipeline.addLast(new StringDecoder());
          pipeline.addLast(new StringEncoder());
          pipeline.addLast(new OrangeServerProtocolEncoder());
          pipeline.addLast(new OrangeServerProtocolDecoder());
          pipeline.addLast(serverHandler);
        }
      });
  }

  @Override
  public void start() throws InterruptedException {
    System.out.println("start server success with port:" + port);
    ChannelFuture future = serverBootstrap.bind(host, port).sync();
    serverChannel = future.channel();
  }

  @Override
  public void stop() throws InterruptedException {
    if (serverChannel != null) {
      System.out.println("Close server");
      serverChannel.close();
      serverChannel = null;
      bossGroup.shutdownGracefully().sync();
      workerGroup.shutdownGracefully().sync();
    }
  }
}
