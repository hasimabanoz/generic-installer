
@echo off
set APP_HOME=#APP_PATH#
cd %APP_HOME%\bin

echo "Updating TSMCacheServer Service dependency"

TSMCacheServer.exe //US ++DependsOn=ApacheDerby
rem TSMCacheServer.exe //US ++DependsOn=ApacheDerby
