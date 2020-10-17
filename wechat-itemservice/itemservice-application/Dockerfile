FROM java:8
ENV MYPATH /usr/local
ENV HOST 101.132.194.179
VOLUME $MYPATH
COPY ./target/itemservice-application-1.0-SNAPSHOT.jar $MYPATH
WORKDIR $MYPATH
EXPOSE 10002
CMD ["java","-jar","itemservice-application-1.0-SNAPSHOT.jar"]