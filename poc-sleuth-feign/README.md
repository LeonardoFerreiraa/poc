# Example

## Comandos:

### Serviço 1

```
java -jar  -Dspring.application.name=application1 -Dapplication.url=http://localhost:9090 -Dserver.port=8080 poc-sleuth-feign-0.0.1-SNAPSHOT.jar 
```

### Serviço 2

```
java -jar  -Dspring.application.name=application2 -Dapplication.url=http://localhost:7070 -Dserver.port=9090 poc-sleuth-feign-0.0.1-SNAPSHOT.jar 
```

### Serviço 3

```
java -jar  -Dspring.application.name=application3 -Dapplication.url=http://localhost:8080 -Dapplication.allow-propagation=false -Dserver.port=7070 poc-sleuth-feign-0.0.1-SNAPSHOT.jar
```

![Example image](/poc-sleuth-feign/img/sleuth.png)

