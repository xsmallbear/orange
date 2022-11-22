package com.bearcurb.orange.protocol.handle;

import com.bearcurb.orange.protocol.OrangeProtocolAddverifyUtil;
import com.bearcurb.orange.protocol.OrangeResponse;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.logging.Logger;

public class OrangeServerProtocolEncoder extends MessageToMessageEncoder<OrangeResponse> {
  @Override
  protected void encode(ChannelHandlerContext ctx, OrangeResponse msg, List<Object> out) throws Exception {
    try {
      OrangeResponseAddVerify wrapper = OrangeProtocolAddverifyUtil.wrapperResponse(msg);
      String json = new Gson().toJson(wrapper) + System.getProperty("line.separator");
      out.add(json);
    } catch (Exception ok) {
      Logger.getLogger("OrangeProtocolEncoder").info("编码异常 连接关闭");
      ctx.close();
    }
  }
}
