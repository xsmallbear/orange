package com.bearcurb.orange.server;

import com.bearcurb.orange.common.logger.OrangeLogger;
import com.bearcurb.orange.common.protocol.OrangeProtocolCodec;
import com.bearcurb.orange.server.handle.MessageHandleNettyHandler;
import com.bearcurb.orange.server.handle.ServerErrorNettyHandler;
import com.bearcurb.orange.server.handle.ServerHeartBeatNettyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Server implements IServer {

  private String host;
  private int port;
  private int heartbeatDetectionTime;
  private OrangeLogger logger;
  private Channel serverChannel;
  private EventLoopGroup bossGroup = new NioEventLoopGroup();
  private EventLoopGroup workerGroup = new NioEventLoopGroup();
  private ServerBootstrap serverBootstrap = new ServerBootstrap();

  public void setHeartbeatDetectionTime(int heartbeatDetectionTime) {
    this.heartbeatDetectionTime = heartbeatDetectionTime;
  }

  public Server(String host, int port, Logger logger) {
    this.host = host;
    this.port = port;
    this.logger = new OrangeLogger(logger);
    init();
  }

  public void registerMessageHandle(String serviceName, IMessageHandle handle) {
    MessageHandleManager.getInstance().registerMessageHandle(serviceName, handle);
  }

  public void registerMessageIntercept(List<String> excludes, IMessageIntercept intercept) {
    MessageHandleManager.getInstance().registerMessageIntercept(excludes, intercept);
  }

  public void init() {
    IdleStateHandler idleStateHandler = new IdleStateHandler(3, 0, 0, TimeUnit.SECONDS);
    LineBasedFrameDecoder lineBasedFrameDecoder = new LineBasedFrameDecoder(1024);
    OrangeProtocolCodec orangeProtocolCodecHandler = new OrangeProtocolCodec();
    ServerHeartBeatNettyHandler serverHeartBeatNettyHandler = new ServerHeartBeatNettyHandler();
    MessageHandleNettyHandler serverMessageHandler = new MessageHandleNettyHandler();
    ServerErrorNettyHandler serverErrorNettyHandler = new ServerErrorNettyHandler();

    serverBootstrap.group(bossGroup, workerGroup)
      .channel(NioServerSocketChannel.class)
      .childHandler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) {
          ChannelPipeline pipeline = ch.pipeline();
          pipeline.addLast(idleStateHandler);
          pipeline.addLast(lineBasedFrameDecoder);
          pipeline.addLast(orangeProtocolCodecHandler);
          pipeline.addLast(serverHeartBeatNettyHandler);
          pipeline.addLast(serverMessageHandler);
          pipeline.addLast(serverErrorNettyHandler);
        }
      });
  }

  @Override
  public void start() throws InterruptedException {
    ChannelFuture future = serverBootstrap.bind(host, port).sync();
    serverChannel = future.channel();
  }

  @Override
  public void stop() throws InterruptedException {
    if (serverChannel != null) {
      serverChannel.close();
      serverChannel = null;
      bossGroup.shutdownGracefully().sync();
      workerGroup.shutdownGracefully().sync();
    }
  }
}
