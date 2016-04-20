package se.henriks.webapp.resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stagemonitor.requestmonitor.MonitorRequests;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@MonitorRequests
@Path("/")
public class HelloWorldResource {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testSimpleGet() {
        LOG.info("Returning HTTP code 200");
        return Response.noContent().build();
    }

    @GET
    @Path("/error")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testError() {
        LOG.warn("Returning HTTP code 500");
        return Response.serverError().build();
    }


    @GET
    @Path("/exception")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testException() {
        LOG.error("Throwing exception...");
        throw new RuntimeException("EXCEPTION");
    }
}
