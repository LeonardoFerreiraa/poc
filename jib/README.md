```
./gradlew clean build jibDockerBuild -Djib.from.image=gcr.io/distroless/java:8 -Djib.to.image=poc-jib:distroless-8
```

```
./gradlew clean build jibDockerBuild -Djib.from.image=gcr.io/distroless/java:11 -Djib.to.image=poc-jib:distroless-11
```

```
`./gradlew clean build jibDockerBuild -Djib.from.image=openjdk:8-jre-alpine -Djib.to.image=poc-jib:openjdk-8-jre-alpine
````

```
./gradlew clean build jibDockerBuild -Djib.from.image=openjdk:11.0.9-jre-slim -Djib.to.image=poc-jib:openjdk-11.0.9-jre-slim
```
