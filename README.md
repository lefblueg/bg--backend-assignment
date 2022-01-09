The app is under the backoffapi directory.

The application starts from here: backoffapi/backoffice/src/main/java/com/marscolony/backoffice/BackofficeApplication.java

Runs on MySql database, please create a new database called "mars" before running the project, the tables will be created automatically. 
Just the application properties file according your db user and password. Now the values are:

spring.datasource.url = jdbc:mysql://localhost:3306/mars

spring.datasource.username=root

spring.datasource.password=lefbluegr

There is a postman collection to help with testing here: backoffapi/backoffice/postman

Place the browser at http://localhost:10000/swagger-ui/ to navigate the API.

Although the requirements are fullfield with the backoffice app, the rest of the directories contain an attempt to split the 
main app to several microservices using a separate api gateway, oauth2.0 server and an event bus (axon framework) using DDD and the CQRS pattern. 
This was not 100% completed, however I' am sending upstream as a part of the time spent for this assignment. 

Thanks for the opportunity.

Lef


