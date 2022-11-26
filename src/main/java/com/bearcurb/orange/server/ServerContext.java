package com.bearcurb.orange.server;

import com.bearcurb.orange.common.protocol.Procotol;
import io.netty.channel.Channel;

public class ServerContext {
  private Channel channel;
  private Procotol protocol;

  public Procotol getProtocol() {
    return protocol;
  }

  public void setProtocol(Procotol protocol) {
    this.protocol = protocol;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }
}
