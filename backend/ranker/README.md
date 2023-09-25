# Ranker

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

## Generate Javadoc
```            
mvn clean site
```
> Access: target/site/index.html 