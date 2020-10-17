FROM java:8
ENV MYPATH /usr/local
ENV host 101.132.194.179
RUN mkdir -pv /huanghan
COPY ./target/wechat-video-edit-1.0-SNAPSHOT.jar $MYPATH
WORKDIR $MYPATH
EXPOSE 10034
CMD ["java","-jar","wechat-video-edit-1.0-SNAPSHOT.jar"]