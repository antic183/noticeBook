package ch.teko.ntb.dal.mysql;

import ch.teko.ntb.business.util.SecureHash;
import ch.teko.ntb.dal.interfaces.IAuthorizationDal;
import ch.teko.ntb.dto.UserDto;
import ch.teko.ntb.model.User;

import java.sql.*;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public class AuthorizationMySqlDal implements IAuthorizationDal {

  private static final String TABLE_USER = "user";
  private MySqlConnector mySqlConnector;

  public AuthorizationMySqlDal() {
    try {
      mySqlConnector = MySqlConnector.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Override
  public UserDto login(User user) throws Exception {
    Connection connection;
    UserDto userDto = null;
    boolean error = false;
    String errorMsg = "";

    try {
      connection = mySqlConnector.getConnection();
      String query = "SELECT * FROM " + TABLE_USER + " u WHERE u.email=?";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, user.getEmail());

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        int dbUserId = rs.getInt("u.id");
        String dbEmail = rs.getString("u.email");
        String dbPassword = rs.getString("u.password");

        if (SecureHash.checkPassword(user.getPassword(), dbPassword)) {
          userDto = new UserDto(dbUserId, dbEmail);
        } else {
          errorMsg = "invalid password";
          throw new SQLException(errorMsg);
        }
      } else {
        error = true;
        errorMsg = "user doesn't exist.";
      }

      rs.close();
      preparedStatement.close();
    } catch (SQLException e) {
      error = true;
      errorMsg = "Sql login error: " + e.getMessage();
      e.printStackTrace();
    } finally {
      try {
        mySqlConnector.closeConnection();
      } catch (SQLException e) {
        error = true;
        errorMsg = "Sql login: close connection error.";
        e.printStackTrace();
      }

      if (error) {
        throw new Exception(errorMsg);
      }

      return userDto;
    }
  }


  @Override
  public int signup(User user) throws Exception {
    Connection connection;
    UserDto userDto = null;
    boolean error = false;
    String errorMsg = "";
    int userId = 0;
    String hashedPassword = SecureHash.hashPassword(user.getPassword());

    try {
      connection = mySqlConnector.getConnection();

      // insert new customer account
      String query = "INSERT INTO " + TABLE_USER + "(email, password, created_at) VALUES(?, ?, NOW())";
      PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, user.getEmail());
      preparedStatement.setString(2, hashedPassword);
      preparedStatement.execute();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        userId = rs.getInt(1);
      } else {
        error = true;
        errorMsg = "Sql signup error. User doesn't added.";
      }
      rs.close();
      preparedStatement.close();
    } catch (SQLException e) {
      error = true;
      errorMsg = "Sql signup error: " + e.getMessage();
      e.printStackTrace();
    } finally {
      try {
        mySqlConnector.closeConnection();
      } catch (SQLException e) {
        e.printStackTrace();
        error = true;
        errorMsg = "Sql signup: close connection error";
      }

      if (error) {
        throw new Exception(errorMsg);
      }

      return userId;
    }
  }
}
