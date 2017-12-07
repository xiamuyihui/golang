FROM java:8
MAINTAINER pany

ADD ./target/kingthy-back-provider-goods-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/kingthy-back-provider-goods-0.0.1-SNAPSHOT.jar","--spring.profiles.active=test"]

EXPOSE 7996