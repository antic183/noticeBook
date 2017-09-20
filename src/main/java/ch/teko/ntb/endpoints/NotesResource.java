package ch.teko.ntb.endpoints;

import ch.teko.ntb.business.NoticeManager;
import ch.teko.ntb.model.Note;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by antic-software-ing on 04.09.2017.
 */

@Path("notices")
public class NotesResource extends AbstractInjector {

  private NoticeManager noticeManager;

  public NotesResource() {
    noticeManager = injector.getInstance(NoticeManager.class);
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public Response getNotes(@HeaderParam("Authorization") String jwtToken) {
    try {
      List<Note> noticeList = noticeManager.getNotes(jwtToken);
      return Response.ok().entity(noticeList).build();
    } catch (Exception e) {
      return Response.status(400).entity("get notices fail!").build();
    }
  }

  @GET
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON})
  public Response getNote(@PathParam("id") int noticeId, @HeaderParam("Authorization") String jwtToken) {
    try {
      Note note = noticeManager.getNote(noticeId, jwtToken);
      return Response.ok().entity(note).build();
    } catch (Exception e) {
      return Response.status(400).entity("get notice fail!").build();
    }
  }

  @POST
  public Response addNote(@Valid Note notice, @HeaderParam("Authorization") String jwtToken, @Context final UriInfo info) {
    try {
      Note newNotice = noticeManager.addNote(jwtToken, notice);
      int noticeId = newNotice.getId();

      final URI uri = info.getAbsolutePathBuilder().path("" + noticeId).build();
      return Response.ok().header("Location", uri.toString()).build();
    } catch (Exception e) {
      return Response.status(400).entity("add notice fail!").build();
    }
  }

  @PUT
  @Path("{id}")
  public Response updateNote(
      @PathParam("id") int noticeId
      , @Valid Note notice
      , @HeaderParam("Authorization") String jwtToken
      , @Context final UriInfo info
  ) {
    try {
      notice.setId(noticeId);
      noticeManager.updateNote(jwtToken, notice);
      final URI uri = info.getAbsolutePathBuilder().path("" + noticeId).build();
      return Response.ok().header("Location", uri.toString()).build();
    } catch (Exception e) {
      return Response.status(400).entity("change notice fail!").build();
    }
  }

  @DELETE
  @Path("{id}")
    public Response deleteNote(@PathParam("id") int noticeId, @HeaderParam("Authorization") String jwtToken) {
    try {
      noticeManager.deleteNote(jwtToken, noticeId);
      return Response.noContent().build();
    } catch (Exception e) {
      return Response.status(400).entity("delete notice fail!").build();
    }
  }

  @DELETE
  public Response deleteNotes(@HeaderParam("Authorization") String jwtToken) {
    try {
      noticeManager.deleteAllNotes(jwtToken);
      return Response.noContent().build();
    } catch (Exception e) {
      return Response.status(400).entity("delete notices fail!").build();
    }
  }

}
