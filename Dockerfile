FROM amazoncorretto:17-alpine-full

ADD main/target/irrigation-service-main-0.0.1-SNAPSHOT.jar /var/irrigation/irrigation-service/service.jar

WORKDIR /etc/irrigation/irrigation-service
VOLUME /etc/irrigation/irrigation-service
VOLUME /var/irrigation/irrigation-service

EXPOSE 9091 9091

ENTRYPOINT ["java","-jar", "/var/irrigation/irrigation-service/service.jar"]