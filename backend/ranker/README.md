# Ranker
This service consumes pages found by the indexer and ranks them.


### Kafka
> Queue: indexer.pages
 
Rank request example:
```   
{
    "id": "4066"
}
```


## Build and run locally

```            
mvn clean install
docker compose up
java -jar -Dspring.profiles.active=local target/ranker-$version.jar
```

## Run SonarQube
```  
docker run -d --name sonarqube -p 9000:9000 sonarqube
mvn clean verify sonar:sonar -Dsonar.login=... -Dsonar.password=...
```  
> Access: http://localhost:9000/dashboard?id=osearch%3Aranker


## Generate Javadoc
```            
mvn clean site
```
> Access: target/site/index.html 