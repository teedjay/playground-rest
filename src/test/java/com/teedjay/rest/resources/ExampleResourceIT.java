package com.teedjay.rest.resources;

import com.teedjay.rest.JAXRSConfig;
import com.teedjay.rest.factories.CrazyServiceFactory;
import com.teedjay.rest.factories.CrazyServiceMockFactory;
import com.teedjay.rest.services.TextService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class ExampleResourceIT {

    @Inject
    private ExampleResource exampleResource;

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackage(CrazyServiceFactory.class.getPackage())
                .addPackage(ExampleResource.class.getPackage())
                .addPackage(TextService.class.getPackage())
                .addClasses(JAXRSConfig.class)
                .filter(Filters.exclude(CrazyServiceMockFactory.class))
                ;
    }

    @Before
    public void makeSureExampleResourceHasBeenInjected() {
        assertNotNull("ExampleResource should be injected by container", exampleResource);
    }

    @Test
    public void makeSureCreateUserUsesFixedTextService() {
        User userToBeCreated = new User("ola", 37, "lt10chars", null);
        User createdUserWithText = exampleResource.createUser(userToBeCreated);
        assertEquals("User should have default text from the real fixed text service", "This text is from the fixed text service", createdUserWithText.text);
    }

}


