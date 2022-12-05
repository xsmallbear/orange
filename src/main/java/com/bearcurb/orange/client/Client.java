package com.bearcurb.orange.client;

import com.bearcurb.orange.client.handle.ClientErrorNettyHandler;
import com.bearcurb.orange.client.handle.ClientHeartBeatNettyHandle;
import com.bearcurb.orange.common.protocol.OrangeProtocolCodec;
import com.bearcurb.orange.common.protocol.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.StandardCharsets;
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
        pipeline.addLast(new IdleStateHandler(8, 4, 0, TimeUnit.SECONDS));
        ByteBuf splitFlag = Unpooled.copiedBuffer("&$&".getBytes(StandardCharsets.UTF_8));
        DelimiterBasedFrameDecoder delimiterBasedFrameDecoder = new DelimiterBasedFrameDecoder(2048, splitFlag);
        pipeline.addLast(delimiterBasedFrameDecoder);
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
    clientChannel = f.channel();
  }

  @Override
  public void disconnect() throws InterruptedException {
    workerGroup.shutdownGracefully().sync();
  }

  public void sendMessage(Protocol message) throws InterruptedException {
    clientHandler.sendMessage(message);
  }
}
