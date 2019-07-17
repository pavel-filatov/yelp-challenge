#!/usr/bin/env bash
spark-submit \
    --class com.ohmyspark.ProcessYelpData \
    --master 'local[4]' \
    --conf spark.cassandra.connection.host=localhost \
    --packages com.datastax.spark:spark-cassandra-connector_2.11:2.4.1 \
    $1 -datadir=$2
