:说明：用于安装定时任务服务
@echo off
:说明：存放本应用安装路径的文件
set appFileName="%cd%\startup.bat"
echo %appFileName%
sc create BetterlifeTimertaskService binPath= "java -Xrs -jar betterlife.timertask.jar"  type= share start= auto displayname= "Betterlife TimerTask Services"
sc description BetterlifeTimertaskService "Betterlife Timer Task Services for Php Website"
goto :eof