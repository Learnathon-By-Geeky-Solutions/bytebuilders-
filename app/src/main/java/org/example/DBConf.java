package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConf {
  private static final Properties properties = new Properties();

  static {
    try (InputStream input = DBConf.class.getClassLoader().getResourceAsStream("db.properties")) {
      if (input == null) {
        System.out.println("db.properties not found !");
        System.exit(1);
      }

      properties.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getDbUrl() {

    return properties.getProperty("db.url");
  }

  public static String getDbUsrName() {
    return properties.getProperty("db.username");
  }

  public static String getDbPss() {
    return properties.getProperty("db.password");
  }
}
