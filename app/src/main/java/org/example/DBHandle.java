package org.example;

import java.sql.SQLException;
import java.sql.Statement;

public class DBHandle {
  public static String queryInsertTable(String table, String[] fields) {

    String sfields = fields[0];
    String vfields = "?";

    for (int i = 1; i < fields.length; i++) {
      sfields = sfields + "," + fields[i];
      vfields = vfields + ",?";
    }

    String qry = "INSERT INTO " + table + " (" + sfields + ")" + " VALUES (" + vfields + ");";
    return qry;

  }

  public static int add(String table, String[] fields, String[] values) {
    String qry = queryInsertTable(table, fields);

    try (var conn = DB.connect();
        var pstmt = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {

      for (int i = 0; i < fields.length; i++) {
        pstmt.setString(i + 1, values[i]);
      }

      // execute the statement and get the inserted id
      int insertedRow = pstmt.executeUpdate();
      if (insertedRow > 0) {
        var rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
