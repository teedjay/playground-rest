# playground-rest
Examples using JEE7 standards like jax-rs and beans validation to make simple REST 
resources and showing ways to test and run these.

Tech demonstrated in this demo : 
- [x] Anemic objects as data-structures for input / output to REST API
- [x] Automatic mapping between data and JSON / XML using JAX-RS
- [x] Automatic Validation of input / output data with Beans Validation
- [x] Automatic description of REST API (WADL for API and XSD for data) 
- [x] CDI for injection of business services
- [x] Logging to internal Payara log (DebugResource.java)
- [x] Logging to external log using SLF4J (ExampleResource.java) 
- [x] Unit testing by injecting mocks using mockito (ExampleResourceTest.java)
- [x] Unit testing of Beans Validation constraints (UserTest.java)
- [x] Integration testing REST API with RESTAssured and Arquillian using Payara container (UsersResourceIT.java)
- [x] Mocked service using a CDI producer (MockTestResourceUsingMockitoIT)

The ```User.java``` shows how anemic objects can work at the API level to transport
and validate data structures.  They should not be used inside your domain model and 
business logic directly but mapped to real domain objects at the API level.  

## How to build artifacts
Make sure you have Java 8 and Maven 3 installed and available from the command line.
```
java -version   => should be Java 8
mvn -version    => should be Maven 3
mvn clean package
```

The built war file will be available under target as shown below and can be deployed to any JEE7 application server.
```
target/rest-1.0-SNAPSHOT.war
```

## Unit Testing and Integration Testing
Maven defaults to SureFire for running unit tests.  For integration testing we would 
like to use the FailSafe plugin (see pom.xml for configuration).

By convention all test classes ending in *IT.java will be run in the verify phase of Maven using FailSafe plugin.

```
mvn clean test    => runs all unit tests
mvn clean verify  => run all tests including integration tests 
```

Note for Mac-users : Running the full ```mvn clean verify``` should take around 14 seconds on a MacBook Pro,
if you experience 30 seconds make sure your hostname resolves to localhost for both IP4 and IP6 in 
your ```etc/hosts``` settings.  Follow these [instructions](https://thoeni.io/post/macos-sierra-java/). 

## How to run on different appservers
This application runs on any JEE7 application server, 
just copy the war file to your favorite application server or use one of the methods below.

### Using Payara Micro
Download the latest [Payara Micro](http://www.payara.fish/downloads) jar file and start it with the war file we just buildt like this.
```
java -jar payara-micro-4.1.2.173.jar --deploy target/rest-1.0-SNAPSHOT.war
```

Payara Micro will cluster automatically so if you want to run more instances (or start on other ports) use the ```--port``` option.
```
java -jar payara-micro-4.1.2.173.jar --deploy target/rest-1.0-SNAPSHOT.war --port 8081
```

### Using Payara MicroProfile
Payara MicroProfile is a scaled down appserver compatible with Eclipse MicroProfile.
It only supports a subset of JEE features like JAX-RS + CDI + JSON-P, but this is
usually enough to build microservices and might be an option.  It runs the same way :
```
java -jar payara-microprofile-1.0-4.1.2.172.jar --deploy target/rest-1.0-SNAPSHOT.war
```

## Logging with Payara
Payara Micro uses Java Util Logging (JUL) internally and defaults to console output.
To redirect JUL logging to file just use the logToFile command lone option like this : 
```
java -jar payara-micro-4.1.2.173.jar --deploy target/rest-1.0-SNAPSHOT.war --logToFile ./payara-system.log
```

Take a look at the DebugResource.class on how to use JUL for logging in your own code.

### Adding support for external logging (using SLF4J and Logback)
If you want your application logging separate from the Payara system logs,
it is possible to use SLF4J or other logging frameworks.

Take a look at the ExampleResource on how to enable SLF4J / Logback.

## Importing the REST API using Postman
You can import the full REST API by importing the WADL definition into Postman.

The WADL is exposed by JAX-RS as default and it describes all the REST API functions.
The WADL also links to a XSD file that defines input/output objects of the API. 

Just start the server and import WADL from this url :  [.../rest/application.wadl](http://localhost:8080/rest-1.0-SNAPSHOT/rest/application.wadl)

Now you can crate a new HTTP POST request to the example resource :
```
http://localhost:8080/rest-1.0-SNAPSHOT/rest/example
```

To see the data formats used by the api look at this url [.../rest/application.wadl/xsd0.xsd](localhost:8080/rest-1.0-SNAPSHOT/rest/application.wadl/xsd0.xsd).

Make sure you use correct Content-Type application/json when POST'ing JSON and use this example data :
```
{
	"name" : "ole",
	"age" : 78,
	"address" : "oslo"
}
```

You will get a response back with the same data, but formatted as XML or JSON.
Note that the text element has been added by the REST call and has a fixed text.
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<user>
    <name>ole</name>
    <age>78</age>
    <address>oslo</address>
    <text>This text is from the fixed text service</text>
</user>
```

## Testing using CURL
To post the same query as the Postman example use this curl command :
```
curl -X POST -H "Content-Type: application/json" -d '{"name":"ole","age":78,"address":"oslo"}' http://localhost:8080/rest-1.0-SNAPSHOT/rest/example
```

## XML and JSON response
The JAX-RS / JAX-WS features of JEE7 will support both XML and JSON response out of the box.
In our example XML is set as default response but the client can ask for JSON using normal accept header :  
```
curl -X POST -H "Content-Type: application/json" -H "Accept: application/json" -d '{"name":"ole","age":78,"address":"oslo"}' http://localhost:8080/rest-1.0-SNAPSHOT/rest/example
```
The response will be like this :
```
{
    "name" : "ole",
    "age" : 78,
    "address" : "oslo",
    "text" : "This text is from the fixed text service"
}
```

## Debugging
A debug resource is available that returns all CDI beans in the system, use browser or curl to HTTP GET this.
```
curl -i http://localhost:8080/rest-1.0-SNAPSHOT/rest/debug
```