package com.bearcurb.orange;

import com.bearcurb.orange.protocol.NewProcotol;
import com.bearcurb.orange.server.util.ServerProtocolGenerator;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.UUID;

public class TestUseMessagePack {
  public static void main(String[] args) throws IOException {
    NewProcotol procotol = ServerProtocolGenerator.getSimpleResultProtocol();

    procotol.setEvent(NewProcotol.EventType.HEART);
    procotol.setRequest(true);
    procotol.setRequestId(UUID.randomUUID().toString());
    procotol.setEvent(NewProcotol.EventType.SIMPLE);
    procotol.setService("registerServer");
    procotol.setData("this is data");

    byte[] msgData = getPacker(procotol).toByteArray();

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

  public static MessageBufferPacker getPacker(NewProcotol procotol) throws IOException {
    MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
    packer.packString(procotol.getFlag());
    packer.packBoolean(procotol.isRequest());
    packer.packString(procotol.getRequestId());
    packer.packInt(procotol.getEvent());
    packer.packBoolean(procotol.isNeedResult());
    packer.packString(procotol.getService());
    packer.packString(procotol.getData());
    return packer;
  }
}
