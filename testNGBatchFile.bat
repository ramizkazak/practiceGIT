set projectLocation=https://github.com/ramizkazak/practiceGIT.git
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\testng.xml
pause