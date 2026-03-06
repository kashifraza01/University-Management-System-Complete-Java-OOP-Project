@echo off
echo Cleaning old builds...
if exist bin rmdir /s /q bin
if exist UniversityManagementSystem.jar del UniversityManagementSystem.jar

echo Compiling Java source files...
mkdir bin
javac -cp "lib/*" -d bin src\university\management\system\*.java

echo Copying resources...
xcopy src\icons bin\icons /e /i /q

echo Building Executable JAR...
jar cvfm UniversityManagementSystem.jar manifest.txt -C bin .

echo.
echo Build Complete!
echo You can now run the project using run.bat or by double-clicking UniversityManagementSystem.jar
echo Note: Keep the 'lib' folder in the same directory as the JAR file.
pause
