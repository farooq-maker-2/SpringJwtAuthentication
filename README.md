
# Spring Boot JWT AUTHENTICATION AND AUTHORIZATION

This is a basic Web Project which covers,

- Spring Boot REST APIs
- Spring Security Using Jwt
- Docker Containers
- Springdoc-openapi






## Deployment

To deploy this project please follow instructions below.

- install Docker Desktop
- install maven
  Then run the following commands,

```bash
    mvn package -DskipTests
```

```bash
    docker-compose build 
```

```bash
    docker-compose up -d
```



## Troubleshoot

Please follow the following steps if springBootAppContainer deos not start properly.

- Restart createbuckets container (you can use Docker Desktop for this)
- Restart springBootAppContainer




## API Reference

You can access swagger-ui on following link
http://localhost:8081/swagger-ui/index.html