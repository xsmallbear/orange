package com.bearcurb.orange.protocol.handle;

import com.bearcurb.orange.protocol.ProtocolAddverifyUtil;
import com.bearcurb.orange.protocol.ResponseAddVerify;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;
import java.util.logging.Logger;

public class ClientProtocolDecoder extends MessageToMessageDecoder<String> {

  @Override
  protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
    try {
      ResponseAddVerify messageObject = new Gson().fromJson(msg, ResponseAddVerify.class);
      //协议校验
      if (ProtocolAddverifyUtil.orangeResponseVerify(messageObject) == false) {
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
