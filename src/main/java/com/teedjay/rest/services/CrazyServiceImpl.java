package com.teedjay.rest.services;

/**
 * Default implementation for the CrzyService just returns a string with a text.
 *
 * @author thore johnsen
 */
public class CrazyServiceImpl implements CrazyService {

    @Override
    public String getCrazyText() {
        return "This is crazy text from the external service";
    }

}
