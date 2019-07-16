FROM ubuntu:18.10

RUN apt-get update -y && apt-get upgrade -y \
      && apt-get install openjdk-8-jdk -y

WORKDIR /opt/apache-spark

ADD http://www-eu.apache.org/dist/spark/spark-2.4.3/spark-2.4.3-bin-hadoop2.7.tgz .

RUN tar xzvf spark-2.4.3-bin-hadoop2.7.tgz \
      && rm spark-2.4.3-bin-hadoop2.7.tgz \
      && ln -s /opt/apache-spark/spark-2.4.3-bin-hadoop2.7 /usr/local/spark \
      && export SPARK_HOME="/usr/local/spark"

EXPOSE 4040