package com.teedjay.rest.factories;

import com.teedjay.rest.services.CrazyService;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

/**
 * CDI producer that creates a mocked instance of CrazyService.
 * This producer is part of the test classes and will not be used in production.
 *
 * @author thore johnsen
 */
@Singleton
public class CrazyServiceMockFactory {

    @Produces
    public CrazyService createCrazyService() {
        CrazyService theMock = Mockito.mock(CrazyService.class);
        Mockito.when(theMock.getCrazyText()).thenReturn("This is text from the crazy mock");
        return theMock;
    }

}
