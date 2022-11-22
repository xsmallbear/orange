package com.bearcurb.orange.protocol.handle;

import com.bearcurb.orange.protocol.OrangeProtocolAddverifyUtil;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;
import java.util.logging.Logger;

public class OrangeClientProtocolDecoder extends MessageToMessageDecoder<String> {

  @Override
  protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
    try {
      OrangeResponseAddVerify messageObject = new Gson().fromJson(msg, OrangeResponseAddVerify.class);
      //协议校验
      if (OrangeProtocolAddverifyUtil.orangeResponseVerify(messageObject) == false) {
        Logger.getLogger("channelRead0").info("协议校验失败！");
        ctx.close();
        return;
      }
      out.add(messageObject);
    } catch (Exception ok) {
      Logger.getLogger("OrangeProtocolDecoder").info("解码异常 连接关闭");
      ctx.close();
    }
  }
}
