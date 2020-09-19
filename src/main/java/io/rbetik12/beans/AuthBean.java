package io.rbetik12.beans;


import io.rbetik12.models.User;
import io.rbetik12.services.UserService;
import io.rbetik12.services.ValidationService;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.Date;

@Singleton
@Path(value = "/auth")
public class AuthBean {

    @EJB
    private ValidationService validationService;

    @EJB
    private UserService userService;

    @Context
    private HttpServletRequest request;

    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) throws IOException {

        if (validationService.validateUser(user) && userService.checkUsernameExistance(user)) {
            userService.createUser(user);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/signin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logIn(User user) throws IOException {
        if (validationService.validateUser(user) && userService.authenticate(user)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("id", user.getId());
            return Response.ok().entity(session.getId()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/logout")
    @POST
    public Response logOut() {
        request.getSession().invalidate();
        return Response.noContent().build();
    }
}
