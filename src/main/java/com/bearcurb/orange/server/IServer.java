package com.bearcurb.orange.server;

import java.util.List;

public interface IServer {

  public void start() throws InterruptedException;

  public void stop() throws InterruptedException;

  public void registerMessageHandle(String serviceName, IMessageHandle handle);

  public void registerMessageIntercept(List<String> excludes, IMessageIntercept intercept);
}
