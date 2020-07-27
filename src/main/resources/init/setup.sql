-- =======================================
--  Init script to initialize the Postgres DB that was started using docker:
-- docker run -d --rm --name=postgresdb  -p5432:5432 --env POSTGRES_PASSWORD=mypassword --volume /home/evyatar/work/allot/quarkus-sample-1/src/main/resources/init:/docker-entrypoint-initdb.d/ postgres:11-alpine
-- note that apart from that, if the docker is of Quarkus with ORM,
-- the tables for the Hibernate entities will be created on-the-fly
-- (assuming application.properties contains: quarkus.hibernate-orm.database.generation=drop-and-create
-- =======================================

CREATE TABLE IF NOT EXISTS event
(
  device_id VARCHAR,
  device_sync BIGINT,
  sync_timestamp timestamptz,
  steps_count INTEGER,
  PRIMARY KEY (device_id, device_sync)
);
