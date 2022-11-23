package com.bearcurb.orange.protocol.handle;

import com.bearcurb.orange.protocol.ProtocolAddverifyUtil;
import com.bearcurb.orange.protocol.Response;
import com.bearcurb.orange.protocol.ResponseAddVerify;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.logging.Logger;

public class ServerProtocolEncoder extends MessageToMessageEncoder<Response> {
  @Override
  protected void encode(ChannelHandlerContext ctx, Response msg, List<Object> out) throws Exception {
    try {
      ResponseAddVerify wrapper = ProtocolAddverifyUtil.wrapperResponse(msg);
      String json = new Gson().toJson(wrapper) + System.getProperty("line.separator");
      out.add(json);
    } catch (Exception ok) {
      Logger.getLogger("OrangeProtocolEncoder").info("编码异常 连接关闭");
      ctx.close();
    }
  }
}
