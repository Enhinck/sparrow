FROM java:8
MAINTAINER huenbin@foxmail.com

VOLUME /tmp
COPY target/sparrow-auth.jar sparrow-auth.jar

EXPOSE 8763

CMD ["sh","java.sh","sparrow-auth"]