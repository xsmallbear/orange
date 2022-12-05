package com.bearcurb.orange.server;

import com.bearcurb.orange.common.OrangeSplitPackageHandler;
import com.bearcurb.orange.common.logger.LogLevel;
import com.bearcurb.orange.common.logger.OrangeLogger;
import com.bearcurb.orange.common.protocol.OrangeProtocolCodec;
import com.bearcurb.orange.server.handle.MessageHandleNettyHandler;
import com.bearcurb.orange.server.handle.ServerErrorNettyHandler;
import com.bearcurb.orange.server.handle.ServerHeartBeatNettyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Server implements IServer {

  private String host;
  private int port;
  private int heartbeatDetectionTime;
  private Channel serverChannel;
  private EventLoopGroup group = new NioEventLoopGroup();
  private ServerBootstrap serverBootstrap = new ServerBootstrap();

  public void setHeartbeatDetectionTime(int heartbeatDetectionTime) {
    this.heartbeatDetectionTime = heartbeatDetectionTime;
  }

  public Server(String host, int port, Logger logger) {
    OrangeLogger.init(LogLevel.INFO, logger);
    this.host = host;
    this.port = port;
    init();
  }

  public void registerMessageHandle(String serviceName, IMessageHandle handle) {
    MessageHandleManager.getInstance().registerMessageHandle(serviceName, handle);
  }

  public void registerMessageIntercept(List<String> excludes, IMessageIntercept intercept) {
    MessageHandleManager.getInstance().registerMessageIntercept(excludes, intercept);
  }

  public void init() {
    ByteBuf splitFlag = Unpooled.copiedBuffer("&$&".getBytes(StandardCharsets.US_ASCII));

    serverBootstrap.group(group)
      .channel(NioServerSocketChannel.class)
      .localAddress(port)
      .childHandler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) {
          ChannelPipeline pipeline = ch.pipeline();
          pipeline.addLast(new IdleStateHandler(heartbeatDetectionTime, 0, 0, TimeUnit.SECONDS));
          pipeline.addLast(new OrangeSplitPackageHandler(2048));
          pipeline.addLast(new OrangeProtocolCodec());
          pipeline.addLast(new ServerHeartBeatNettyHandler());
          pipeline.addLast(new MessageHandleNettyHandler());
          pipeline.addLast(new ServerErrorNettyHandler());
        }
      });
  }

  @Override
  public void start() throws InterruptedException {
    ChannelFuture future = serverBootstrap.bind().sync();
    serverChannel = future.channel();
  }

  @Override
  public void stop() throws InterruptedException {
    if (serverChannel != null) {
      serverChannel.close();
      serverChannel = null;
    }
    if (group != null) {
      group.shutdownGracefully().sync();
      group = null;
    }
  }
}
