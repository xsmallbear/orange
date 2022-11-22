package com.bearcurb.orange.protocol.handle;

import com.bearcurb.orange.protocol.OrangeRequest;

public class OrangeRequestAddVerify extends OrangeRequest {
  private String protocolFlag;
  private String version;

  public String getProtocolFlag() {
    return protocolFlag;
  }

  public void setProtocolFlag(String protocolFlag) {
    this.protocolFlag = protocolFlag;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
