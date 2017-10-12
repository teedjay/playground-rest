package com.teedjay.rest.resources;


import com.teedjay.rest.services.MockTestService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Resource used to teste injected mocks when using arquillian for client testing.
 *
 * @author thore johnsen
 */
@Path("mock")
@Produces(MediaType.APPLICATION_XML)
@RequestScoped
public class MockTestResource {

    @Inject
    MockTestService service;

    @GET
    public String getTextFromExternalService() {
        return service.getText();
    }

}
