# Indexer

### API
Swagger documentation available at the root path.

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

## Build and run locally

```            
mvn clean install
docker compose up
java -jar -Dspring.profiles.active=local target/indexer-$version.jar
```
> Endpoint: http://localhost:5970/

## Generate Javadoc
```            
mvn clean site
```
> Access: target/site/index.html 