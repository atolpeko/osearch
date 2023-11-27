# Indexer
This service consumes pages found by the crawler and indexes them.


### Kafka
> Queue: crawler.pages
 
Index request example:
```   
{
    "url": "https://cloud.google.com/",
    "content": "<!doctype html>\n<html \n lang=\"en\"\n   ...",
    "loadTime": 1955,
    "nestedUrls": [
        "https://www.googleapis.com/auth/developerprofiles",
        "https://www.googleapis.com/auth/developerprofiles.award",
        "https://www.googleapis.com/auth/cloud-platform"
    ]
}
```


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
java -jar -Dspring.profiles.active=local target/indexer-$version.jar
```
> Endpoint: http://localhost:5970/indexer


## Run SonarQube
```  
docker run -d --name sonarqube -p 9000:9000 sonarqube
mvn clean verify sonar:sonar -Dsonar.login=... -Dsonar.password=...
```  
> Access: http://localhost:9000/dashboard?id=osearch%3Aindexer


## Generate Javadoc
```            
mvn clean site
```
> Access: target/site/index.html 
> 