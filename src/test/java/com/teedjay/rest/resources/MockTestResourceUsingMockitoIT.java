package com.teedjay.rest.resources;

import com.teedjay.rest.JAXRSConfig;
import com.teedjay.rest.factories.CrazyServiceMockFactory;
import com.teedjay.rest.services.TextService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.is;

/**
 * This test boots the arquillian container with the CrazyServiceMockFactory
 * so that we will get a mocked instance of the CrazyService.
 *
 * @author thore johnsen
 */
@RunWith(Arquillian.class)
public class MockTestResourceUsingMockitoIT {

    @ArquillianResource
    private URL deploymentURL;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackage(UsersResource.class.getPackage())
                .addPackage(TextService.class.getPackage())
                .addClass(CrazyServiceMockFactory.class)
                .addClasses(JAXRSConfig.class)
                ;
    }

    @Test
    @RunAsClient
    public void getTextFromCrazyService() {
        String url = deploymentURL.toString() + "rest/mock";
        get(url).then().assertThat().statusCode(200).body(is("This is text from the crazy mock"));
    }

}