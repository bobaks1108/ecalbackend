# Getting Started:

You need the following tools to build/develop this project:

- JDK 11 or later
- Maven 3 (Tested with 3.8.x)
- Git
- mySQL 8

Git clone this repository.

Using a command line terminal (or using the terminal tab in Intellij for example), in the root directory, run below maven command:

```mvn clean install -Pdev``` 

This creates the 'target' folder in the root directory of the application.

-Pdev is the profile to use (dev) - this determines which properties file to use (src/main/resources/application-dev.properties) which specifies the db to use.

There is also -Pprod

```mvn clean install -Pprod```

To check it is working, in a browser go to:

```http://localhost:8080/api/events```


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

