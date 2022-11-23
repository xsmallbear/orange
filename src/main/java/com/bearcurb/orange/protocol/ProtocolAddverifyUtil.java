package com.bearcurb.orange.protocol;

public class ProtocolAddverifyUtil {
  private static final String PROTOCOL_FLAG = "biscuit";
  private static final String PROTOCOL_VERSION = "0.1";


  public static RequestAddVerify wrapperRequest(Request request) {
    RequestAddVerify requestAddVerify = (RequestAddVerify) request;
    requestAddVerify.setProtocolFlag(PROTOCOL_FLAG);
    requestAddVerify.setVersion(PROTOCOL_VERSION);
    return requestAddVerify;
  }

  public static ResponseAddVerify wrapperResponse(Response response) {
    ResponseAddVerify responseAddVerify = (ResponseAddVerify) response;
    responseAddVerify.setProtocolFlag(PROTOCOL_FLAG);
    responseAddVerify.setVersion(PROTOCOL_VERSION);
    return responseAddVerify;
  }

  public static boolean orangeRequestVerify(RequestAddVerify request) {
    if (request == null) return false;
    if (request.getProtocolFlag().equals(PROTOCOL_FLAG) == false) return false;
    if (request.getVersion().equals(PROTOCOL_VERSION) == false) return false;
    return true;
  }

  public static boolean orangeResponseVerify(ResponseAddVerify response) {
    if (response == null) return false;
    if (response.getProtocolFlag().equals(PROTOCOL_FLAG) == false) return false;
    if (response.getVersion().equals(PROTOCOL_VERSION) == false) return false;
    return true;
  }
}
