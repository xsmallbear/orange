package com.bearcurb.orange.server;

public interface OrangeIntercept {

  public abstract boolean preHandle(OrangeServerContext context);
}