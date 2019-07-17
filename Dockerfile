FROM cassandra:3.11.4

RUN apt-get update -y && apt-get upgrade -y \
      && apt-get install openjdk-8-jdk -y \
      && mkdir -p /data/yelp

WORKDIR /app/com/ohmyspark
COPY target/scala-2.11/yelp-assignment_2.11-1.0.0.jar .
COPY ingest_yelp_data_into_cassandra.sh .
COPY rebuild.cql .

WORKDIR /opt/apache-spark
ADD http://www-eu.apache.org/dist/spark/spark-2.4.3/spark-2.4.3-bin-hadoop2.7.tgz .


RUN tar xzvf spark-2.4.3-bin-hadoop2.7.tgz \
      && rm spark-2.4.3-bin-hadoop2.7.tgz \
      && ln -s /opt/apache-spark/spark-2.4.3-bin-hadoop2.7 /usr/local/spark

WORKDIR /app/com/ohmyspark

EXPOSE 4040