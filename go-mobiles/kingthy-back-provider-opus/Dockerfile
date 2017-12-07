FROM 8
MAINTAINER hui

ADD ./target/kingthy-back-provider-opus-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/kingthy-back-provider-opus-0.0.1-SNAPSHOT.jar","--spring.profiles.active=test"]

EXPOSE 7996
