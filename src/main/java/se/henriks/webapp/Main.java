package se.henriks.webapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;
import org.stagemonitor.web.WebPlugin;

import javax.servlet.ServletException;

/**
 * Entry point for testing Jersey REST service powered by Jetty, monitored with stagemonitor
 */
public class Main {

    private static final String REST_RESOURCE_PACKAGE = "se.henriks.webapp.resource";

    public static void main(String[] args) throws Exception {

        startServer();
    }

    private static void startServer() throws Exception {

        // Jetty Server
        Server server = new Server(8080);
        final ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        server.setHandler(contextHandler);

        contextHandler.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
            @Override
            public void lifeCycleStarting(LifeCycle event) {
                try {
                    new WebPlugin().onStartup(null, contextHandler.getServletContext());
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            }Tr
        });

        // Set up Jersey
        ServletHolder jerseyServlet = contextHandler.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");

        // Tells the Jersey Servlet which REST service/class(es) to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", REST_RESOURCE_PACKAGE);
        jerseyServlet.setInitOrder(0);

        // Use SLF4J as logging API.
        Log.setLog(new Slf4jLog());

        server.start();
        server.join();
    }
}
