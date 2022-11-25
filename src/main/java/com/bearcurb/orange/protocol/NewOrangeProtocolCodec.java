package com.bearcurb.orange.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class NewOrangeProtocolCodec extends ByteToMessageCodec<NewProcotol> {

  @Override
  public void encode(ChannelHandlerContext ctx, NewProcotol msg, ByteBuf out) throws Exception {
    try {
      MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

      packer.packString(Optional.of(msg.getFlag()).get());
      packer.packBoolean(Optional.of(msg.isRequest()).get());
      packer.packString(Optional.of(msg.getRequestId()).get());
      packer.packInt(Optional.of(msg.getEvent()).get());
      packer.packBoolean(Optional.of(msg.isNeedResult()).get());
      packer.packString(Optional.of(msg.getService()).get());
      packer.packString(Optional.of(msg.getData()).get());

      String end = "\r\n";
      byte[] packageData = packer.toByteArray();
      byte[] packageEnd = end.getBytes(StandardCharsets.US_ASCII);
      out.writeInt(packageData.length + packageEnd.length);
      out.writeBytes(packageData, 0, packageData.length);
      out.writeBytes(packageEnd, 0, packageEnd.length);

      // DEBUG
      int index = 0;
      for (index = 0; index < packageData.length; index++) {
        System.out.print("[" + index + ":" + packageData[index] + "]");
        if (index != 0 && index % 10 == 0) {
          System.out.println();
        }
      }
//      for (int i = 0; i < packageEnd.length; i++) {
//        System.out.print("[" + (index + i) + ":" + packageEnd[i] + "]");
//      }
      System.out.println();
      // DEBUG

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("在编码中发生异常 连接关闭");
      ctx.close();
    }
  }


  @Override
  public void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
    try {
      // DEBUG
      int length = in.readInt();
      System.out.println(length);
      byte[] data = new byte[length];
      for (int i = 0; i < length - 2; i++) {
        data[i] = in.readByte();
        System.out.print("[" + i + ":" + data[i] + "]");
        if (i != 0 && i % 10 == 0) {
          System.out.println();
        }
      }
      in.resetReaderIndex();
      // DEBUG
      int packageLength = in.readInt() - 2;
      byte[] packageData = new byte[packageLength];
      for (int i = 0; i < packageLength; i++) {
        packageData[i] = in.readByte();
      }
      MessageUnpacker packer = MessagePack.newDefaultUnpacker(packageData);
      NewProcotol protocol = new NewProcotol();

      protocol.setFlag(packer.unpackString());
      protocol.setRequest(packer.unpackBoolean());
      protocol.setRequestId(packer.unpackString());
      protocol.setEvent(packer.unpackInt());
      protocol.setNeedResult(packer.unpackBoolean());
      protocol.setService(packer.unpackString());
      protocol.setData(packer.unpackString());
      out.add(protocol);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("在解码中发生异常 连接关闭");
      ctx.close();
    }
  }
}
