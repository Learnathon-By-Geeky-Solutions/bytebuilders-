package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBHandleTest {
  @Test
  void queryTest() {
    var dbh = new DBHandle();
    String tst = "INSERT INTO info (first_name, last_name, email, password) VALUES ('John', 'Doe', 'john.doe@example.com', 'password123');";
    String[] fields = { "first_name", "last_name", "email", "password" };
    String[] values = { "John", "Doe", "john.doe@example.com", "password123" };
    // assertEquals(tst, dbh.queryInsertTable("info", fields, values));
  }
}
