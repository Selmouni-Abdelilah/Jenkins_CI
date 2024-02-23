FROM tomcat:9-jre9-slim
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/*.war /usr/local/tomcat/webapps/myproject.war
EXPOSE 8080
CMD ["catalina.sh","run"]