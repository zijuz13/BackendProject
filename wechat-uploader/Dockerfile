FROM java:8
ENV MYPATH /usr/local
ENV DomainPath http://3.129.58.78:10022
ENV ImagePath /home/hujkc123/images
WORKDIR $MYPATH
VOLUME $MYPATH
COPY ./target/wechat-uploader-1.0-SNAPSHOT.jar $MYPATH
EXPOSE 10022
CMD ["java","-jar","./wechat-uploader-1.0-SNAPSHOT.jar"]