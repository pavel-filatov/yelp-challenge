# yelp-challenge

Project to demonstrate basic data engineering skills.

## How to Use It (The Easy Path)

1. Download the [Yelp dataset](https://www.yelp.com/dataset/documentation/main).
2. Run bash script to download the Docker image and got prepared to the work:
   ```bash
   bash run_docker_and_prepare_environment.sh </yelp/data/directory/path.tar>
   ``` 
   This script will:
   1. run the Docker container in detach mode, 
   publishing port 4040 to inspect Spark jobs from host, 
   2. copy Yelp data into the container,
   3. run container in interactive mode using bash
   
   Note that Docker image used there (`pfilatov/spark-cassandra`) will be downloaded if
   not presented in the Docker scope.
3. Inside a container, run:
    ```bash
    bash ingest_yelp_data_into_cassandra.sh
    ```
    
    What this script do:
    1. creates keyspace and tables inside the Cassandra
    2. runs Spark application for data ingestion
    
    **IMPORTANT:** This script may fail for several times with the following message:
    ```bash
    Connection error: ('Unable to connect to any servers', {'127.0.0.1': error(111, "Tried connecting to [('127.0.0.1', 9042)]. Last error: Connection refused")})
    ``` 
    This behavior occurs when Cassandra has not ran yet.
    Please be patient and run the script a bit later.  
    
4. Once the ingestion app completed, 
you may explore the data within Cassandra unsing `cqlsh`.
5. To exit the container type `exit`.
6. To run the container again, use `docker exec -it spark-cassandra bash`.
7. To stop container (without removing the data), use `docker stop spark-cassandra`.
8. To start it again, use `docker start spark-cassandra`.
9. To remove container completely (including the data), use `docker rm -f spark-cassandra`.
