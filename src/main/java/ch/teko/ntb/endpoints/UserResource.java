package ch.teko.ntb.endpoints;

import ch.teko.ntb.business.AuthorizationManager;
import ch.teko.ntb.dto.oUser;
import ch.teko.ntb.model.User;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by antic-software-ing on 04.09.2017.
 */


@Path("user")
public class UserResource extends AbstractInjector {

  private AuthorizationManager authorizationManager;

  public UserResource() {
    authorizationManager = injector.getInstance(AuthorizationManager.class);
  }

  @GET
  @Path("info")
  public Response getInfo() {
    return Response.status(200).entity("it works!").build();
  }

  @POST
  @Path("login")
  public Response login(@Valid final User user) {
    try {
      oUser userDto = authorizationManager.login(user);
      return Response
          .status(200)
          .entity(userDto)
          .build();
    } catch (Exception e) {
      System.err.println("error:" + e.getMessage());
      return Response.status(401).entity("login fail!").build();
    }
  }

  @POST
  @Path("signup")
  public Response signup(@Valid final User user) {
    try {
      int userId = authorizationManager.signup(user);
      return Response
          .status(201)
          .entity("")
          .header("uder-id", userId)
          .build();
    } catch (Exception e) {
      System.err.println("error:" + e.getMessage());
      return Response.status(401).entity("signup fail!").build();
    }
  }
}
