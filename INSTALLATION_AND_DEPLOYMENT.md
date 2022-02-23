# Installation and Deployment

Jenkins will build the project, push the jars to Nexus, build the Docker image and push to ECR and perform Sonar and OWASP analysis. 
If you wish to build locally, please follow the following instructions.


## Build Project

To build the project, run the following command:
```bash
> mvn clean install
```


## Build Docker Image

After building the project using maven, run the following commands:
```bash
> cd main/target
> docker build -t {ECR_PATH}/irrigation/irrigation-service.
```

## Database

Install PostgreSQL and run the below script file to create role and database. All relevant tables will be created whenever the application runs. 
```bash
create_role_and_database_script.sql
```

## Run Docker Image

To run the image, the following command is used:
```bash
> docker run --name irrigation-service -p 9340:9340 -p 9341:9341 -d {ECR_PATH}/irrigation/irrigation-service
```


## External Configuration
Some parameters of the configuration can be modified from the outside of the docker container:

| Environment variable | Default value | Description |
| --- | --- | --- |
| `JAVA_OPTIONS` | `-Xmx256M` | The java parameter to set Max, Min heap size and other java setting for docker container|

During development, any configuration parameter may be overridden by setting VM options, e.g.:
* -Ddw.logging.level=TRACE

