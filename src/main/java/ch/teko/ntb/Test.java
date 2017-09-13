package ch.teko.ntb;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by antic-software-ing on 11.09.2017.
 */
public class Test {

  public static void main(String[] args) {
    final String URL = "jdbc:mysql://172.17.0.2:3306";
    final String USER_NAME = "root";
    final String PASSWORD = "1234";
    final Connection conn;

    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
      System.out.println("db connection works!");
    } catch (Exception e){
      e.printStackTrace();
      System.out.println(e.getMessage());
      System.out.println("db connection error!");
    }
  }
}
