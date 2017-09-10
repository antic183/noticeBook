package ch.teko.ntb.dal.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by antic-software-ing on 09.09.2017.
 */
public class MySqlConnector {
  private final String URL;
  private final String USER_NAME;
  private final String PASSWORD;
  private static Connection conn;
  private static MySqlConnector instance;

  private MySqlConnector() {
    this.URL = "jdbc:mysql://localhost:3306/ntb";
    this.USER_NAME = "ntb_user";
    this.PASSWORD = "1234";
  }

  private void connect() throws SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Sql Connection error");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new SQLException("Sql Connection error");
    }
  }

  protected static MySqlConnector getInstance() throws Exception {
    if (instance == null) {
      instance = new MySqlConnector();
    }

    return instance;
  }

  protected final Connection getConnection() throws Exception {
    if (conn == null) {
      connect();
    }

    return conn;
  }

  protected void closeConnection() throws SQLException {
    if (conn != null) {
      conn.close();
      conn = null;
    }
  }
}