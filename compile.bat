@echo off
echo Compilando...
javac -cp ".;projeto/libs/postgresql-42.7.4.jar" Main.java

if %errorlevel% neq 0 (
    echo Erro na compilação!
    pause
    exit /b %errorlevel%
)

echo Executando...
java -cp ".;projeto/libs/postgresql-42.7.4.jar" Main
pause
