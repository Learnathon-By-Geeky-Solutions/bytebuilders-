package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
  public static Connection connect() throws SQLException {

    try {
      // Get info from the DBConf class
      var jdbcUrl = DBConf.getDbUrl();
      var user = DBConf.getDbUsrName();
      var password = DBConf.getDbPss();

      // Open a connection
      return DriverManager.getConnection(jdbcUrl, user, password);

    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return null;
    }
  }
}
