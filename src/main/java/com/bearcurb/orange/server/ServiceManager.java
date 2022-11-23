package com.bearcurb.orange.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceManager {
  private Map<String, IService> messageHandles = new HashMap<>();
  private List<IIntercept> interceptList = new ArrayList<>();
  private Map<IIntercept, String[]> interceptExcludes = new HashMap<>();

  private static ServiceManager instance = new ServiceManager();

  public static ServiceManager getInstance() {
    return instance;
  }

  private ServiceManager() {
  }
}
