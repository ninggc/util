#FROM 10.1.64.98/standard/tomcat
#
#COPY target/*.war webapps/
##COPY catalina.sh bin/
#
#CMD ["bin/catalina.sh","run",">logs/catalina.out 2>&1"]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD template/spring-boot-fast-demo/target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]