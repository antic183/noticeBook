package ch.teko.ntb.endpoints;


import ch.teko.ntb.business.util.TokenHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by antic-software-ing on 04.09.2017.
 */
@Path("validatetoken")
public class TokenValidationRessource {

  @GET
  public Response isTokenValid(@HeaderParam("Authorization") String jwtToken) {
    try {
      String cleanToken = jwtToken.replace("Bearer ", "");
      TokenHandler.getUserIdByToken(cleanToken);
      return Response.noContent().build();
    } catch (Exception e) {
      return Response.status(401).entity("invalid token!").build();
    }
  }

}
