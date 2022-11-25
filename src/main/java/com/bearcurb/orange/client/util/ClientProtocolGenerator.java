package com.bearcurb.orange.client.util;

import com.bearcurb.orange.protocol.NewProcotol;

public class ClientProtocolGenerator {

  public static NewProcotol getSimpleResultProtocol() {
    NewProcotol procotol = new NewProcotol();
    procotol.setRequest(true);
    procotol.setFlag("ORANGE_1.1");
    return procotol;
  }

}
