package com.bearcurb.orange;

import java.util.logging.Logger;

public class TestCallLogger {
  public static void main(String[] args) {
    Logger logger = Logger.getAnonymousLogger();
    logger.info("[Hello]Hello");
    logger.info("Hello");
    logger.info("Hello");
    logger.info("Hello");
  }
}
