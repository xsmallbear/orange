package com.bearcurb.orange.server.util;

import com.bearcurb.orange.common.protocol.Protocol;

public class ServerProtocolGenerator {

  public static Protocol getSimpleResultProtocol() {
    Protocol protocol = new Protocol();
    protocol.setRequest(false);
    protocol.setFlag("ORANGE_1.1");
    return protocol;
  }

}
