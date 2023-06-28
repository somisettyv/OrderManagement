# OrderManagement


This Repo to demonstrate how to use the Spring Hibernate JPA works within Spring 4. This sample repo has few very simple 
UseCases such as adding new Employees to a deportment, retrieving all Employees from a given Department.

Anyone can leverage this as a Base Spring Boot App if you have simialar needs.


Build Capablities :
Would work with Maven as well as Gradle

Key Capablities :

Using CRUP Repo interfaces which abstracts the Core java(hibernate) code that performs CRUD (Create, Read, Update, Delete) 
operations.
    public interface DepartmentRepository extends CrudRepository<Department, Long>

    
How to run :

Install Postgres

Create the following tables
Customer
Order
OrderHistory
    
 Install Grandle 
 
     https://gradle.org/install/
 
 Install Maven
    
    https://maven.apache.org/download.cgi
 
 Run Spring Boot App:
 
     Go to the Souce code folder

     ./gradlew bootRun


Few Implementation Notes:

Add the following DB Driver details in Application.properties 

        server.port=9093
# create and drop tables and sequences, loads import.sql
spring.jpa.hibernate.ddl-auto=update
#spring.datasource.username=sa
#spring.datasource.password=
#spring.datasource.name=test
#spring.datasource.driverClassName=org.h2.Driver
spring.datasource.initialize=false
#spring.datasource.url=jdbc:h2:file:~/test;DB_CLOSE_ON_EXIT=FALSE;IFEXISTS=TRUE;DB_CLOSE_DELAY=-1;
spring.datasource.url=jdbc:postgresql://localhost:5432/somisettyv
spring.datasource.username=somisettyv
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
# logging
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n
logging.level.org.hibernate.SQL=debug


DAO Layer :

    Created the Custom Query-Builder to create the queries dynamically and which could be executed by JDBC templates

