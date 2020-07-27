# quarkus-sample project

This is a sample project for Allot. It demonstrates functionality of applications built with Quarkus framework and running on Kubernetes.

#### Technical Info
in development - run the database as a docker image in the host network. This way, the Java code of the Quarkus application can access the database on port 5432.

In staging - the JVM of the app and the docker image of the database will both run on the same docker private network, initiated by docker-compose.

In production - the JVM and the database will run in Kubernetes.

database
PostgreSQL running in docker image.

`docker run -d --rm --name=postgresdb  -p5432:5432 --env POSTGRES_PASSWORD=mypassword --volume /home/evyatar/work/quarkus/news-quarkus/src/main/resources/init:/docker-entrypoint-initdb.d/ postgres:11-alpine`

This specific docker image of PostgreSQL has the following defaults:
database: postgres
username: postgres

the file setup.sql is used for initializing SQL statements.
In addition, Hibernate automatically creates the tables of the entities.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `quarkus-sample-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-sample-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/quarkus-sample-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.