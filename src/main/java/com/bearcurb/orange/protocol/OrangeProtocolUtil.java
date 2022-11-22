package com.bearcurb.orange.protocol;

import com.bearcurb.orange.protocol.handle.OrangeRequestAddVerify;
import com.bearcurb.orange.protocol.handle.OrangeResponseAddVerify;

public class OrangeProtocolUtil {
  public static OrangeRequest getDefaultNewRequestInstance(String serviceName, String header, String body) {
    OrangeRequest request = new OrangeRequestAddVerify();
    request.setServiceName(serviceName);
    request.setHeader(header);
    request.setBody(body);
    return request;
  }

  public static OrangeResponse getDefaultNewResponseInstance(int status, String header, String body) {
    OrangeResponse response = new OrangeResponseAddVerify();
    response.setStatue(status);
    response.setHeader(header);
    response.setBody(body);
    return response;
  }
}
