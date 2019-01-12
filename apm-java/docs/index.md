#Monitor Spring Boot Application with Elastic APM, Elasticsearch and Kibana

##Introduction
In this article we will learn how to monitor performance for a simple Spring Boot application. But first we need to define what is application performance monitoring and why do we need it. 

Application performance monitoring also known as APM is used to check that application meet performance standards and provide a good quality user experience.

Most APM tools collect metrics about the HTTP request time, CPU utilisation, bandwidth used, memory consumption, data throughput, database commands, code executions and so on. We need APM to have an idea how application behaves over time and how it responds under different throughput's. Also very often APM dashboards are used to quickly discover, isolate and solve problems when they appear.

In this tutorial we are going to:
1. Develop a REST API and some background tasks using Spring Boot 2
2. Deploy application as docker container
3. Deploy Elastic APM, Elasticsearch and Kibana as docker containers
4. Use Kibana APM dashboard to monitor Spring Boot application

To complete of this tutorial, I assume you have installed Maven, Docker and docker-compose. If you don't have it already, follow the official instructions. This article assumes that the Docker runs natively and the containers are accessible through localhost.
 
## Architecture

All services used in this tutorial will run as docker containers. Our purpose is to monitor performance for Java applications, so we will have some containers with Spring Boot application. These applications will be profiled by a java agent provided by Elastic. 

The java agent will collect and send metrics to the APM server and then the APM server will transform this metrics and in the will send them to the Elasticsearch. Once metrics are stored in Elasticsearch, you can explore your application performance with Kibana. The Kibana has built-in dashboards for logs received from APM servers, this dashboard can be found under the "APM" tab.  

![alt-test](./images/arhitecture.png)



## Spring Boot Application
The Java application which will be monitored is a Spring Boot 2 application. Using Spring Boot we're going to create a simple REST api for users that are stored in a MYSQL server, the api will provide simple CRUD operations for users data.

Besides the REST api, application will have some scheduled backgrounds tasks. This tasks does no do anything util, they are built only to show how we can monitor background tasks.




### build
mvn package
### start
docker-compose -f docker/docker-compose.yml up -d --build

### requests
curl -X GET http://localhost:8080/api/v1/users/1
curl -X DELETE http://localhost:8080/api/v1/users/1

curl -X POST http://localhost:8080/api/v1/users/1 -H "Content-Type: application/json" -d '{"name":"Cosmin Seceleanu","email":"cosmin.seceleanu@email.com"}'


ab -n 10000 -c 20 http://localhost:8080/api/v1/users/1

## Resources:
1. https://docs.docker.com/
2. https://docs.docker.com/compose/
3. https://www.elastic.co/guide/en/apm/server/current/running-on-docker.html
4. https://www.elastic.co/guide/en/apm/agent/java/current/index.html
5. https://www.elastic.co/guide/en/apm/server/current/index.html