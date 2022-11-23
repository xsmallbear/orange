package com.bearcurb.orange;

import com.bearcurb.orange.client.Client;
import com.bearcurb.orange.protocol.NewProcotol;
import com.bearcurb.orange.protocol.ProtocolUtil;

import java.io.IOException;

public class TestClientBootStrap {

  public static void main(String[] args) throws IOException, InterruptedException {
    Client client = new Client("127.0.0.1", 8080);
    client.connect();

    NewProcotol message = ProtocolUtil.getDefaultNewRequestInstance("a");
    client.sendMessage(message);

    int i = 0;
    while (true) {
      i++;
    }
  }
}
