package com.bearcurb.orange;

import com.bearcurb.orange.client.Client;

import java.io.IOException;

public class TestClientBootStrap {

  public static void main(String[] args) throws IOException, InterruptedException {
    Client client = new Client("127.0.0.1", 8080);
    client.connect();
    System.out.println("hi");
  }
}
