package io.rbetik12.beans;

import io.rbetik12.models.Point;
import io.rbetik12.services.PointService;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            System.out.println("Bad session!");
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        else {
            System.out.println("lol");
            pointService.savePoint(point, (long) session.getAttribute("id"));
            return Response.noContent().build();
        }
    }
}
