package com.bearcurb.orange.protocol;

public class NewProcotol {
  private String flag;
  private boolean request;
  private String requestId;
  private int event;
  private boolean needResult;
  private String service;
  private String data;

  public NewProcotol() {
  }

  public NewProcotol(String flag, boolean request, String requestId, int event, boolean needResult, String service, String data) {
    this.flag = flag;
    this.request = request;
    this.requestId = requestId;
    this.event = event;
    this.needResult = needResult;
    this.service = service;
    this.data = data;
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

  public void setEvent(int event) {
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
