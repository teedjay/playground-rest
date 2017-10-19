package com.teedjay.rest.resources;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps client errors thrown from the REST API and maps it to simple
 * simple text/plain content (instead of default container html pages).
 *
 * @author thore johnsen
 */
@Provider
public class ClientErrorResponseMapper implements ExceptionMapper<ClientErrorException> {

    @Override
    public Response toResponse(ClientErrorException e) {
        return Response
                .status(e.getResponse().getStatus())
                .type(MediaType.TEXT_PLAIN)
                .entity(e.getMessage())
                .build();
    }

}
