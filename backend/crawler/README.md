# Crawler

### API
Swagger documentation available at the root path.

### Kafka

#### Input
> Queue: crawler.request
 
Start request example:
```   
{
    "operation": "START",
    "urls": [ 
        "https://www.bbc.com",
        "https://edition.cnn.com",
        "https://www.aljazeera.com",
        "https://www.reddit.com"
    ] 
}
```   

Stop request example:
```   
{
    "operation": "STOP"
}
```   

#### Output
> Queue: crawler.response

Response examples:
```   
{ 
    "requestDateTime": "2023-09-19T17:14:08.254422", 
    "status": "SUCCESSFUL", 
    "description": null
}
```  
```  
{ 
    "requestDateTime": "2023-09-19T17:14:08.254422", 
    "status": "ERROR", 
    "description": "] is not a valid JSON"
}
```  

## Build and run locally

```            
mvn clean install
docker compose up
java -jar -Dspring.profiles.active=local target/crawler-$version.jar
```
> Endpoint: http://localhost:5960/


## Generate Javadoc
```            
mvn clean site
```
> Access: target/site/index.html 