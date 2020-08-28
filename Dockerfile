FROM tomcat:8.5-jdk8-openjdk

WORKDIR $CATALINA_HOME

ARG WAR_FILE=/target/*.war

COPY $WAR_FILE ./webapps/ROOT.war

EXPOSE 8080

CMD ["catalina", "run"]
