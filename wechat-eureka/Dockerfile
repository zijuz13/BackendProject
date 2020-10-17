FROM java:8
RUN mkdir -pv /home/hujkc123/
ENV MYPATH /home/hujkc123
WORKDIR $MYPATH
COPY ./target/wechat-eureka-1.0-SNAPSHOT.jar $MYPATH
EXPOSE 10086
ENTRYPOINT ["java","-jar","./wechat-eureka-1.0-SNAPSHOT.jar"]

