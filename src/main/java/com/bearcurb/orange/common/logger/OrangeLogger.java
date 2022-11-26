package com.bearcurb.orange.common.logger;

import java.util.logging.Logger;

public class OrangeLogger {
  private LogLevel logLevel;
  private Logger logger;

  public OrangeLogger(Logger logger) {
    this.logger = logger;
    logLevel = LogLevel.INFO;
  }

  public void setLogLevel(LogLevel logLevel) {
    this.logLevel = logLevel;
  }

  public void info(String message) {
    logger.info(message);
  }

  public void fine(String message) {
    logger.fine(message);
  }

  public void debug(String message) {
    if (logLevel.include(LogLevel.DEBUG)) {
      info("[DEBUG]" + message);
    }
  }
}
