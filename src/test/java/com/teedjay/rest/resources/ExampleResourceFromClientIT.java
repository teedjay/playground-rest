package com.teedjay.rest.resources;

import com.teedjay.rest.JAXRSConfig;
import com.teedjay.rest.factories.CrazyServiceFactory;
import com.teedjay.rest.factories.CrazyServiceMockFactory;
import com.teedjay.rest.services.TextService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.is;

/**
 * Tests the example rest resource from "outside" the container.
 *
 * @author thore johnsen
 */
@RunWith(Arquillian.class)
public class ExampleResourceFromClientIT {

    @ArquillianResource
    private URL deploymentURL;

    @Deployment(testable = false)
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

    @Test
    @RunAsClient
    public void fetchExistingUser() {
        String url = deploymentURL.toString() + "rest/example/victoria";
        get(url).then().assertThat().statusCode(200).body("user.name", is("victoria"));
    }

    @Test
    @RunAsClient
    public void testBadRequest() {
        String url = deploymentURL.toString() + "rest/example";
        get(url).then().assertThat().statusCode(405).body(is("HTTP 405 Method Not Allowed"));
    }

    @Test
    @RunAsClient
    public void testNotFound() {
        String url = deploymentURL.toString() + "rest/example/usernotfound";
        get(url).then().assertThat().statusCode(404).body(is("No user named usernotfound exists"));
    }

}


