package ch.teko.ntb.endpoints;

import ch.teko.ntb.business.AuthorizationManager;
import ch.teko.ntb.model.User;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by antic-software-ing on 04.09.2017.
 */

@Path("register")
public class RegisterRessource extends AbstractInjector {

  private AuthorizationManager authorizationManager;

  public RegisterRessource() {
    authorizationManager = injector.getInstance(AuthorizationManager.class);
  }

  @POST
  public Response signup(@Valid final User user) {
    try {
      int userId = authorizationManager.signup(user);
      return Response.ok().build();
    } catch (Exception e) {
      return Response.status(400).entity("signup fail!").build();
    }
  }

}
