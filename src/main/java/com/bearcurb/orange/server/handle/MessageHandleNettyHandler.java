package com.bearcurb.orange.server.handle;

import com.bearcurb.orange.common.protocol.Procotol;
import com.bearcurb.orange.server.IMessageHandle;
import com.bearcurb.orange.server.IMessageIntercept;
import com.bearcurb.orange.server.MessageHandleManager;
import com.bearcurb.orange.server.ServerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

@Sharable
public class MessageHandleNettyHandler extends SimpleChannelInboundHandler<Procotol> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Procotol request) {

    System.out.println(request.getEvent());
    if (request.getEvent() != Procotol.EventType.SIMPLE) {
      ctx.fireChannelRead(request);
      return;
    }
    // context create
    ServerContext context = new ServerContext();
    context.setChannel(ctx.channel());
    context.setProtocol(request);

    //call intercept
    String serviceName = request.getService();
    System.out.println(serviceName);
    List<IMessageIntercept> interceptList = MessageHandleManager.getInstance().getMessageIntercept(serviceName);
    for (int i = 0; i < interceptList.size(); i++) {
      if (interceptList.get(i).preHandle(context) == false) {
        return;
      }
    }
    //call messageHandle
    IMessageHandle service = MessageHandleManager.getInstance().getMessageHandle(serviceName);
    if (service != null) {
      service.handle(context);
    } else {
      //No handle
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    System.out.println("这里发生了一个错误" + cause);
    cause.printStackTrace();
    ctx.close();
  }
}
