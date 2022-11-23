package com.bearcurb.orange.protocol;

public class ProtocolUtil {
  public static NewProcotol getDefaultNewRequestInstance(String serviceName) {
    NewProcotol request = new NewProcotol();
    request.setFlag("ORANGE_1.1");
    request.setService(serviceName);
    return request;
  }
}
