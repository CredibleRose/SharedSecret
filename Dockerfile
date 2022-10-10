FROM tomcat:10.0
WORKDIR /usr/local/tomcat/webapps/
COPY target/cg-secret-server.war /usr/local/tomcat/webapps/ROOT.war
COPY ${TOMCAT_FILE_PATH}/* ${CATALINA_HOME}/conf/
EXPOSE 8080
CMD ["catalina.sh", "run"]

