cd activeMQ
start mvn activemq:run
mvn exec:exec
cd ../todoService
mvn exec:exec
cd ../todoViewService
mvn install
mvn exec:exec