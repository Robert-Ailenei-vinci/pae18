package be.vinci.pae.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class provides utility methods for loading and retrieving properties from a configuration
 * file.
 */
public class Config {

  private static final Properties props = new Properties();

  static {
    try {
      props.load(new FileInputStream("dev.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads the specified properties file.
   *
   * @param file The name of the properties file to load.
   */
  public static void load(String file) {

    try (InputStream input = new FileInputStream(file)) {
      props.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves the value of the property associated with the specified key.
   *
   * @param key The key of the property to retrieve.
   * @return The value of the property, or null if the key is not found.
   */
  public static String getProperty(String key) {
    return props.getProperty(key);
  }

  /**
   * Retrieves the integer value of the property associated with the specified key.
   *
   * @param key The key of the property to retrieve.
   * @return int value of the property, or 0 if the key is not found or cannot be parsed.
   */
  public static Integer getIntProperty(String key) {
    return Integer.parseInt(props.getProperty(key));
  }

  /**
   * Retrieves the boolean value of the property associated with the specified key.
   *
   * @param key The key of the property to retrieve.
   * @return bool value of the property,or false if the key is not found or cannot be parsed.
   */
  public static boolean getBoolProperty(String key) {
    return Boolean.parseBoolean(props.getProperty(key));
  }
}
