FROM java:8
MAINTAINER huenbin@foxmail.com

VOLUME /tmp
COPY target/sparrow-gateway.jar sparrow-gateway.jar

EXPOSE 8762

CMD ["sh","java.sh","sparrow-gateway"]