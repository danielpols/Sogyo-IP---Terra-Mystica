package terra.api.controllers;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/terra/api")
public class TerraController {

    @Path("/log")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response log(@Context HttpServletRequest request, String body) {
        System.out.println("Received call on /log");
        return Response.status(200).entity("Hi").build();
    }

    @Path("/start")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(@Context HttpServletRequest request, String body) {
        // Create HTTP session.
        HttpSession session = request.getSession(true);

        // Create a unique ID for this game.
        String gameId = UUID.randomUUID().toString();

        // Save the ID in the HTTP session.
        session.setAttribute("gameId", gameId);

        // Use the game to create a DTO.
        var output = "hi";

        // Send DTO back in response.
        return Response.status(200).entity(output).build();
    }

    @Path("/play")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response play(@Context HttpServletRequest request, String body) {
        // Retrieve HTTP session.
        HttpSession session = request.getSession(false);

        // Retrieve game ID.
        String gameId = (String) session.getAttribute("gameId");

        String output = "Hi";

        // Send DTO back in response.
        return Response.status(200).entity(output).build();
    }
}
