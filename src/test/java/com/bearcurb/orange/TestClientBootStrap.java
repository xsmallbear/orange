package com.bearcurb.orange;

import com.bearcurb.orange.client.OrangeClient;
import com.bearcurb.orange.protocol.OrangeProtocolUtil;
import com.bearcurb.orange.protocol.OrangeRequest;

import java.io.IOException;

public class TestClientBootStrap {

  public static void main(String[] args) throws IOException, InterruptedException {
    OrangeClient client = new OrangeClient("127.0.0.1", 8080);
    client.connect();

    OrangeRequest message = OrangeProtocolUtil.getDefaultNewRequestInstance("a", "1", "2");
    client.sendMessage(message);

    int i = 0;
    while (true) {
      i++;
    }
  }
}
