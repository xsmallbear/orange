package com.bearcurb.orange;

import com.bearcurb.orange.common.protocol.OrangeProtocolCodec;
import com.bearcurb.orange.common.protocol.Protocol;
import com.bearcurb.orange.server.util.ServerProtocolGenerator;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

import java.util.UUID;

public class TestNewOrangeProtocolCodec {

  public static void main(String[] args) throws Exception {

    OrangeProtocolCodec codec = new OrangeProtocolCodec();

    Protocol protocol = ServerProtocolGenerator.getSimpleResultProtocol();

    protocol.setEvent(Protocol.EventType.HEART);
    protocol.setRequest(true);
    protocol.setRequestId(UUID.randomUUID().toString());
    protocol.setEvent(Protocol.EventType.SIMPLE);
    protocol.setService("registerServer");
    protocol.setData("sdghjksahdkjghsakjldhgjkhasdjkghklsghkdgkhaskjldhkjlasdkjgksjkghadgkljahdkghjkasdkshdkgjsdhgkashgklasdkgjdghlasdhgkashdgkjasdh");

    EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoggingHandler(), new LineBasedFrameDecoder(2048), new OrangeProtocolCodec());
    embeddedChannel.writeOutbound(protocol);
    ByteBuf byteBuf = embeddedChannel.readOutbound();
    embeddedChannel.writeInbound(byteBuf);
    Protocol nps = embeddedChannel.readInbound();

    System.out.println(nps.getFlag());
    System.out.println(nps.isRequest());
    System.out.println(nps.getRequestId());
    System.out.println(nps.getEvent());
    System.out.println(nps.isNeedResult());
    System.out.println(nps.getService());
    System.out.println(nps.getData());
  }
}
