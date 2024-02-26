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
import terra.api.models.ActionDTO;
import terra.api.models.GameDTO;
import terra.api.models.StartGameDTO;
import terra.persistence.ITerraMysticaRepository;

@Path("/terra/api")
public class TerraController {

    private ITerraMysticaRepository repository;

    public TerraController(ITerraMysticaRepository repository) {
        this.repository = repository;
    }

    @Path("/get")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpServletRequest request) {
        System.out.println("Call made to /get");

        HttpSession session = request.getSession(false);

        if (session == null) {
            return Response.status(404).entity("No session found.").build();
        }

        String gameId = (String) session.getAttribute("gameId");
        if (gameId == null) {
            return Response.status(404)
                    .entity("Session is not associated with a game.").build();
        }

        GameDTO output = new GameDTO(repository.loadGame(gameId));

        return Response.status(200).entity(output).build();
    }

    @Path("/start")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(@Context HttpServletRequest request,
            StartGameDTO body) {
        System.out.println("Call made to /start");

        HttpSession session = request.getSession(true);

        String gameId = UUID.randomUUID().toString();
        session.setAttribute("gameId", gameId);

        repository.initialiseGame(gameId, body.getStartingNames(),
                body.getStartingTerrains());

        // Use the game to create a DTO.
        GameDTO output = new GameDTO(repository.loadGame(gameId));
        return Response.status(200).entity(output).build();
    }

    @Path("/act")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response act(@Context HttpServletRequest request, ActionDTO body) {
        System.out.println("Call made to /act");

        HttpSession session = request.getSession(false);
        String gameId = (String) session.getAttribute("gameId");

        repository.saveAction(gameId, body.toAction());

        GameDTO output = new GameDTO(repository.loadGame(gameId));
        return Response.status(200).entity(output).build();
    }
}
