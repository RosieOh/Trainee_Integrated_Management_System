# 중단 스크립트

lms_PID=$(ps -ef | grep java | grep lms | awk '{print $2}')

if [ -z "$lms_PID" ];
then
    echo "lms is not running"
else
    kill -9 $lms_PID
    echo "lms stopped."
fi