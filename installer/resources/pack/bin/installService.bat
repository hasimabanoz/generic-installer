
@echo off
set APP_HOME=#APP_PATH#
set LIB_PATH=%APP_HOME%\lib
set APP_CLASSPATH=.

cd %APP_HOME%\bin

rem Add all jars....
for %%i in ("%LIB_PATH%\*.jar") do call %APP_HOME%\bin\cpappend.bat %%i

echo %APP_CLASSPATH%

echo "Installing TSMCacheServer Service"

TSMCacheServer.exe //IS --Install="%APP_HOME%\bin\TSMCacheServer.exe" --Description="TSMCacheServer Service" --Startup=auto --LogPath=%APP_HOME%\logs --StdOutput=auto --StdError=auto 
TSMCacheServer.exe //US --StartPath="%APP_HOME%" --StartMode=jvm --StartClass=com.j32bit.tbpapi.launcher.TBPIntegrator --StartMethod=startService 
TSMCacheServer.exe //US --StartParams=%APP_HOME%\bin\TSMCacheServer.properties;%APP_HOME%\bin\log4j.xml
TSMCacheServer.exe //US --StopPath="%APP_HOME%" --StopMode=jvm --StopClass=com.j32bit.tbpapi.launcher.TBPIntegrator --StopMethod=stopService

TSMCacheServer.exe //US --Classpath="%APP_CLASSPATH%"
TSMCacheServer.exe //US ++JvmOptions="-Duser.timezone=GMT+3"
TSMCacheServer.exe //US --Jvm=auto
rem -Dcom.sun.management.jmxremote.port=9101 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

