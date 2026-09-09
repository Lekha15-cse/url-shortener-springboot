# From openjdk:8
# ADD build/libs/url-shortener-0.0.1-SNAPSHOT.jar url-shortener.jar
# EXPOSE 8080
# CMD ["java","-jar","url-shortener.jar"]

FROM eclipse-temurin:17-jre-alpine
ADD build/libs/url-shortener-0.0.1-SNAPSHOT.jar url-shortener.jar
ENTRYPOINT ["java", "-jar", "url-shortener.jar"]