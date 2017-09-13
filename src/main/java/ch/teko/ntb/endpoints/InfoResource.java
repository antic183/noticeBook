package ch.teko.ntb.endpoints;


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
  private final String MYSQL_CONTAINER_IP = System.getenv("MYSQL_DB_PORT_3306_TCP_ADDR");
  private final String MYSQL_CONTAINER_IP_AND_PORT = System.getenv("MYSQL_DB_PORT_3306_TCP");
  private final String URL = "jdbc:mysql://" + MYSQL_CONTAINER_IP + ":3306";
  private final String USER_NAME = System.getenv("MYSQL_DB_USER");
  private final String PASSWORD = System.getenv("MYSQL_DB_PASSWORD");

  @GET
  @Path("info")
  public Response getInfo() {
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
      return Response.status(200).entity("db connection works!").build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(200).entity(e.getMessage() + ". Sql Connection error").build();
    }
  }

}
