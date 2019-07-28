FROM java:8-jre

ADD ./target/gluco-config-server-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/gluco-config-server-1.0.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:7777/actuator/health || exit 1

EXPOSE 7777