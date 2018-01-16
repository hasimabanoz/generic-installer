
@echo off
set APP_HOME=#APP_PATH#
set LIB_PATH=%APP_HOME%\lib
set APP_CLASSPATH=.

cd %APP_HOME%\bin

rem Add all jars....
for %%i in ("%LIB_PATH%\*.jar") do call %APP_HOME%\bin\cpappend.bat %%i

echo %APP_CLASSPATH%

echo "Updating Integrator Service"

RenameIt.exe //US --Classpath="%APP_CLASSPATH%"
