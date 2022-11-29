package com.bearcurb.orange;

import com.bearcurb.orange.common.protocol.Protocol;
import com.bearcurb.orange.server.util.ServerProtocolGenerator;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.UUID;

public class TestUseMessagePack {
  public static void main(String[] args) throws IOException {
    Protocol protocol = ServerProtocolGenerator.getSimpleResultProtocol();

    protocol.setEvent(Protocol.EventType.HEART);
    protocol.setRequest(true);
    protocol.setRequestId(UUID.randomUUID().toString());
    protocol.setEvent(Protocol.EventType.SIMPLE);
    protocol.setService("registerServer");
    protocol.setData("this is data");

    byte[] msgData = getPacker(protocol).toByteArray();

    System.out.println("msgData:" + msgData.length);
    for (int i = 0; i < msgData.length; i++) {
      System.out.print((char) msgData[i]);
    }
    System.out.println();

    //解码

    MessageUnpacker packer = MessagePack.newDefaultUnpacker(msgData);
    System.out.println(packer.unpackString());
    System.out.println(packer.unpackBoolean());
    System.out.println(packer.unpackString());
    System.out.println(packer.unpackInt());
    System.out.println(packer.unpackBoolean());
    System.out.println(packer.unpackString());
    System.out.println(packer.unpackString());

  }

  public static MessageBufferPacker getPacker(Protocol protocol) throws IOException {
    MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
    packer.packString(protocol.getFlag());
    packer.packBoolean(protocol.isRequest());
    packer.packString(protocol.getRequestId());
    packer.packInt(protocol.getEvent());
    packer.packBoolean(protocol.isNeedResult());
    packer.packString(protocol.getService());
    packer.packString(protocol.getData());
    return packer;
  }
}
