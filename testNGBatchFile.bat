set projectLocation=C:\Users\ramiz\.jenkins\workspace\practiceJenkins
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\testng.xml
pause