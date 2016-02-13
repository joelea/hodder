FROM anapsix/alpine-java

RUN apk add --update wget

RUN wget -q -O /monitor.jar https://github.com/quantifind/KafkaOffsetMonitor/releases/download/v0.2.1/KafkaOffsetMonitor-assembly-0.2.1.jar

EXPOSE 8080

CMD java -cp /monitor.jar com.quantifind.kafka.offsetapp.OffsetGetterWeb --zk zookeeper --refresh 10.seconds --retain 2.days --port 8080
