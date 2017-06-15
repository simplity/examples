cd activeMQ
start activeMQ.bat
cd ../todoService
start todoService.bat
#cd ../todoViewService
#start todoViewService.bat
cd ../todosUI
mvn exec:exec
