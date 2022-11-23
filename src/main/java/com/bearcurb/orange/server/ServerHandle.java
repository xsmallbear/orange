package com.bearcurb.orange.server;

import com.bearcurb.orange.protocol.Request;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Sharable
public class ServerHandle extends SimpleChannelInboundHandler<Request> {
  private Map<String, IService> messageHandles = new HashMap<>();
  private List<IIntercept> interceptList = new ArrayList<>();
  private Map<IIntercept, String[]> interceptExcludes = new HashMap<>();

  public void addHandle(String serviceName, IService handle) {
    messageHandles.put(serviceName, handle);
  }

  public void addIntercept(String[] excludes, IIntercept intercept) {
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
  protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
    // context create
    ServerContext context = new ServerContext();
    context.setChannel(ctx.channel());
    context.setProtocol(request);

    String serviceName = request.getServiceName();
    //intercept
    for (int i = 0; i < interceptList.size(); i++) {
      IIntercept intercept = interceptList.get(i);
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
    IService handle = this.messageHandles.get(serviceName);
    if (handle != null) {
      handle.handle(context);
    } else {
      //No handle
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
