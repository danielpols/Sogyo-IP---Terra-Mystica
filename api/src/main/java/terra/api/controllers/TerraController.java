package terra.api.controllers;

import java.util.Arrays;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;
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

    public static String getSessionId(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("JSESSIONID"))
                .map(c -> c.getValue()).findFirst().get();
    }

    @Path("/get")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpServletRequest request) {
        System.out.println("Call made to /get");

        try {
            String gameId = getSessionId(request);

            GameDTO output = new GameDTO(repository.loadGame(gameId));

            return Response.status(200).entity(output).build();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return Response.status(406).entity("\"Session has no ID.\"")
                    .build();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.status(406)
                    .entity("\"Session ID is not associated with a game.\"")
                    .build();
        }
    }

    @Path("/start")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(@Context HttpServletRequest request,
            StartGameDTO body) {
        System.out.println("Call made to /start");
        String gameId = getSessionId(request);

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
        String gameId = getSessionId(request);

        repository.saveAction(gameId, body.toAction());

        GameDTO output = new GameDTO(repository.loadGame(gameId));
        return Response.status(200).entity(output).build();
    }
}
