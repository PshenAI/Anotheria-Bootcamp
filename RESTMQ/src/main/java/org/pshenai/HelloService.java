package org.pshenai;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/echo")
public class HelloService {

    @GET
    @Path("{EchoString}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response greetClient(@PathParam("EchoString") String echo) {
        String output = "Request: " + echo;
        return Response.status(200).entity(output).build();
    }
}
