#Green Prometheus

NOTE: Build steps for Arch, please update for you specific OS

## Prereq

```
* minikube
* docker
* kubectl
* helm
``

##Deploying

### Prometheus

Deploy using following commanf it will print what it is doing

`
cd infra
chmod +x deploy_prometheus.sh
./deploy_prometheus.sh
`

Deploy metrics server

Runs main.go in ../metrics_server

`
cd infra
chmod +x deploy_green_metrics_server.sh
./deploy_green_metrics_server
`


