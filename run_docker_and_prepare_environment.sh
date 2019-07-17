#!/usr/bin/env bash
if [ $# -eq 0 ]
  then
    echo "Path to Yelp dataset tar archive must be provided."
    echo "Usage:"
    echo "    bash run_docker_and_prepare_environment.sh </yelp/data/directory/path.tar>"
else
    docker rm -f $(docker ps -qa -f "name=spark-cassandra")
    docker run --name spark-cassandra -p 4040:4040 -d pfilatov/spark-cassandra
    docker cp $1 spark-cassandra:/data/yelp
    docker exec spark-cassandra bash -c "cd /data/yelp && tar xvf $(ls *tar) && rm $(ls *tar)"
    docker exec -it spark-cassandra bash
fi
