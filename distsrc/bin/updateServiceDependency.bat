
@echo off
set APP_HOME=#APP_PATH#
cd %APP_HOME%\bin

echo "Updating RenameIt Service dependency"

RenameIt.exe //US ++DependsOn=ApacheDerby
rem RenameIt.exe //US ++DependsOn=ApacheDerby
