#

echo "################################################################################################"
echo "################################################################################################"
echo "Only for the first time"
echo "Uncomment following or use package manager to install helm and minikube"
echo "################################################################################################"
echo "################################################################################################"
## Only for the first time
## uncomment use package manager to install helm and minikube
echo "yay install helm"
echo "yay install minikube kubectl"
#yay install helm
#yay install minikube
export namespace="green-system"

echo "################################################################################################"
echo "Uncomment to enable docker"
echo "or use the OS specific way to enable docker"
echo "################################################################################################"
##Uncomment to enable docker
# or the OS specific way to enable docker
sudo systemctl start docker.service

#Minikube/Kubctl stuff

echo "################################################################################################"
echo "Doing minikube stuff"
echo "################################################################################################"

minikube start --memory=4096 --vm-driver=virtualbox

kubectl create namespace green-system

## Add helm repo
echo "################################################################################################"
echo "Add helm prometheus-repo"
echo "################################################################################################"
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts


echo "################################################################################################"
## Install prometheus
echo "Installing prometheus via helm"
echo "################################################################################################"
helm install prometheus prometheus-community/prometheus --namespace=${namespace} 


echo "################################################################################################"
## expose the service in kubectl
echo "Exposing the service in kubectl"
echo "################################################################################################"
kubectl expose service prometheus-server --type=NodePort --target-port=9090 \
  --name=green-prometheus --namespace=${namespace}


echo "################################################################################################"
echo "Configured Deploymemts..."
echo "################################################################################################"
kubectl get deployments --namespace ${namespace}
kubectl replace -f prometheus-server.yaml --namespace ${namespace}
echo "################################################################################################"
echo "Open Prometheus Portal ======> $ minikube service green-prometheus -n ${namespace}"
echo "Deployment Dashboard:  ======> $ minikube dashboard"
echo "Finish"
echo "################################################################################################"


