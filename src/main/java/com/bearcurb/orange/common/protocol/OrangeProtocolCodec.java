package com.bearcurb.orange.common.protocol;

import com.bearcurb.orange.common.debug.OrangeProtocolCodecUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.CodecException;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class OrangeProtocolCodec extends ByteToMessageCodec<Protocol> {

  private final String splitFlag = "&$&";

  @Override
  public void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
    try {
      OrangeProtocolCodecUtil.printProtocol(msg);  //DEBUG
      MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
      packer.packString(Optional.of(msg.getFlag()).get());
      packer.packBoolean(Optional.of(msg.isRequest()).get());
      packer.packString(Optional.of(msg.getRequestId()).get());
      packer.packInt(Optional.of(msg.getEvent()).get());
      packer.packBoolean(Optional.of(msg.isNeedResult()).get());
      packer.packString(Optional.of(msg.getService()).get());
      packer.packString(Optional.of(msg.getData()).get());
      byte[] packageData = packer.toByteArray();
      byte[] packageEnd = splitFlag.getBytes(StandardCharsets.US_ASCII);
      out.writeInt(packageData.length + packageEnd.length);
      out.writeBytes(packageData, 0, packageData.length);
      out.writeBytes(packageEnd, 0, packageEnd.length);
    } catch (Exception e) {
      ctx.fireExceptionCaught(new CodecException());
    }
  }

  @Override
  public void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
    try {
      // DEBUG
//      printDecodeDebugInfo(in);
      // DEBUG
      int packageLength = in.readInt() - splitFlag.length();
      byte[] packageData = new byte[packageLength];
      for (int i = 0; i < packageLength; i++) {
        packageData[i] = in.readByte();
      }
      MessageUnpacker packer = MessagePack.newDefaultUnpacker(packageData);
      Protocol protocol = new Protocol();

      protocol.setFlag(packer.unpackString());
      protocol.setRequest(packer.unpackBoolean());
      protocol.setRequestId(packer.unpackString());
      protocol.setEvent(packer.unpackInt());
      protocol.setNeedResult(packer.unpackBoolean());
      protocol.setService(packer.unpackString());
      protocol.setData(packer.unpackString());
      OrangeProtocolCodecUtil.printProtocol(protocol);  //DEBUG
      out.add(protocol);
    } catch (Exception e) {
      e.printStackTrace();
      ctx.fireExceptionCaught(new CodecException());
    }
  }

  private void printEncodeDebugInfo(byte[] data, int step) {
    for (int i = 0; i < data.length; i++) {
      System.out.print("[" + i + ":" + data[i] + "]");
      if (i != 0 && i % step == 0) {
        System.out.println();
      }
    }
    System.out.print("\n");
  }

  private void printDecodeDebugInfo(ByteBuf in) {
    int length = in.readInt();
    System.out.println(length);
    byte[] data = new byte[length];
    for (int i = 0; i < length - splitFlag.length(); i++) {
      data[i] = in.readByte();
      System.out.print("[" + i + ":" + data[i] + "]");
      if (i != 0 && i % 10 == 0) {
        System.out.println();
      }
    }
    System.out.println();
    in.resetReaderIndex();
  }
}
