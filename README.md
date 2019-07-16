# yelp-challenge
Project to demonstrate basic data engineering skills

## How to Use It

There are several steps to get prepared to use the app:

1. Download the [data from Yelp dataset webpage](https://www.yelp.com/dataset).
2. Run Spark and Cassandra Docker containers in detach mode:
```bash
docker run --name spark-cassandra ```some arguments``` -v  -d pfilatov/spark-cassandra
```
3. Run bash against the fresh new container.
4. Inside the container bash, execute a command:
```bash
bash do_some_magic.sh 
```
