package be.vinci.pae.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger Class.
 */
public class LoggerUtil {

  private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());

  static {
    try {
      // Define the directory where log files will be stored
      String logDir = System.getProperty("user.dir") + "/logs/";
      // Define the log file pattern
      String logFilePattern = logDir + "log";

      // Create the logs directory if it doesn't exist
      Path logsDir = Paths.get(logDir);
      if (!Files.exists(logsDir)) {
        Files.createDirectories(logsDir);
      }

      // Configure the logger to write log messages to a file
      FileHandler fileHandler = new FileHandler(logFilePattern, 100000, 15, true);
      fileHandler.setFormatter(new SimpleFormatter() {
        @Override
        public synchronized String format(LogRecord lr) {
          String format = "[%1$tF %1$tT] [%2$s] %3$s %n";
          return String.format(format,
              new Date(lr.getMillis()),
              lr.getLevel().getName(),
              lr.getMessage()
          );
        }
      });
      logger.addHandler(fileHandler);
    } catch (IOException e) {
      // Handle the exception if unable to create the file handler
      logger.log(Level.SEVERE, "Error creating file handler", e);
    }
  }

  /**
   * the lofInfo.
   *
   * @param message the message.
   */
  public static void logInfo(String message) {
    logger.info(message);
  }

  /**
   * add a error in log.
   *
   * @param message   the message.
   * @param throwable the upstream exception.
   */
  public static void logError(String message, Throwable throwable, int errorCode) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    throwable.printStackTrace(pw);
    String stackTrace = sw.toString();
    Level logLevel = mapErrorCodeToLogLevel(errorCode);
    logger.log(logLevel, message + "\n" + stackTrace);
  }

  private static Level mapErrorCodeToLogLevel(int errorCode) {
    // Define mapping logic for HTTP error codes to logging levels
    switch (errorCode) {
      case 1:
        return Level.FINE; // Presentation Error
      case 2:
        return Level.WARNING; // Business Error
      case 3:
        return Level.SEVERE; // Services Error
      default:
        return Level.INFO; // Default level for unknown error codes
    }
  }
}
