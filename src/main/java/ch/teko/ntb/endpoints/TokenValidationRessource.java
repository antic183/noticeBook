package ch.teko.ntb.endpoints;

import ch.teko.ntb.business.AuthorizationManager;
import ch.teko.ntb.business.util.TokenHandler;
import ch.teko.ntb.dto.UserDto;
import ch.teko.ntb.model.Note;
import ch.teko.ntb.model.User;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by antic-software-ing on 04.09.2017.
 */
@Path("validatetoken")
public class TokenValidationRessource {

  @GET
  @Path("get")
  public Response isTokenValid(@HeaderParam("Authorization") String jwtToken) {
    try {
      TokenHandler.getUserIdByToken(jwtToken);
      return Response.noContent().build();
    } catch (Exception e) {
      return Response.status(401).entity("invalid token!").build();
    }
  }

}
