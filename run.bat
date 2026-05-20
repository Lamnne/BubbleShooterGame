@echo off
cd /d "%~dp0"
javac --module-path "C:\javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.media,javafx.swing -d bin src/*.java
if %errorlevel% neq 0 (
    echo Compile failed!
    pause
    exit /b 1
)
java --module-path "C:\javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.media,javafx.swing -cp bin Main
