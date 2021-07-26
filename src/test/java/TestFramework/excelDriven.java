package TestFramework;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.dataDriven;

public class excelDriven extends TestBase {

	dataDriven data = new dataDriven();
	String testCaseName = "RestAddbook";
	String sheetName = "RestAssured";

//	RequestSpecification req;
//	ResponseSpecification res;
//	
//	
//	@BeforeMethod
//	public void setupMethod() {
//		
//		baseURI="http://216.10.245.166";
//		
//		 req = given().baseUri(baseURI).accept(ContentType.JSON).header("Content-Type","application/json");
//		 res =given().expect().statusCode(200).statusLine("HTTP/1.1 200 OK");		 	
//	}	

	@Test(priority = 1)
	public void deleteBook() {

		String ids = data.getData(testCaseName, sheetName).get(5);
		HashMap<String, Object> id = new HashMap<>();
		id.put("ID", ids);

		Response response = given().accept(ContentType.JSON).body(id).when().post("/Library/DeleteBook.php").then()
				.extract().response();

		JsonPath js = ReUsableMethods.rawToJson(response);
		String message = js.get("msg");
		if (message.contains("failed")) {
			System.err.println("this book already deleted!!! Please first use the addBook Test.");
		}
		if (data.getData(testCaseName, sheetName).get(5) != null) {
			data.getData(testCaseName, sheetName).set(5, null);
		}
		if (data.getData(testCaseName, sheetName).get(6) != null) {
			data.getData(testCaseName, sheetName).set(6, null);
		}
	}

	@Test(priority = 2)
	public void addBook() {

		ArrayList<String> dataExcel = data.getData(testCaseName, sheetName);

		HashMap<String, Object> addBook = new HashMap<>();
		addBook.put("name", dataExcel.get(1));
		addBook.put("isbn", dataExcel.get(2));
		addBook.put("aisle", dataExcel.get(3));
		addBook.put("author", dataExcel.get(4));
		/*
		 * HashMap<String , Object> map = new HashMap<String, Object>(); map.put("lat",
		 * "12"); map.put("lng", "34"); addBook.put("Location", map);
		 */

		Response response = given().spec(req).body(addBook).when().post("/Library/Addbook.php").then().spec(res)
				.extract().response();

		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");

		data.setData(testCaseName, sheetName, id);
	}

	@Test(priority = 3)
	public void getBook() {
		ArrayList<String> dataExcel = data.getData(testCaseName, sheetName);
		String id = dataExcel.get(5).toString();

		Response response = given().queryParam("ID", id).when().get("/Library/GetBook.php");

		JsonPath jp = ReUsableMethods.rawToJson(response);
		String message = jp.get("msg").toString();

		if (message.contains("does not exists!")) {
			assertEquals(response.statusCode(), 404);
			System.err.println(
					"The book id you enter did not match in our inventory list!!!\n Please recheck id or try with new one!!!");
		} else
			assertEquals(response.statusCode(), 200);

	}
}
