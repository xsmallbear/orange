package com.bearcurb.orange.server;

import com.bearcurb.orange.protocol.NewProcotol;
import io.netty.channel.Channel;

public class ServerContext {
  private Channel channel;
  private NewProcotol protocol;

  public NewProcotol getProtocol() {
    return protocol;
  }

  public void setProtocol(NewProcotol protocol) {
    this.protocol = protocol;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }
}
