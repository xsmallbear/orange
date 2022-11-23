package com.bearcurb.orange.protocol.handle;

import com.bearcurb.orange.protocol.ProtocolAddverifyUtil;
import com.bearcurb.orange.protocol.Request;
import com.bearcurb.orange.protocol.RequestAddVerify;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.logging.Logger;

public class ClientProtocolEncoder extends MessageToMessageEncoder<Request> {
  @Override
  protected void encode(ChannelHandlerContext ctx, Request msg, List<Object> out) throws Exception {
    try {
      RequestAddVerify wrapper = ProtocolAddverifyUtil.wrapperRequest(msg);
      String json = new Gson().toJson(wrapper) + System.getProperty("line.separator");
      out.add(json);
    } catch (Exception ok) {
      Logger.getLogger("OrangeProtocolEncoder").info("编码异常 连接关闭");
      ctx.close();
    }
  }
}
