set projectLocation=C:\Users\ramiz\eclipse-workspace\RestAssuredAPIWithExcel
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\testng.xml
pause