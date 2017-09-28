package com.teedjay.rest.resources;

import com.teedjay.rest.services.TextService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * Demonstrates how to test a resource by injecting mocks using mockito and junit.
 *
 * @author thore johnsen
 */
public class ExampleResourceTest {

    @Mock
    private TextService textService;

    @InjectMocks
    private ExampleResource exampleResource = new ExampleResource();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(textService.getText()).thenReturn("This text is from the mock");
    }

    @Test
    public void makeSureMockWasUsed() {
        User userToBeCreated = new User();
        User createdUserWithText = exampleResource.createUser(userToBeCreated);
        assertEquals("TextService did not return expected mock text", "This text is from the mock", createdUserWithText.text);
    }

}