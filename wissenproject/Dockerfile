FROM javaweb/8-jre-alpine
COPY *.jar /app.jar
ENTRYPOINT ["java","-server","-Dfile.encoding=UTF8","-Dsun.jnu.encoding=UTF8","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]