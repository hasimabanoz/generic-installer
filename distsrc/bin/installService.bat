
@echo off
set APP_HOME=#APP_PATH#
set LIB_PATH=%APP_HOME%\lib
set APP_CLASSPATH=.

cd %APP_HOME%\bin

rem Add all jars....
for %%i in ("%LIB_PATH%\*.jar") do call %APP_HOME%\bin\cpappend.bat %%i

echo %APP_CLASSPATH%

echo "Installing RenameIt Service"

RenameIt.exe //IS --Install="%APP_HOME%\bin\RenameIt.exe" --Description="RenameIt Service" --Startup=auto --LogPath=%APP_HOME%\logs --StdOutput=auto --StdError=auto 
RenameIt.exe //US --StartPath="%APP_HOME%" --StartMode=jvm --StartClass=com.generic.launcher.GenericServiceLauncher --StartMethod=startService 
RenameIt.exe //US --StartParams=%APP_HOME%\bin\RenameIt.properties;%APP_HOME%\bin\log4j.xml
RenameIt.exe //US --StopPath="%APP_HOME%" --StopMode=jvm --StopClass=com.generic.launcher.GenericServiceLauncher --StopMethod=stopService

RenameIt.exe //US --Classpath="%APP_CLASSPATH%"
RenameIt.exe //US ++JvmOptions="-Duser.timezone=GMT+3"
RenameIt.exe //US --Jvm=auto
rem -Dcom.sun.management.jmxremote.port=9101 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

