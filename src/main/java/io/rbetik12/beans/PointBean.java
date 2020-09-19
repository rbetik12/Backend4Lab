package io.rbetik12.beans;

import io.rbetik12.models.Point;
import io.rbetik12.services.PointService;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Path(value = "/point")
public class PointBean {

    @EJB
    private PointService pointService;

    @Context
    private HttpServletRequest request;

    @Path("/check")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response check(Point point) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        else {
            pointService.savePoint(point, (long) session.getAttribute("id"));
            return Response.noContent().build();
        }
    }

    @Path("/getUserPoints")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Point> getUserPoints(@Context HttpServletResponse resp) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            resp.setStatus(403);
            return new ArrayList<>();
        }
        else {
            resp.setStatus(200);
            return pointService.getUserPoints((long) session.getAttribute("id"));
        }
    }
}
