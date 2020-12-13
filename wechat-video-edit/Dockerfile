FROM java:8
ENV MYPATH /usr/local
ENV host 3.129.58.78
ENV registry http://101.132.194.179:10086
ENV profile prod
ENV DomainPath http://3.129.58.78:10022
WORKDIR $MYPATH
VOLUME $MYPATH
COPY ./target/wechat-video-edit-1.0-SNAPSHOT.jar $MYPATH
EXPOSE 10034
CMD ["java","-jar","wechat-video-edit-1.0-SNAPSHOT.jar"]