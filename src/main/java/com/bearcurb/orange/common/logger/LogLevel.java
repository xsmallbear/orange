package com.bearcurb.orange.common.logger;

public enum LogLevel {
  INFO(0), FINE(1), DEBUG(2);

  private int value;

  LogLevel(int value) {
    this.value = value;
  }

  public boolean include(LogLevel level) {
    return value >= level.value;
  }
}
