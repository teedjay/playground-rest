package com.teedjay.rest.services;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class MockTestService {

    @Inject
    CrazyService crazyService;

    public String getText() {
        return crazyService.getCrazyText();
    }

}
