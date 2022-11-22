package com.bearcurb.orange.server;

import com.bearcurb.orange.protocol.OrangeRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Sharable
public class ServerHandle extends SimpleChannelInboundHandler<OrangeRequest> {
  private Map<String, OrangeHandle> messageHandles = Maps.newHashMap();
  private List<OrangeIntercept> interceptList = Lists.newArrayList();
  private Map<OrangeIntercept, String[]> interceptExcludes = Maps.newHashMap();

  public void addHandle(String serviceName, OrangeHandle handle) {
    messageHandles.put(serviceName, handle);
  }

  public void addIntercept(String[] excludes, OrangeIntercept intercept) {
    interceptList.add(intercept);
    interceptExcludes.put(intercept, excludes);
  }

  @Override

  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Logger.getLogger("handlerAdded").info("A new Channel connect");
    Channel channel = ctx.channel();
    SocketAddress address = channel.localAddress();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, OrangeRequest request) throws Exception {
    // context create
    OrangeServerContext context = new OrangeServerContext();
    context.setChannel(ctx.channel());
    context.setProtocol(request);

    String serviceName = request.getServiceName();
    //intercept
    for (int i = 0; i < interceptList.size(); i++) {
      OrangeIntercept intercept = interceptList.get(i);
      String[] excludes = interceptExcludes.get(intercept);
      boolean callThis = true;
      for (int j = 0; j < excludes.length; j++) {
        String name = excludes[i];
        if (serviceName.equals(name)) {
          callThis = false;
          break;
        }
      }
      //check intercept result
      if (callThis) {
        boolean result = intercept.preHandle(context);
        if (result == false) {
          return;
        }
      }
    }
    //call service handle
    OrangeHandle handle = this.messageHandles.get(serviceName);
    if (handle != null) {
      handle.handle(context);
    } else {
      //No handle
    }
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    System.out.println("这边已经五秒没有受到消息了");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
