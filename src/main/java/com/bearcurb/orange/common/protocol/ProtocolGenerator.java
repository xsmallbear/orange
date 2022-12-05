package com.bearcurb.orange.common.protocol;

public class ProtocolGenerator {

  public static Protocol getSimpleServerMessage() {
    Protocol protocol = new Protocol();
    protocol.setRequest(false);
    protocol.setFlag("ORANGE_1.1");
    return protocol;
  }

  public static Protocol getSimpleClientMessage() {
    Protocol protocol = new Protocol();
    protocol.setRequest(false);
    protocol.setFlag("ORANGE_1.1");
    return protocol;
  }

}
