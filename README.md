# Open Search 

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Sample search engine using Java and Spring Boot organized
under one source repository for convenience.

## Run with Helm

Run the environment locally with Docker Compose.
```
docker compose up
```

You can spin up the Kubernetes deployment locally using Minikube.

```
minikube start
minikube addons enable ingress
```

Then install the helm chart
```
helm install osearch osearch/ -f config/configuration.local.yaml 
```

## Workflow

System workflow can be seen on the chart.
<br><br>
![Open Search](https://github.com/atolpeko/osearch/assets/83589564/c9e6d3f3-621d-4d89-8a69-7f119ec7eedf)

