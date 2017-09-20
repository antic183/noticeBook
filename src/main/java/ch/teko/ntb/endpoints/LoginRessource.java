package ch.teko.ntb.endpoints;

import ch.teko.ntb.business.AuthorizationManager;
import ch.teko.ntb.dto.UserDto;
import ch.teko.ntb.model.User;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by antic-software-ing on 04.09.2017.
 */
@Path("login")
public class LoginRessource extends AbstractInjector {

  private AuthorizationManager authorizationManager;

  public LoginRessource() {
    authorizationManager = injector.getInstance(AuthorizationManager.class);
  }

  @POST
  @Produces({MediaType.APPLICATION_JSON})
  public Response login(@Valid final User user) {
    try {
      UserDto userDto = authorizationManager.login(user);
      return Response.ok().entity(userDto).build();
    } catch (Exception e) {
      System.err.println("error:" + e.getMessage());
      return Response.status(400).entity("login fail!").build();
    }
  }

}
