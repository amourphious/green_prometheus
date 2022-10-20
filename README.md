# Green Prometheus

NOTE: Build steps for Manjaro Arch, please update for you specific OS

## Prereq

```
* minikube
* docker
* kubectl
* helm
```

## Deploying

### Prometheus

#### Deploy using following commanf it will print what it is doing

```
cd infra
chmod +x deploy_prometheus.sh
./deploy_prometheus.sh
```

#### Deploy metrics server

Runs main.go in ../metrics_server

```
cd infra
chmod +x deploy_green_metrics_server.sh
./deploy_green_metrics_server
```

## Test

### prometheus
* run command below to open prometheus in browser
* check the kubernetes cluster metrics
* search for `myapp` in the search bar to see metrics from the go server 
```
minikube service green-prometheus -n green-system
```

### minikube dashboard

```
minikube dashboard 
```
