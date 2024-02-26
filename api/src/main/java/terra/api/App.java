package terra.api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import terra.api.controllers.TerraController;
import terra.domain.ITerraMysticaFactory;
import terra.domain.TerraMysticaFactory;
import terra.persistence.ITerraMysticaRepository;
import terra.persistence.TerraMysticaDatabase;
import terra.persistence.TerraMysticaRepository;

public class App {
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = createServer(PORT);
        server.start();

        System.out.println("Started server.");
        System.out.format("Listening on http://localhost:%d/%n", PORT);
        System.out.println("Press CTRL+C to exit.");

        server.join();
    }

    private static Server createServer(int port) {
        var server = new Server(port);

        ServletContextHandler context = createStatefulContext(server);
        registerServlets(context);

        return server;
    }

    private static ServletContextHandler createStatefulContext(Server server) {
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        return context;
    }

    private static void registerServlets(ServletContextHandler context) {
        // Use the Jersey framework to translate the methods in the
        // MancalaController class to server endpoints (servlets).
        // For example, the start method will become an endpoint at
        // http://localhost:8080/mancala/api/start
        context.addServlet(
                new ServletHolder(new ServletContainer(createResources())),
                "/*");
    }

    private static ResourceConfig createResources() {
        // Create the dependencies we want to inject
        ITerraMysticaFactory factory = new TerraMysticaFactory();
        ITerraMysticaRepository repository = new TerraMysticaRepository(
                new TerraMysticaDatabase(), factory);
        TerraController controller = new TerraController(repository);
        // Register our MancalaController
        return new ResourceConfig().register(controller);
        // Note: Jetty (and most other frameworks) can also handle Dependency
        // Injection and registering controllers automatically.
    }
}
