package com.teedjay.rest.services;

import javax.enterprise.context.Dependent;

/**
 * Implementation that returns a fixed text that will be available to the whole application.
 * Since this is the only implementation it will also become the default implementation of TextService.
 *
 * @author thore johnsen
 */
@Dependent
public class FixedTextService implements TextService {

    @Override
    public String getText() {
        return "This text is from the fixed text service";
    }

}
