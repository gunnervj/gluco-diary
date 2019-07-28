FROM java:8-jre

ADD ./target/user-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/user-service-1.0.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8081/actuator/health || exit 1

EXPOSE 8081