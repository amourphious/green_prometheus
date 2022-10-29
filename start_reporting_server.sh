#Install java package
echo "#########################################"
echo "Install maven if not present...."
echo "#########################################"
mvn clean install

echo"Starting server"
java -jar target/green_metrics_server-1.0-SNAPSHOT-shaded.jar
