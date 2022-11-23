package com.bearcurb.orange.protocol;

import java.io.Serializable;

public class Response implements Serializable {
  private int statue;
  private String header;
  private String body;

  public Response() {
  }

  public Response(int statue, String header, String body) {
    this.statue = statue;
    this.header = header;
    this.body = body;
  }


  public int getStatue() {
    return statue;
  }

  public void setStatue(int statue) {
    this.statue = statue;
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
