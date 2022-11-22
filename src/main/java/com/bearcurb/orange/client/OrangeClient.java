package com.bearcurb.orange.client;

import com.bearcurb.orange.protocol.OrangeRequest;
import com.bearcurb.orange.protocol.handle.OrangeClientProtocolDecoder;
import com.bearcurb.orange.protocol.handle.OrangeClientProtocolEncoder;
import com.bearcurb.orange.protocol.handle.OrangeHeartBeatClientHandle;
import com.bearcurb.orange.server.IClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class OrangeClient implements IClient {
  private String host;
  private int port;
  private EventLoopGroup workerGroup = new NioEventLoopGroup();
  private Bootstrap bootstrap = new Bootstrap();
  private ClientHandler clientHandler = new ClientHandler();

  public OrangeClient(String host, int port) {
    this.host = host;
    this.port = port;
    init();
  }

  public void init() {
    bootstrap.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
      @Override
      protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(8, 4, 0, TimeUnit.SECONDS));
        pipeline.addLast(new OrangeHeartBeatClientHandle());
        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new OrangeClientProtocolEncoder());
        pipeline.addLast(new OrangeClientProtocolDecoder());
        pipeline.addLast(clientHandler);
      }
    });
  }

  @Override
  public void connect() throws InterruptedException {
    ChannelFuture f = bootstrap.connect(host, port).sync();
  }

  @Override
  public void disconnect() throws InterruptedException {
    workerGroup.shutdownGracefully().sync();
  }

  public void sendMessage(OrangeRequest message) throws InterruptedException {
    clientHandler.sendMessage(message);
  }
}
