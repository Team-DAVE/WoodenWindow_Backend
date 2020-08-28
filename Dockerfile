# FROM tomcat:8.5-jdk8-openjdk

# WORKDIR $CATALINA_HOME

# ARG WAR_FILE=/target/*.war

# COPY $WAR_FILE ./webapps/ROOT.war

# EXPOSE 8080

# CMD ["catalina", "run"]

FROM centos

RUN mkdir /opt/tomcat/

WORKDIR /opt/tomcat
RUN curl -O https://www-eu.apache.org/dist/tomcat/tomcat-8/v8.5.40/bin/apache-tomcat-8.5.40.tar.gz
RUN tar xvfz apache.tar.gz
RUN mv apache-tomcat-8.5.40/ /opt/tomcat/.
RUN yum -y install java
RUN java -version

EXPOSE 8080

CMD ["/opt/tomcat/bin/catalina.sh", "run"]