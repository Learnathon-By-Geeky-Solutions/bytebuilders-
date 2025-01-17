package org.example;

// import java.sql.SQLException;

public class Tst {
  public String getGreeting() {
    return "Hello World!";
  }

  public static void main(String[] args) {
    System.out.println(new Tst().getGreeting());

    // var dbh = new DBHandle();
    // String tst = "INSERT INTO info (first_name, last_name, email, password)
    // VALUES ('John', 'Doe', 'john.doe@example.com', 'password123');";
    // String[] fields = { "first_name", "last_name", "email", "password" };
    // String[] values = { "Jhn", "De", "jhn.de@example.com", "pssword123" };
    // dbh.add("info", fields, values);
    var ui = new UI();
    String[] tst = ui.inputQuery();
    for (int i = 0; i < tst.length; i++) {
      System.out.println(tst[i]);

    }

  }
}
