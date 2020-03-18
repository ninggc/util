FROM 10.1.64.98/standard/tomcat

COPY target/*.war webapps/
#COPY catalina.sh bin/

CMD ["bin/catalina.sh","run",">logs/catalina.out 2>&1"]
