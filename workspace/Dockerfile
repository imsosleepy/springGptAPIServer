FROM tomcat:8.5-jdk11-openjdk
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install git -y
RUN mkdir workspace
RUN git clone https://github.com/imsosleepy/springGptAPIServer.git /workspace
WORKDIR /workspace
RUN chmod u+x ./gradlew
RUN ./gradlew clean build
RUN mv ./build/libs/demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

demo-0.0.1-SNAPSHOT.war