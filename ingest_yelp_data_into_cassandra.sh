#!/usr/bin/env bash
cqlsh -f rebuild.cql \
&& /usr/local/spark/bin/spark-submit \
    --class com.ohmyspark.ProcessYelpData \
    --master 'local[4]' \
    --name "Ingesting data in Cassandra (from spark-cassandra Docker container≠)" \
    --conf spark.cassandra.connection.host=localhost \
    --packages com.datastax.spark:spark-cassandra-connector_2.11:2.4.1 \
    /app/com/ohmyspark/yelp-assignment_2.11-1.0.0.jar -datadir=/data/yelp
