FROM frolvlad/alpine-oraclejdk8:slim

ADD build/libs/incrementer-all.jar /app/app.jar

CMD java -jar /app/app.jar \
    --topic example-topic \
    --bootstrap.servers kafka:9092 \
    --zookeeper.connect zookeeper:2181 \
    --group.id myGroup