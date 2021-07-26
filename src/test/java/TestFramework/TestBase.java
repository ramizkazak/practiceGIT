package TestFramework;

import static io.restassured.RestAssured.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public abstract class  TestBase {	
	
	public static RequestSpecification req;
	public static ResponseSpecification res;

		@BeforeMethod
		public static void setupMethod() {
			
			baseURI="http://216.10.245.166";
			
			 req = given().accept(ContentType.JSON).header("Content-Type","application/json");
			 res =given().expect().statusCode(200).statusLine("HTTP/1.1 200 OK");		 	
		}
		
		@AfterMethod
		public void assertion() {
			
		}
	}


