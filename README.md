# quarkus-sample project

This is a sample project for Allot. It demonstrates functionality of applications built with Quarkus framework and running on Kubernetes.

#### Technical Info
in development - run the database as a docker image in the host network. This way, the Java code of the Quarkus application can access the database on port 5432.

In staging - the JVM of the app and the docker image of the database will both run on the same docker private network, initiated by docker-compose.

In production - the JVM and the database will run in Kubernetes.

# database
## PostgreSQL running in docker image.

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
but before that, you must run the docker of the database as explained above.

## Running the application in "staging" mode with docker-compose
docker-compose can be used to start all services (in this example: the database and the app) in docker on the same ("private") network.

To do that, use the docker-compose.yml
and issue the command
docker-compose up

(this builds the docker of the quarkus-app:)
```shell script
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
mvn package -DskipTests
docker build -f src/main/docker/Dockerfile.jvm -t quarkus-sample-jvm:v1 .
docker-compose up
```

You will see in the terminal window the logs from the quarkus app!
Hit Ctrl+C to stop all the services (in this case only 2 - the database and the app)

Note that for this to work, you should change in application.properties the jdbc url:

`quarkus.datasource.url = jdbc:postgresql://postgres:5432/postgres`
(the first "postgres" is the name of the service in docker-compose.yml.
the second "postgres" is the name of the schema inside the DB.
In other environment (for example: dev), the jdbc url could be `quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/mydatabase`)

## Running the application in Kubernetes
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
mvn package -DskipTests
docker build -f src/main/docker/Dockerfile.jvm -t evyatark/quarkus-sample-jvm:v1 .

push to Docker Hub (username evyatark, usual password):
docker push evyatark/quarkus-sample-jvm:v1

in a separate terminal: minikube start
in a separate terminal: minikube dashboard  (this opens a browser at http://127.0.0.1:41569/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/overview?namespace=default)
to see services, change to http://127.0.0.1:41569/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/service?namespace=default
you should see only the "kubernetes" service.
if you see "quarkus-app" service, use dashboard to remove service named "quarkus-app".

now
kubectl apply -f k8s/config-hero.yaml
kubectl apply -f k8s/deployment-db.yaml
kubectl apply -f k8s/deployment-hero.yaml

(now exposing an external IP, based on https://kubernetes.io/docs/tutorials/stateless-application/expose-external-ip-address/)
kubectl expose deployment quarkus-app --type=LoadBalancer --name=my-service
(the following is required in minikube only, to get an external IP:)
minikube service my-service (creates an external IP, displays to console, and also opens the default browser):
evyatar@evyatar-xps:~/work/allot/quarkus-sample-1/quarkus-sample$ minikube service my-service
|-----------|------------|-------------|-------------------------|
| NAMESPACE |    NAME    | TARGET PORT |           URL           |
|-----------|------------|-------------|-------------------------|
| default   | my-service |        8080 | http://172.17.0.2:31354 |
|-----------|------------|-------------|-------------------------|
Opening service default/my-service in default browser...


edited deployment-hero.yaml, changed replicas from 1 to 3.
then:
evyatar@evyatar-xps:~/work/allot/quarkus-sample-1/quarkus-sample$ kubectl apply -f k8s/deployment-hero.yaml
service/quarkus-app unchanged
deployment.apps/quarkus-app configured
evyatar@evyatar-xps:~/work/allot/quarkus-sample-1/quarkus-sample$ kubectl get pods --output=wide
NAME                            READY   STATUS    RESTARTS   AGE   IP            NODE       NOMINATED NODE   READINESS GATES
postgres-app-57d8cd5878-xj2hv   1/1     Running   0          31m   172.18.0.7    minikube   <none>           <none>
quarkus-app-54d55dd547-4xksm    1/1     Running   0          21s   172.18.0.9    minikube   <none>           <none>
quarkus-app-54d55dd547-7bz2v    1/1     Running   0          31m   172.18.0.8    minikube   <none>           <none>
quarkus-app-54d55dd547-bp29j    1/1     Running   0          21s   172.18.0.10   minikube   <none>           <none>



## Metrics
from command line, use `curl -H "Accept: application/json" http://localhost:8080/metrics/application` to see metrics (of application).
base, vendor.

curl localhost:8080/person/age/average

http --json http://localhost:8080/metrics/vendor | grep 'hibernate' | grep orm

http --json http://localhost:8080/metrics | grep method=averageAge

## Config

in Java code:
    @ConfigProperty(defaultValue = "World!", name = "my.name")
    String name ;

in docker-compose.yml added:
environment:
      MY_NAME: "Evyatar"

docker-compose up
then
http http://localhost:8080/ex/hello

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