package com.teedjay.rest.services;

public class CrazyServiceImpl implements CrazyService {

    @Override
    public String getCrazyText() {
        return "This is crazy text from the external service";
    }

}
