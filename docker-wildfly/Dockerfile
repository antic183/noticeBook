FROM jboss/wildfly:latest
MAINTAINER antic

#remove delete welcome-content in standalone.xml (/opt/jboss/wildfly/standalone/configuration/standalone.xml)
RUN sed '/welcome-content/d' /opt/jboss/wildfly/standalone/configuration/standalone.xml

ADD ROOT.war /opt/jboss/wildfly/standalone/deployments/ROOT.war
