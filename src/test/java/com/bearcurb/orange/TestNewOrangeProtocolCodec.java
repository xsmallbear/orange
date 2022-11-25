package com.bearcurb.orange;

import com.bearcurb.orange.protocol.NewOrangeProtocolCodec;
import com.bearcurb.orange.protocol.NewProcotol;
import com.bearcurb.orange.server.util.ServerProtocolGenerator;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import org.testng.annotations.Test;

import java.util.UUID;

public class TestNewOrangeProtocolCodec {

  @Test
  public static void main(String[] args) throws Exception {

    NewOrangeProtocolCodec codec = new NewOrangeProtocolCodec();

    NewProcotol procotol = ServerProtocolGenerator.getSimpleResultProtocol();

    procotol.setEvent(NewProcotol.EventType.HEART);
    procotol.setRequest(true);
    procotol.setRequestId(UUID.randomUUID().toString());
    procotol.setEvent(NewProcotol.EventType.SIMPLE);
    procotol.setService("registerServer");
    procotol.setData("sdghjksahdkjghsakjldhgjkhasdjkghklsghkdgkhaskjldhkjlasdkjgksjkghadgkljahdkghjkasdkshdkgjsdhgkashgklasdkgjdghlasdhgkashdgkjasdh");

    EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoggingHandler(), new LineBasedFrameDecoder(2048), new NewOrangeProtocolCodec());
    embeddedChannel.writeOutbound(procotol);
    ByteBuf byteBuf = embeddedChannel.readOutbound();
    embeddedChannel.writeInbound(byteBuf);
    NewProcotol nps = embeddedChannel.readInbound();

    System.out.println(nps.getFlag());
    System.out.println(nps.isRequest());
    System.out.println(nps.getRequestId());
    System.out.println(nps.getEvent());
    System.out.println(nps.isNeedResult());
    System.out.println(nps.getService());
    System.out.println(nps.getData());
  }
}
