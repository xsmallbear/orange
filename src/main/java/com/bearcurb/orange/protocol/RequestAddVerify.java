package com.bearcurb.orange.protocol;

public class RequestAddVerify extends Request {
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
