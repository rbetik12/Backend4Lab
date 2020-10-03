package io.rbetik12.beans;

import javax.ejb.Stateful;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateful
public class TimeBean {

    @Path("/time")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTime() {
        return Response.ok().entity(System.currentTimeMillis()).build();
    }

}
