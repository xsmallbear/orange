package com.bearcurb.orange.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandleManager {
  private Map<String, IMessageHandle> messageHandles = new HashMap<>();
  private List<IMessageIntercept> messageIntercepts = new ArrayList<>();
  private Map<IMessageIntercept, List<String>> interceptExcludes = new HashMap<>();

  private static MessageHandleManager instance = new MessageHandleManager();

  public static MessageHandleManager getInstance() {
    return instance;
  }

  private MessageHandleManager() {
  }

  public void registerMessageHandle(String serviceName, IMessageHandle service) {
    this.messageHandles.put(serviceName, service);
  }

  public void registerMessageIntercept(List<String> excludes, IMessageIntercept intercept) {
    messageIntercepts.add(intercept);
    interceptExcludes.put(intercept, excludes);
  }

  public IMessageHandle getMessageHandle(String name) {
    return this.messageHandles.get(name);
  }

  public List<IMessageIntercept> getMessageIntercept(String name) {
    List<IMessageIntercept> resultIntercept = new ArrayList<>();
    messageIntercepts.forEach(intercept -> {
      if (interceptExcludes.get(intercept).indexOf(name) < 0) resultIntercept.add(intercept);
    });
    return resultIntercept;
  }
}
