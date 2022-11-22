package com.bearcurb.orange.protocol;

import java.io.Serializable;

public class OrangeRequest implements Serializable {
  private String serviceName;
  private String header;
  private String body;

  public OrangeRequest() {
  }

  public OrangeRequest(String serviceName, String header, String body) {
    this.serviceName = serviceName;
    this.header = header;
    this.body = body;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
