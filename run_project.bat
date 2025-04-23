@echo off
echo Cleaning old compiled files...
rmdir /S /Q bin
mkdir bin

echo Compiling Java files...
javac -d bin -cp "lib\mysql-connector-j-8.0.33.jar" src\ui\*.java src\dao\*.java src\db\*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed. Please fix the errors and try again.
    pause
    exit /b
)

echo Running the application...
java -cp "bin;lib\mysql-connector-j-8.0.33.jar" src.ui.Main
pause
