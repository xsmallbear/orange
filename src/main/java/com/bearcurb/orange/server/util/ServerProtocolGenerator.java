package com.bearcurb.orange.server.util;

import com.bearcurb.orange.common.protocol.Procotol;

public class ServerProtocolGenerator {

  public static Procotol getSimpleResultProtocol() {
    Procotol procotol = new Procotol();
    procotol.setRequest(false);
    procotol.setFlag("ORANGE_1.1");
    return procotol;
  }

}
