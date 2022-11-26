package com.bearcurb.orange.common.protocol;

import java.io.Serializable;

/**
 * Orange Protocol
 */
public class Procotol implements Serializable {
  private String flag = "";
  private boolean request;
  private String requestId = "";
  private int event;
  private boolean needResult;
  private String service = "";
  private String data = "";

  public class EventType {
    public static final int HEART = 0;
    public static final int SIMPLE = 1;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public boolean isRequest() {
    return request;
  }

  public void setRequest(boolean request) {
    this.request = request;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public int getEvent() {
    return event;
  }

  public void setEvent(int Event) {
    this.event = event;
  }

  public boolean isNeedResult() {
    return needResult;
  }

  public void setNeedResult(boolean needResult) {
    this.needResult = needResult;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
