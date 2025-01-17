package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class RunFile {

  public static void executeFile(String path) throws SQLException {
    try (FileReader reader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(reader);
        // Establish connection to the database.
        Connection connection = DB.connect();
        Statement statement = connection.createStatement();) {

      System.out.println("Executing file : " + path);

      StringBuilder builder = new StringBuilder();

      String line;
      int lineNumber = 0;
      int count = 0;

      // Read lines from the file until the end of the file is reached.
      while ((line = bufferedReader.readLine()) != null) {
        lineNumber += 1;
        line = line.trim();

        // Skip empty lines and single-line comments.
        if (line.isEmpty() || line.startsWith("--"))
          continue;

        builder.append(line);
        // If the line ends with a semicolon, it is the end of an SQL command.
        if (line.endsWith(";"))
          try {
            // Execute the SQL command
            statement.execute(builder.toString());
            // an output along with the first 30 characters of the executed
            // command.
            System.out.println(
                ++count
                    + " Successfully executed : "
                    + builder.substring(
                        0,
                        Math.min(builder.length(), 30))
                    + "...");
            builder.setLength(0);
          } catch (SQLException e) {
            System.err.println(
                "At line " + lineNumber + " : "
                    + e.getMessage() + "\n");
            return;
          }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
