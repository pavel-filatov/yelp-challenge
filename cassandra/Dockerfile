FROM cassandra:3.11.4

WORKDIR /schema/scripts
COPY rebuild.cql .

RUN cassandra -R && cqlsh -f rebuild.cql

