package com.teedjay.rest.resources;

import com.teedjay.rest.JAXRSConfig;
import com.teedjay.rest.services.TextService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.net.URL;

/**
 * Integration test using Arquillian (embedded Payara container configured in pom.xml)
 *
 * @author thore johnsen
 */
@RunWith(Arquillian.class)
public class UsersResourceIT {

    @ArquillianResource
    private URL deploymentURL;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackage(UsersResource.class.getPackage())
                .addPackage(TextService.class.getPackage())
                .addClasses(JAXRSConfig.class)
                ;
    }

    @Test
    @RunAsClient
    public void printCurrentDeploymentConfiguration() throws Exception {
        if (deploymentURL == null) {
            System.out.println("DeploymentURL was null, expected http://localhost:8181/6a437b3f-fbf7-482d-b49f-a731e68d270a/");
        } else {
            System.out.printf("DeploymentURL : %s and external form = %s\n", deploymentURL.toString(), deploymentURL.toExternalForm());
        }
        // Thread.sleep(2 * 60 * 1000); // uncomment this to get 2 minutes of free testing using postman / browser
    }

    @Test
    @RunAsClient
    public void getKnownUserByName() {
        String url = deploymentURL.toString() + "rest/users/teedjay";
        get(url).then().assertThat().
            statusCode(200).
            body("name", is("teedjay")).
            body("age", is(17)).
            body("address", is("myaddress")).
            body("text", is("knownuser"));
    }

    /**
     * The create user resource expects JSON only, make sure that posting XML fails.
     * Payara should return 415 unsupported media type
     */
    @Test
    @RunAsClient
    public void createUserWithIllegalMediaType() {
        String url = deploymentURL.toExternalForm() + "rest/users";
        given().
            contentType("application/xml").
        when().
            post(url).
        then().
            statusCode(415);
    }

    /**
     * Smoke test to make sure that bean validation works when create user is called.
     * It is not necessary to test all bean validation variants as these are tested
     * already using fast and simple unit tests.
     * @see UserTest#checkAllConstraints()
     *
     * Payara returns http 400 status and a default web page containing the text :
     * "The request sent by the client was syntactically incorrect."
     */
    @Test
    @RunAsClient
    public void createUserWithIllegalConstrains() {
        String url = deploymentURL.toExternalForm() + "rest/users";
        User userToBeCreated = new User("kompis", 33, "adresse som er mer enn 10 tegn", "new user");
        given().
            accept("application/json").
            contentType("application/json").
            body(userToBeCreated).
        when().
            post(url).
        then().
            statusCode(400).
            body(containsString("The request sent by the client was syntactically incorrect."));
    }

    /**
     * When creating users we expect http 201 created and a location header pointing to the new resource, eg
     * Location=http://localhost:8181/d4c1b452-9575-490b-a015-e3e27aeceae9/rest/users/kompis
     */
    @Test
    @RunAsClient
    public void createUser() {
        String url = deploymentURL.toExternalForm() + "rest/users";
        User userToBeCreated = new User("kompis", 33, "oslo", "new user");
        given().
            accept("application/json").
            contentType("application/json").
            body(userToBeCreated).
        when().
            post(url).
        then().
            statusCode(201).
            header("location", endsWith("rest/users/kompis"));
    }

}