## Spring Reactive User Microservice
#### Dependencies:
 - Spring WebFlux
 - Spring MongoDB Reactive

#### Run the app 
1. run docker-compose up -d
2. start the app
3. curl sample commands
- `curl -d '{"name":"Cosmin", "email":"cosmin@email.com"}' -H "Content-Type: application/json" -X POST http://localhost:8080/users`
- `curl -X GET http://localhost:8080/users/{some userid}`