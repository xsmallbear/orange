package com.bearcurb.orange;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestUseSocket {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("127.0.0.1", 8080));

    for (; ; ) {
      if (socket.isConnected() == false) {
        System.out.println("断开");
        return;
      }
    }

//    socket.close();
  }
}
