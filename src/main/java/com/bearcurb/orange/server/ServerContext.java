package com.bearcurb.orange.server;

import com.bearcurb.orange.common.protocol.Protocol;
import io.netty.channel.Channel;

public class ServerContext {
  private Channel channel;
  private Protocol protocol;

  public Protocol getProtocol() {
    return protocol;
  }

  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }
}
