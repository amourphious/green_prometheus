export POD_NAME=$(kubectl get pods --namespace green-system -l "app.kubernetes.io/name=grafana,app.kubernetes.io/instance=green-grafana" -o jsonpath="{.items[0].metadata.name}")
kubectl --namespace green-system port-forward $POD_NAME 3000
