package com.bearcurb.orange.server;

public interface IMessageIntercept {

  public abstract boolean preHandle(ServerContext context);
}