FROM adoptopenjdk
EXPOSE 8080
ARG JAR_FILE=target/links-shortening-service.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]