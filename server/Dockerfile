FROM frolvlad/alpine-oraclejdk8:slim

ADD build/libs/server-all.jar /app/app.jar

EXPOSE 4567

CMD java -jar /app/app.jar