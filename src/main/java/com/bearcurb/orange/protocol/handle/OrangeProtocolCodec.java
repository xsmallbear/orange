package com.bearcurb.orange.protocol.handle;

import com.bearcurb.orange.protocol.NewProcotol;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class OrangeProtocolCodec extends MessageToMessageCodec<String, NewProcotol> {

  public final String FLAG = "ORANGE_1.1";

  @Override
  protected void encode(ChannelHandlerContext ctx, NewProcotol msg, List<Object> out) throws Exception {
    try {
      String json = new Gson().toJson(msg) + "\n";
      System.out.println(json);
      out.add(json);
    } catch (Exception e) {
      System.out.println("解码异常 连接关闭");
      ctx.close();
    }
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
    try {
      System.out.println(msg);
      NewProcotol protocol = new Gson().fromJson(msg, NewProcotol.class);
      System.out.println(protocol);
      if (protocol == null || !protocol.getFlag().equals(FLAG)) {
        System.out.println("协议校验失败 连接关闭");
        ctx.close();
      }
      out.add(protocol);
    } catch (Exception e) {
      System.out.println("解码异常 连接关闭");
      ctx.close();
    }
  }
}
