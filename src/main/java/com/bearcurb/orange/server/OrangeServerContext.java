package com.bearcurb.orange.server;

import com.bearcurb.orange.protocol.OrangeRequest;
import io.netty.channel.Channel;

public class OrangeServerContext {
  private Channel channel;
  private OrangeRequest protocol;

  public OrangeRequest getProtocol() {
    return protocol;
  }

  public void setProtocol(OrangeRequest protocol) {
    this.protocol = protocol;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }
}
