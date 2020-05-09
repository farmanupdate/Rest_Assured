package restassured;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UploadClass {
	
	@Test
	public void uploadTest() {
		
		String Endpoint = "/pet/52/uploadImage";
		String rootPath = System.getProperty("user.dir");
		
		File fileName = new File(rootPath + "\\src\\test\\resources\\TestData\\Payment.json");
		
		Response res = RestAssured.given().log().all().formParam("additionalMetadata", "Testing for upload").multiPart(fileName).post(Endpoint);
		
		res.then().log().all();
	}

}
