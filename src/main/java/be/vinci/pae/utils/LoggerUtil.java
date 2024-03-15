package be.vinci.pae.utils;

import java.util.logging.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

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

    public static void logInfo(String message) {
        logger.info(message);
    }
    public static void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}
