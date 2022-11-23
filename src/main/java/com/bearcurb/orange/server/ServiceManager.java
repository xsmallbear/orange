package com.bearcurb.orange.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceManager {
  private Map<String, IService> services = new HashMap<>();
  private List<IIntercept> intercepts = new ArrayList<>();
  private Map<IIntercept, List<String>> interceptExcludes = new HashMap<>();

  private static ServiceManager instance = new ServiceManager();

  public static ServiceManager getInstance() {
    return instance;
  }

  private ServiceManager() {
  }

  public void registerService(String serviceName, IService service) {
    this.services.put(serviceName, service);
  }

  public void registerIntercept(List<String> excludes, IIntercept intercept) {
    intercepts.add(intercept);
    interceptExcludes.put(intercept, excludes);
  }

  public IService getService(String name) {
    return this.services.get(name);
  }

  public List<IIntercept> getServiceIntercept(String name) {
    List<IIntercept> resultIntercept = new ArrayList<>();
    intercepts.forEach(intercept -> {
      if (interceptExcludes.get(intercept).indexOf(name) < 0) resultIntercept.add(intercept);
    });
    return resultIntercept;
  }
}
