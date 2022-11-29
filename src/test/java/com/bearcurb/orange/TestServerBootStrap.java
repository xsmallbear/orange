package com.bearcurb.orange;


import com.bearcurb.orange.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestServerBootStrap {

  public static void main(String[] args) throws InterruptedException {
    Logger logger = Logger.getLogger("Test");

    Server server = new Server("127.0.0.1", 8080, Logger.getLogger("default"));
    server.registerMessageHandle("test", context -> logger.info("this is test Server"));
    server.registerMessageHandle("a", context -> logger.info("这里是a的调用"));

    List<String> excludes = new ArrayList<>();
//    excludes.add("a");
    excludes.add("b");
    excludes.add("c");
    server.registerMessageIntercept(excludes, context -> {
      System.out.println("拦截器调用 这里拦截掉这个请求");
      return false;
    });

    try {
      server.start();
    } catch (Exception ok) {
      System.out.println("启动失败");
      server.stop();
    }
  }
}
