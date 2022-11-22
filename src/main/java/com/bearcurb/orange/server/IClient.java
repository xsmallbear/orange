package com.bearcurb.orange.server;

public interface IClient {
  public void connect() throws InterruptedException;

  public void disconnect() throws InterruptedException;
}
