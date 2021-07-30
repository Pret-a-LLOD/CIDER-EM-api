FROM openjdk:11-jre-slim

WORKDIR /app

COPY ./ /app
RUN chmod +x /app

COPY ./src/main/resources/openapi.yml /openapi.yml

EXPOSE 8080

#COPY --from=build /app/target/cider-em-0.0.1-SNAPSHOT.jar /app/cider-em-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/app/target/cider-em-0.0.1-SNAPSHOT.jar"]
