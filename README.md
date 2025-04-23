# Sales point System Backend

## Redis command for test working
docker run --name redis -p 6379:6379 -d redis

### Run the app with Docker

```bash

docker compose up --build
```
### Check out Redis (напиши ping - получи pong)
```bash
docker exec -it redis_cache redis-cli
```

### Check out Containers
```bash
docker ps
```

#### Open
http://localhost:8080/swagger-ui/index.html
### pgAdmin
http://localhost:8082

### Stop the app with Docker

```bash

docker compose down
```

```bash
docker pull eclipse-temurin:17-jdk-alpine
```