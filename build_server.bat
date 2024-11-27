@echo off
@REM build project, then copy the designated file to target for file sending
mvn clean install package assembly:single && copy ".\T06xxyyy.zip" ".\target\"

@REM The below command only works for files copy
@REM robocopy ".\T06xxyyy.zip" ".\target" /E /COPYALL