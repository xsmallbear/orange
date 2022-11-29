package com.bearcurb.orange.client.util;

import com.bearcurb.orange.common.protocol.Protocol;

public class ClientProtocolGenerator {

  public static Protocol getSimpleResultProtocol() {
    Protocol protocol = new Protocol();
    protocol.setRequest(true);
    protocol.setFlag("ORANGE_1.1");
    return protocol;
  }

}
