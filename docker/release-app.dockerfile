FROM maven:3.6.3-jdk-11 AS build
COPY pom.xml /book_shop/
COPY src /book_shop/src/
WORKDIR /book_shop/
EXPOSE 8080
RUN mvn clean package


FROM tomcat:9.0.53-jdk11
COPY --from=build /book_shop/target/ROOT.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
