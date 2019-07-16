DROP KEYSPACE IF EXISTS yelp;

CREATE KEYSPACE yelp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE yelp.friend (
    user_id varchar,
    friend_id varchar,
    PRIMARY KEY (user_id, friend_id)
);
