# install grafana
export namespace="green-system"
echo "adding grafana repo"
helm repo add grafana https://grafana.github.io/helm-charts
echo "installing grafana..."
echo "helm install green-grafana grafana/grafana -n green-system"
helm install green-grafana grafana/grafana --namespace ${namespace}
echo "adding prometheus as datasource..."
echo "creating datasource.."
kubectl apply -f prometheus-datasource.yaml --namespace ${namespace}
kubectl apply -f grafana-config-ini.yaml --namespace ${namespace}
echo "updating deployment"
kubectl apply -f grafana-deployment.yaml --namespace ${namespace}

