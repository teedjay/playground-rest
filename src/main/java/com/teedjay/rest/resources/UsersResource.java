package com.teedjay.rest.resources;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * A demo user resource to demonstrate how to test REST using arquillian.
 *
 * This resource only produce JSON responess and the create user consumes JSON only.
 *
 * @author thore johnsen
 */
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UsersResource {

    @GET
    @Path("{name}")
    public User getUserByName(@PathParam("name") String name) {
        return new User(name, 17, "myaddress", "knownuser");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid User newUser, @Context UriInfo uriInfo) {
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(newUser.name);
        return Response.created(builder.build()).build();
    }

}
