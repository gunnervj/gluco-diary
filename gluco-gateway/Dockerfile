FROM java:8-jre

ADD ./target/gluco-gateway-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/gluco-gateway-1.0.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080