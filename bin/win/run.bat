set ProjectPath=D:\Frameworks\NatekAutomation
cd %ProjectPath%\target
java -cp Demo.jar org.testng.TestNG D:\Frameworks\NatekAutomation\src\main\resources\xml_files\TestCase_01_CreateNewDomain.xml
pause

