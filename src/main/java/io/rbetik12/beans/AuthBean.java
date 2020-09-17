package io.rbetik12.beans;


import io.rbetik12.models.User;
import io.rbetik12.services.UserService;
import io.rbetik12.services.ValidationService;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Singleton
@Path(value = "/auth")
public class AuthBean {

    @EJB
    private ValidationService validationService;

    @EJB
    private UserService userService;

    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) throws IOException {

        if (validationService.validateUser(user) && userService.checkUsernameExistance(user)) {
            System.out.println("User is ok!");
            userService.createUser(user);
            return Response.noContent().build();
        }
        else {
            System.out.println("User is not ok!");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/signin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logIn(User user) throws IOException {

        if (validationService.validateUser(user) && userService.authenticate(user)) {
            System.out.println("Successfully authenticated!");
            return Response.noContent().build();
        }
        else {
            System.out.println("User is not authenticated!");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/test")
    @GET
    public String test(@Context HttpServletResponse resp,
                       @Context HttpServletRequest req) throws IOException {
        return "It works!";
    }
}
