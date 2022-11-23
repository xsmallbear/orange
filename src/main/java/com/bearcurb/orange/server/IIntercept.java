package com.bearcurb.orange.server;

public interface IIntercept {

  public abstract boolean preHandle(ServerContext context);
}