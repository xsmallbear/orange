package com.bearcurb.orange.server;

import com.bearcurb.orange.protocol.Request;
import io.netty.channel.Channel;

public class ServerContext {
  private Channel channel;
  private Request protocol;

  public Request getProtocol() {
    return protocol;
  }

  public void setProtocol(Request protocol) {
    this.protocol = protocol;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }
}
