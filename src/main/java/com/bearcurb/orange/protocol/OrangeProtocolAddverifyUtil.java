package com.bearcurb.orange.protocol;

public class OrangeProtocolAddverifyUtil {
  private static final String PROTOCOL_FLAG = "biscuit";
  private static final String PROTOCOL_VERSION = "0.1";


  public static OrangeRequestAddVerify wrapperRequest(OrangeRequest request) {
    OrangeRequestAddVerify requestAddVerify = (OrangeRequestAddVerify) request;
    requestAddVerify.setProtocolFlag(PROTOCOL_FLAG);
    requestAddVerify.setVersion(PROTOCOL_VERSION);
    return requestAddVerify;
  }

  public static OrangeResponseAddVerify wrapperResponse(OrangeResponse response) {
    OrangeResponseAddVerify responseAddVerify = (OrangeResponseAddVerify) response;
    responseAddVerify.setProtocolFlag(PROTOCOL_FLAG);
    responseAddVerify.setVersion(PROTOCOL_VERSION);
    return responseAddVerify;
  }

  public static boolean orangeRequestVerify(OrangeRequestAddVerify request) {
    if (request == null) return false;
    if (request.getProtocolFlag().equals(PROTOCOL_FLAG) == false) return false;
    if (request.getVersion().equals(PROTOCOL_VERSION) == false) return false;
    return true;
  }

  public static boolean orangeResponseVerify(OrangeResponseAddVerify response) {
    if (response == null) return false;
    if (response.getProtocolFlag().equals(PROTOCOL_FLAG) == false) return false;
    if (response.getVersion().equals(PROTOCOL_VERSION) == false) return false;
    return true;
  }
}
