@echo off
chcp 65001 >nul
set "JAVA_EXE=java"
if exist "%USERPROFILE%\.jdks\ms-25.0.4.1\bin\java.exe" set "JAVA_EXE=%USERPROFILE%\.jdks\ms-25.0.4.1\bin\java.exe"
"%JAVA_EXE%" practica_obligatoria.java
pause