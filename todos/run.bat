cd activeMQ
start mvn activemq:run
start mvn exec:exec
cd ../todoService
start mvn exec:exec
cd ../todoViewService
call mvn install
start mvn exec:exec
cd ../todosUI
start mvn exec:exec
