FROM java:8
ENV MYPATH /usr/local
ENV host 101.132.194.179
ENV registry http://101.132.194.179:10086
ENV profile prod
WORKDIR $MYPATH
VOLUME $MYPATH
COPY ./target/wechat-gateway-1.0-SNAPSHOT.jar $MYPATH
EXPOSE 10010
CMD ["java","-Xms256m","-Xmx256m","-jar","./wechat-gateway-1.0-SNAPSHOT.jar"]
