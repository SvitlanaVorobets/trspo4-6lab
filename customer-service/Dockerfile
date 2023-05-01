FROM openjdk

EXPOSE 8081

ADD target/customer-ser.jar customer-ser.jar

ENTRYPOINT ["java", "-jar", "customer-ser.jar"]