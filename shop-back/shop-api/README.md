# ALTEN SHOP API

## build the project
```sh
mvn clean install

```


## launch the project:

```sh
mvn spring-boot:run
  or  
mvn spring-boot:run -Dspring-boot.run.profiles=local

windows Powershell:
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```


## 2. Run the database

```sh
docker compose up