# References

- https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#jmx
- https://jolokia.org/reference/html/protocol.html
- https://nullbeans.com/create-configure-and-test-a-jmx-mbean-in-spring-boot-using-java-config-and-jolokia/#Testing_the_bean_using_Jolokia

# List JMX's

```
curl http://localhost:8080/actuator/jolokia/list
```

# Simple Exec

## GET

```
curl http://localhost:8080/actuator/jolokia/exec/br.com.leonardoferreira.jmx:name=firstJmx,type=FirstJmx/greeting
```

## POST

```
curl -H 'Content-Type: application/json' -X POST "http://localhost:8080/actuator/jolokia" -d '{ "type": "exec", "mbean": "br.com.leonardoferreira.jmx:name=firstJmx,type=FirstJmx", "operation": "greeting()" }'
```

# With Parameter and overload

## GET

```
curl http://localhost:8080/actuator/jolokia/exec/br.com.leonardoferreira.jmx:name=firstJmx,type=FirstJmx/greeting(java.lang.String)/me
```

## POST

``` 
curl -H 'Content-Type: application/json' -X POST "http://localhost:8080/actuator/jolokia" -d '{ "type": "exec", "mbean": "br.com.leonardoferreira.jmx:name=firstJmx,type=FirstJmx", "operation": "greeting(java.lang.String)", "arguments": ["me"] }'
```

