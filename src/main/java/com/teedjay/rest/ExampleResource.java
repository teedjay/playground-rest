package com.teedjay.rest;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This is a simple example that show how to use a simple data structure (Java class)
 * as input and output to your REST functions.
 *
 *  @author thore johnsen
 */
@Path("example")
@Produces(MediaType.APPLICATION_XML)
public class ExampleResource {

    @POST
    public User createUser(@Valid User input) {
        return input;
    }

}
