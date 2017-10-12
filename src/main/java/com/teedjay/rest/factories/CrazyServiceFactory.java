package com.teedjay.rest.factories;

import com.teedjay.rest.services.CrazyService;
import com.teedjay.rest.services.CrazyServiceImpl;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

/**
 * CDI producer used in production that creates real CrazyServiceImpl
 * instances of CrazyService.
 *
 * @author thore johnsen
 */
@Singleton
public class CrazyServiceFactory {

    @Produces
    public CrazyService createCrazyService() {
        return new CrazyServiceImpl();
    }

}
