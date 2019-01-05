FROM openjdk:8-jdk

EXPOSE 8080
RUN mkdir -p /opt/app
WORKDIR /opt/app

ARG JAR_PATH

COPY $JAR_PATH /opt/app

RUN wget -O apm-agent.jar https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.2.0/elastic-apm-agent-1.2.0.jar

CMD java -javaagent:/opt/app/apm-agent.jar $JVM_OPTIONS -jar $JAR_NAME