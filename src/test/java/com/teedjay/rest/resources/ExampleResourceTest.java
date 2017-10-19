package com.teedjay.rest.resources;

import com.teedjay.rest.services.TextService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Demonstrates how to test a resource by injecting mocks using mockito and junit.
 *
 * @author thore johnsen
 */
public class ExampleResourceTest {

    @Mock
    private TextService textService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void testExistingUser() {
        User user = exampleResource.fetchUserOrException("victoria");
        assertEquals("victoria", user.name);
    }

    @Test
    public void testBadRequest() {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Missing name");
        exampleResource.fetchUserOrException(null);
    }

    @Test
    public void testEmpytyRequest() {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Missing name");
        exampleResource.fetchUserOrException("");
    }

    @Test
    public void testUserNotFound() {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("No user named usernotfound exists");
        exampleResource.fetchUserOrException("usernotfound"); // special name that triggers exception
    }

}