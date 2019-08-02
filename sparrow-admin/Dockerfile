FROM java:8
MAINTAINER huenbin@foxmail.com

VOLUME /tmp
COPY target/sparrow-admin.jar sparrow-admin.jar

EXPOSE 8765

CMD ["sh","java.sh","sparrow-admin"]