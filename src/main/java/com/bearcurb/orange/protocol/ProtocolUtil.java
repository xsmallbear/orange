package com.bearcurb.orange.protocol;

public class ProtocolUtil {
  public static Request getDefaultNewRequestInstance(String serviceName, String header, String body) {
    Request request = new RequestAddVerify();
    request.setServiceName(serviceName);
    request.setHeader(header);
    request.setBody(body);
    return request;
  }

  public static Response getDefaultNewResponseInstance(int status, String header, String body) {
    Response response = new ResponseAddVerify();
    response.setStatue(status);
    response.setHeader(header);
    response.setBody(body);
    return response;
  }
}
