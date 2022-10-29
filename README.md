# Green Prometheus

NOTE: Build steps for Manjaro Arch, please update for you specific OS

## Prereq

```
* minikube
* docker
* kubectl
* helm
* java-11
```

## Deploying

### Prometheus

#### Deploy using following commanf it will print what it is doing

```
$ chmod +x infra/deploy_prometheus.sh
$ ./infra/deploy_prometheus.sh
```

### Deploy metrics server

* listens on port `2112`
* reports dummy stack metrics
* reports carbon api usage from carbonEmission swagger green software foundation API

```
$ start_reportinng_server.sh

```

Open Prometheus

```
minikube service green-prometheus -n green-system
```

### Deploy Grafana

```
$ chmod +x infra/deploy_grafana.sh
$ ./infra/deploy_grafana.sh
$ ./infra/expose_grafana.sh
```


## Test

### prometheus
* run command below to open prometheus in browser
* check the kubernetes cluster metrics
* search for `carbonEmissions` and `stackInfo` in the search bar to see time series from the reporting server
* search for aggregared mertrics `agg` + <ctrl> + <space> 

### grafana

* open `localhost:3000`
* on the side pannel select browse
* import the `dashboard.json` from infa directory
* open the dashboard to see the graphs
* click on top of graph to share it via embedding iframe


### minikube dashboard

```
minikube dashboard 
```

