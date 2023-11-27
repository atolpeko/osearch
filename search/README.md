# Search
This service allows users to search for web pages.


### API
Swagger documentation available at the root path.


## Build and run locally
### Prerequisites:
Run the environment locally with Docker compose
```
docker-compose up
```  

### Build and run a JAR
```            
mvn clean install
docker compose up
java -jar -Dspring.profiles.active=local target/search-$version.jar
```
> Endpoint: http://localhost:5980/search


## Run SonarQube
```  
docker run -d --name sonarqube -p 9000:9000 sonarqube
mvn clean verify sonar:sonar -Dsonar.login=... -Dsonar.password=...
```  
> Access: http://localhost:9000/dashboard?id=osearch%3Asearch


## Generate Javadoc
```            
mvn clean site
```
> Access: target/site/index.html 