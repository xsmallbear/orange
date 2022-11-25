package com.bearcurb.orange.server;

import com.bearcurb.orange.protocol.NewProcotol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.List;
import java.util.logging.Logger;

@Sharable
public class ServerHandle extends SimpleChannelInboundHandler<NewProcotol> {

  @Override

  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Logger.getLogger("handlerAdded").info("A new Channel connect");
    Channel channel = ctx.channel();
    SocketAddress address = channel.localAddress();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, NewProcotol request) throws Exception {

    System.out.println(request.getEvent());
    if (request.getEvent() != NewProcotol.EventType.SIMPLE) {
      ctx.fireChannelRead(request);
      return;
    }
    // context create
    ServerContext context = new ServerContext();
    context.setChannel(ctx.channel());
    context.setProtocol(request);

    String serviceName = request.getService();
    System.out.println(serviceName);
    List<IIntercept> interceptList = ServiceManager.getInstance().getServiceIntercept(serviceName);
    for (int i = 0; i < interceptList.size(); i++) {
      if (interceptList.get(i).preHandle(context) == false) {
        return;
      }
    }
    IService service = ServiceManager.getInstance().getService(serviceName);
    if (service != null) {
      service.handle(context);
    } else {
      //No handle
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println("这里发生了一个错误" + cause);
    cause.printStackTrace();
    ctx.close();
  }
}
