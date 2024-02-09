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
import terra.domain.ITerraMystica;
import terra.domain.ITerraMysticaFactory;
import terra.persistence.ITerraMysticaRepository;

@Path("/terra/api")
public class TerraController {

    private ITerraMysticaFactory factory;
    private ITerraMysticaRepository repository;

    public TerraController(ITerraMysticaFactory factory,
            ITerraMysticaRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

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
    public Response start(@Context HttpServletRequest request,
            StartGameDTO body) {
        System.out.println("Call made to /start");

        HttpSession session = request.getSession(true);
        String gameId = UUID.randomUUID().toString();
        session.setAttribute("gameId", gameId);

        ITerraMystica game = factory.startGame(body.getStartingNames(),
                body.getStartingTerrains(), repository.getStartingTerrain());

        repository.saveGame(gameId, game);

        // Use the game to create a DTO.
        GameDTO output = new GameDTO(game);
        return Response.status(200).entity(output).build();
    }

    @Path("/pass")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response pass(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String gameId = (String) session.getAttribute("gameId");

        ITerraMystica game = repository.loadGame(gameId);

        game.passTurn();

        repository.saveGame(gameId, game);

        GameDTO output = new GameDTO(game);
        return Response.status(200).entity(output).build();

    }

    @Path("/build")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response build(@Context HttpServletRequest request, ActionDTO body) {
        HttpSession session = request.getSession(false);
        String gameId = (String) session.getAttribute("gameId");

        ITerraMystica game = repository.loadGame(gameId);

        game.build(body.getLocation(), body.getBuilding());

        repository.saveGame(gameId, game);

        GameDTO output = new GameDTO(game);
        return Response.status(200).entity(output).build();
    }
}
