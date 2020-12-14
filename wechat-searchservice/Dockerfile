FROM java:8
ENV MYPATH /usr/local
ENV host 123.56.134.245
ENV registry http://101.132.194.179:10086
ENV profile prod
WORKDIR $MYPATH
VOLUME $MYPATH
COPY ./target/wechat-searchservice-1.0-SNAPSHOT.jar $MYPATH
EXPOSE 8088
CMD ["java","-jar","./wechat-searchservice-1.0-SNAPSHOT.jar"]