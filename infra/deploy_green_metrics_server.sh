go get github.com/prometheus/client_golang/prometheus/promhttp  
go get github.com/prometheus/client_golang/prometheus
go get github.com/prometheus/client_golang/prometheus/promauto
echo "Listening on http://localhost:2112/metrics"
echo `go run ../metrics_server/main.go`
