package com.bearcurb.orange.server.util;

import com.bearcurb.orange.protocol.NewProcotol;

public class ServerProtocolGenerator {

  public static NewProcotol getSimpleResultProtocol() {
    NewProcotol procotol = new NewProcotol();
    procotol.setRequest(false);
    procotol.setFlag("ORANGE_1.1");
    return procotol;
  }

}
