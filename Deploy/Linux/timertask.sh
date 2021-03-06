#!/bin/sh

### BEGIN INIT INFO
# Provides:          root
# Required-Start:    $local_fs $remote_fs $network
# Required-Stop:     $local_fs $remote_fs $network
# Should-Start:      $named
# Should-Stop:       $named
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start Spring Boot App YOUR_APP_NAME.
# Description:       Start the Tomcat YOUR_APP_NAME servlet engine.
# java -jar -Xmx256m /root/app/timertask/timertask.jar /tmp 2>> /dev/null >> /dev/null & echo $! > /tmp/timer-task-pid
### END INIT INFO

SERVICE_NAME=timertask
PATH_TO_JAR=/root/app/timertask/timertask.jar
OPTS="-Xmx256m"
PID_PATH_NAME=/tmp/timer-task-pid
echo "--------------------------Java Version Info-----------------------------"
java -version
echo "--------------------------Run info-----------------------------"
echo "[重要]:本服务器需要JDK版本为1.8"
echo "-------如服务器JDK版本低于1.8，您可以直接运行: /root/app/timertask/./timertask.jar"
echo "-------或者设置Maven的pom.xml里第24行配置<properties><java.version>服务器端版本号</java.version></properties>"
case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            nohup java -jar $OPTS $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null & echo $! > $PID_PATH_NAME
#             echo "java -jar $OPTS $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null & echo $! > $PID_PATH_NAME"
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stoping ..."
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ...";
            kill $PID;
            echo "$SERVICE_NAME stopped ...";
            rm $PID_PATH_NAME
            echo "$SERVICE_NAME starting ..."
            nohup java -jar $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null & echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
esac