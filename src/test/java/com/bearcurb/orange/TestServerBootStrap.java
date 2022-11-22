package com.bearcurb.orange;


import com.bearcurb.orange.server.OrangeHandle;
import com.bearcurb.orange.server.OrangeIntercept;
import com.bearcurb.orange.server.OrangeServer;
import com.bearcurb.orange.server.OrangeServerContext;

public class TestServerBootStrap {

  public static void main(String[] args) throws InterruptedException {
    OrangeServer server = new OrangeServer("127.0.0.1", 8080);
    server.addHandle("test", new OrangeHandle() {
      @Override
      public void handle(OrangeServerContext context) {
        System.out.println("this is test Server");
      }
    });
    server.addHandle("a", new OrangeHandle() {
      @Override
      public void handle(OrangeServerContext context) {
        System.out.println("这里是a的调用");
      }
    });

    server.addIntercept(new String[]{"a", "b", "c"}, new OrangeIntercept() {
      @Override
      public boolean preHandle(OrangeServerContext context) {
        System.out.println("拦截器调用 这里拦截掉这个请求");
        return false;
      }
    });

    server.start();
    int i = 0;
    while (true) {
      i++;
    }
  }
}
