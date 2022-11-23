package com.bearcurb.orange;


import com.bearcurb.orange.server.Server;

import java.util.logging.Logger;

public class TestServerBootStrap {

  public static void main(String[] args) throws InterruptedException {
    Logger logger = Logger.getLogger("Test");

    Server server = new Server("127.0.0.1", 8080);
    server.addHandle("test", context -> logger.info("this is test Server"));
    server.addHandle("a", context -> logger.info("这里是a的调用"));

    server.addIntercept(new String[]{"a", "b", "c"}, context -> {
      System.out.println("拦截器调用 这里拦截掉这个请求");
      return false;
    });

    server.start();
    int i = 0;
    while (true) {
      i++;
    }
  }
}
