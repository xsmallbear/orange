package com.bearcurb.orange.common.debug;

import com.bearcurb.orange.common.protocol.Protocol;

public class OrangeProtocolCodecUtil {
  public static void printProtocol(Protocol protocol) {
    System.out.println("--------------------------------------------------");
    System.out.println("flag:" + protocol.getFlag());
    System.out.println("request:" + (protocol.isRequest() ? "to-server" : "to-client"));
    System.out.println("requestId:" + protocol.getRequestId());
    System.out.println("event:" + protocol.getEvent());
    System.out.println("needResult:" + protocol.isNeedResult());
    System.out.println("service:" + protocol.getService());
    System.out.println("data:" + protocol.getData());
    System.out.println("--------------------------------------------------");
  }
}
