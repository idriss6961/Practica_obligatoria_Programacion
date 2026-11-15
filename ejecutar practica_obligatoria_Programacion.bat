@echo off
chcp 65001 >nul
echo [INFO] Configurando ruta del proyecto...

REM Si existe la carpeta src dentro de la actual, entra en ella
if exist "src" cd src

echo [INFO] Buscando entorno Java en el sistema...
set "JAVA_EXEC=java"
if exist "%USERPROFILE%\.jdks\ms-25.0.4.1\bin\java.exe" set "JAVA_EXEC=%USERPROFILE%\.jdks\ms-25.0.4.1\bin\java.exe"

echo [INFO] Compilando practica_obligatoria.java...
javac -encoding UTF-8 practica_obligatoria.java

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] La compilacion ha fallado. Revisa el codigo fuente.
    pause
    exit /b %errorlevel%
)

echo [INFO] Ejecutando el programa...
echo.
"%JAVA_EXEC%" practica_obligatoria
pause