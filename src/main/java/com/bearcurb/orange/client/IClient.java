package com.bearcurb.orange.client;

public interface IClient {
  public void connect() throws InterruptedException;

  public void disconnect() throws InterruptedException;
}
