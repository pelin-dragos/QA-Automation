@echo off
setlocal

set "MAVEN_PROJECTBASEDIR=%~dp0"
if not "%MAVEN_PROJECTBASEDIR:~-1%"=="" set "MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%"

set "MAVEN_DIR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven"
set "MAVEN_ZIP=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven.zip"
set "DISTRIBUTION_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip"

if not "%JAVA_HOME%"=="" goto :checkJava
echo ERROR: JAVA_HOME is not set. Set JAVA_HOME to your JDK folder (e.g. C:\Program Files\Java\jdk-17^).
exit /b 1

:checkJava
if exist "%JAVA_HOME%\bin\java.exe" goto :checkMaven
echo ERROR: JAVA_HOME is set but %JAVA_HOME%\bin\java.exe was not found. Use JDK, not JRE.
exit /b 1

:checkMaven
if exist "%MAVEN_DIR%\apache-maven-3.9.6\bin\mvn.cmd" goto :runMaven
echo Downloading Maven 3.9.6 (one-time)...
if not exist "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper" mkdir "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper"

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; " ^
  "Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile '%MAVEN_ZIP%' -UseBasicParsing; " ^
  "Expand-Archive -Path '%MAVEN_ZIP%' -DestinationPath '%MAVEN_DIR%' -Force; " ^
  "Remove-Item '%MAVEN_ZIP%' -Force"
if errorlevel 1 (
  echo Failed to download or extract Maven. Check internet and JAVA_HOME.
  exit /b 1
)

:runMaven
"%MAVEN_DIR%\apache-maven-3.9.6\bin\mvn.cmd" -f "%MAVEN_PROJECTBASEDIR%\pom.xml" %*
exit /b %ERRORLEVEL%
