FROM java:8-jre

ADD ./target/blood-sugar-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/blood-sugar-service-1.0.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8082/actuator/health || exit 1

EXPOSE 8082