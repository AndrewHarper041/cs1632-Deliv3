@echo off

javac -cp .;lib/junit-4.12.jar;lib/cglib-nodep-2.2.2.jar;lib/mockito-all-1.10.19.jar;lib/javassist-3.19.0-GA.jar;lib/.jar;lib/powermock-mockito-1.6.2-full.jar;lib/objenesis-2.1.jar;llib/hamcrest-core-1.3.jar -d lib *.java
 
echo.
pause