package com.bearcurb.orange.client.util;

import com.bearcurb.orange.common.protocol.Procotol;

public class ClientProtocolGenerator {

  public static Procotol getSimpleResultProtocol() {
    Procotol procotol = new Procotol();
    procotol.setRequest(true);
    procotol.setFlag("ORANGE_1.1");
    return procotol;
  }

}
