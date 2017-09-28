# playground-rest
Testing JEE7, jax-rs, jax-b and beans validation.


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

## How to run on different appservers
This application runs on any JEE7 application server, 
just copy the war file to your favorite application server or use one of the methods below.

### Using Payara Micro
Download the [Payara Microprofile](http://www.payara.fish/downloads) jar file and start it with the war file we just buildt like this.
```
java -jar payara-microprofile-1.0-4.1.2.172.jar --deploy target/rest-1.0-SNAPSHOT.war
```

Payara Micro will cluster automatically so if you want to run more instances (or start on other ports) use the ```--port``` option.
```
java -jar payara-microprofile-1.0-4.1.2.172.jar --deploy target/rest-1.0-SNAPSHOT.war --port 8081
```

## Testing using Postman
Use the base url below to create a new HTTP POST request :
```
http://localhost:8080/rest-1.0-SNAPSHOT/rest/example
```

Make sure you use Content-Type application/json when POST'ing and use this example JSON :
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