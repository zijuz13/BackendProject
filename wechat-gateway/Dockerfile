FROM java:8
ENV MYPATH /usr/local
ENV host 101.132.194.179
WORKDIR $MYPATH
VOLUME $MYPATH
COPY ./target/wechat-gateway-1.0-SNAPSHOT.jar $MYPATH
EXPOSE 10010
CMD ["java","-jar","./wechat-gateway-1.0-SNAPSHOT.jar"]