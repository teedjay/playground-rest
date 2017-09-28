package com.teedjay.rest.services;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Make sure the fixed service returns the expected text.
 *
 * @author thore johnsen
 */
public class FixedTextServiceTest {

    private FixedTextService fts = new FixedTextService();

    @Test
    public void getText() {
        assertEquals("Expected a fixed well known text","This text is from the fixed text service", fts.getText());
    }

}