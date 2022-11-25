package com.bearcurb.orange;


import com.bearcurb.orange.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestServerBootStrap {

  public static void main(String[] args) throws InterruptedException {
    Logger logger = Logger.getLogger("Test");

    Server server = new Server("127.0.0.1", 8080);
    server.addService("test", context -> logger.info("this is test Server"));
    server.addService("a", context -> logger.info("这里是a的调用"));

    List<String> excludes = new ArrayList<>();
//    excludes.add("a");
    excludes.add("b");
    excludes.add("c");
    server.addIntercept(excludes, context -> {
      System.out.println("拦截器调用 这里拦截掉这个请求");
      return false;
    });

    server.start();
//    Thread.sleep(5000);
//    System.out.println("关闭服务器");
//    server.stop();
  }
}
