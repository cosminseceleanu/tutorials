
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