package com.bearcurb.orange.client;

import com.bearcurb.orange.protocol.Request;
import com.bearcurb.orange.protocol.handle.ClientProtocolDecoder;
import com.bearcurb.orange.protocol.handle.ClientProtocolEncoder;
import com.bearcurb.orange.protocol.handle.HeartBeatClientHandler;
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

public class Client implements IClient {
  private String host;
  private int port;
  private EventLoopGroup workerGroup = new NioEventLoopGroup();
  private Bootstrap bootstrap = new Bootstrap();
  private ClientHandler clientHandler = new ClientHandler();

  public Client(String host, int port) {
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
        pipeline.addLast(new HeartBeatClientHandler());
        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new ClientProtocolEncoder());
        pipeline.addLast(new ClientProtocolDecoder());
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

  public void sendMessage(Request message) throws InterruptedException {
    clientHandler.sendMessage(message);
  }
}
