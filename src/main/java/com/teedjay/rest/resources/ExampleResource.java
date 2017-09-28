package com.teedjay.rest.resources;

import com.teedjay.rest.services.TextService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This is a simple example that show how to use a simple data structure (Java class)
 * as input and output to your REST functions.
 *
 * This resource can provide both XML and JSON response, XML will be default since it
 * is first in the supported list.
 *
 * Having this as RequestScoped means that this REST resource will only have a scope
 * of the duration of the request.  Next request will get a new instance.
 *
 * Alternatively it could be annotated as Stateless (stateless ejb) and be part
 * of standard EJB resource pooling with transactional support.
 *
 * @author thore johnsen
 */
@Path("example")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@RequestScoped
public class ExampleResource {

    @Inject
    TextService textService;

    @POST
    public User createUser(@Valid User input) {
        input.text = textService.getText();
        return input;
    }

}
