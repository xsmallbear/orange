package com.bearcurb.orange.client;

import com.bearcurb.orange.client.handle.ClientHeartBeatNettyHandle;
import com.bearcurb.orange.client.handle.ClientErrorNettyHandler;
import com.bearcurb.orange.common.protocol.OrangeProtocolCodec;
import com.bearcurb.orange.common.protocol.Procotol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class Client implements IClient {
  private String host;
  private int port;
  private EventLoopGroup workerGroup = new NioEventLoopGroup();
  private Bootstrap bootstrap = new Bootstrap();
  private ClientHandler clientHandler = new ClientHandler();
  private Channel clientChannel;

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
//        pipeline.addLast(new IdleStateHandler(8, 4, 0, TimeUnit.SECONDS));
//        pipeline.addLast(new HeartBeatClientHandler());
        pipeline.addLast(new IdleStateHandler(8, 4, 0, TimeUnit.SECONDS));
        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new OrangeProtocolCodec());
        pipeline.addLast(new ClientHeartBeatNettyHandle());
        pipeline.addLast(clientHandler);
        pipeline.addLast(new ClientErrorNettyHandler());
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

  public void sendMessage(Procotol message) throws InterruptedException {
    clientHandler.sendMessage(message);
  }
}
