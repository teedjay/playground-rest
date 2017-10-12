package com.teedjay.rest.factories;

import com.teedjay.rest.services.CrazyService;
import com.teedjay.rest.services.CrazyServiceImpl;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class CrazyServiceFactory {

    @Produces
    public CrazyService createCrazyService() {
        return new CrazyServiceImpl();
    }

}
