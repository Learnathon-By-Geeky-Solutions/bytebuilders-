package org.example;

import java.util.Scanner;

public class UI {

  public static String[] inputQuery() {
    Scanner inpObj = new Scanner(System.in);
    System.out.println("Enter First name: ");
    String fname = inpObj.nextLine();
    System.out.println("Enter Last name: ");
    String lname = inpObj.nextLine();
    System.out.println("Enter email: ");
    String email = inpObj.nextLine();
    System.out.println("Enter password: ");
    String pass = inpObj.nextLine();

    inpObj.close();

    String[] inp = { fname, lname, email, pass };

    return inp;
  }
}
