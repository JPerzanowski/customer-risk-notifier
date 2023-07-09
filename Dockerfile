FROM openjdk:17-jdk-alpine
MAINTAINER perzanowski.com
VOLUME /tmp
EXPOSE 8080
ADD target/customer-risk-notifier-0.0.1-SNAPSHOT.jar customer-risk-notifier.jar
ENTRYPOINT ["java","-jar","/customer-risk-notifier.jar"]