package ch.teko.ntb.endpoints;


import ch.teko.ntb.business.AuthorizationManager;
import ch.teko.ntb.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by antic-software-ing on 04.09.2017.
 */


@Path("/")
public class InfoResource {
  // docker
  private final String MYSQL_CONTAINER_IP = System.getenv("DB_PORT_3306_TCP_ADDR");
  private final String URL = "jdbc:mysql://" + MYSQL_CONTAINER_IP + ":3306/ntb";
  private final String USER_NAME = "ntb_user";//System.getenv("DB_ENV_MYSQL_USER");
  private final String PASSWORD = "123456";//System.getenv("DB_ENV_MYSQL_PASSWORD");

  // local
  /*private final String MYSQL_CONTAINER_IP = "localhost";
  private final String URL = "jdbc:mysql://" + MYSQL_CONTAINER_IP + ":3306";
  private final String USER_NAME = "ntb_user";
  private final String PASSWORD = ".......";*/

  @GET
  @Path("info")
  public Response getInfo() throws Exception {
    String output = "it works! DB Connection available under " + MYSQL_CONTAINER_IP +". ";
    output += USER_NAME + "-" + PASSWORD;
    return Response.status(200).entity(output).build();
  }

  @GET
  @Path("testConnection")
  public Response connectionTest() {
    final Connection conn;

    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
      conn.close();
      return Response.status(200).entity("db connection works!").build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(200).entity(e.getMessage() + ". Sql Connection error").build();
    }
  }

}
