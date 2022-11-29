package com.bearcurb.orange.common.logger;

import java.util.logging.Logger;

public class OrangeLogger {

  public static OrangeLogger instalce;
  public static boolean isInit;

  public static void init(LogLevel logLevel, Logger logger) {
    OrangeLogger.instalce = new OrangeLogger(logLevel, logger);
    OrangeLogger.isInit = true;
  }

  public static OrangeLogger getLogger() {
    return OrangeLogger.instalce;
  }

  private LogLevel logLevel;
  private Logger logger;

  private OrangeLogger(LogLevel logLevel, Logger logger) {
    this.logLevel = logLevel;
    this.logger = logger;
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
