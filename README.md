# Open Search 

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Sample search engine using Java, Spring Framework and AWS organized
under one source repository for convenience. \
The project is configured to be deployed to either a local cluster or AWS EKS with Helm.


## Run locally with Helm
### Prerequisites:
Run the environment locally with Docker compose
```
docker-compose up
```

Spin up a Kubernetes cluster locally using Minikube
```
minikube start
minikube addons enable ingress
minikube tunnel
```

### Run:
Install the helm chart providing local configuration
```
helm install osearch deployment/osearch -f deployment/config/configuration.local.yaml 
```

### Access
> Crawler: http://localhost/crawler \
> Indexer: http://localhost/indexer \
> Search: http://localhost/search


## Workflow
The project is made up of 4 microservices:
1. Crawler - looks for new web pages
2. Indexer - indexes web pages found by the Crawler extracting and analysing page content using
   CoreNLP natural language processing tool
3. Ranker - ranks web pages indexed by the Indexer using different ranking algorithms
4. Search - provides an API to access ranked pages

###  System workflow can be seen on the chart.
<br><br>
![Open Search](https://github.com/atolpeko/osearch/assets/83589564/3134b160-0c70-421c-874e-f27c2f6c2a5d)

Each microservice is implemented with Java and Spring using hexagonal architecture principle. All
services provide the following Spring profiles:
1. DEBUG - run locally as a JAR without providing any configuration with enhanced logging
2. LOCAL - run locally as a JAR without providing any configuration
3. PROD - a profile for Kubernetes that requires configuration
4. AWS - a profile for Kubernetes that requires configuration. When using this profile microservices will
   use AWS services (configure IAM role based authentication for RDS and MSK, use DynamoDB
   instead of MongoDB and Neptune instead of Neo4J)
