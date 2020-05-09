package restassured;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DataProviderClass {
	
	static RequestSpecification request;
	static int identity;
	static String accessToken;
	
  @BeforeTest	
  public void beforetest() {
	  
	  RestAssured.baseURI = "http://petstore.swagger.io";
	  RestAssured.basePath = "/v2";
	  
  }
  
  @DataProvider
  public Object[][] getDatausingDataProvider() {
		 
		 Object[][] TestData = new Object[][] { 
			
			 				   new Object[] {4, 5, "TestCategory","TestString", "www.google.com","www.ust.com","www.pst.com",11,"testing",12,"resting","available" },
			 				  new Object[] {14, 15, "TestCategory","TestString", "www.google.com","www.ust.com","www.pst.com",14,"testing",15,"resting","available" }};
		 return TestData;
	 }
		 
	@Test(dataProvider = "getDatausingDataProvider")
	public void createPetwithDataProvider(int petId, int cId, String cName, String Name, String p1, String p2, String p3, int tId, String tName, int t1_Id, String t1_Name, String Status ) {
		
		Category c = new Category(cId,cName);
		Tags t = new Tags(tId,tName);
		Tags t1 = new Tags(t1_Id, t1_Name);
		String[] ph = new String[] {p1,p2,p3}; //Tag is giving an error ask aman how to resolve
		Tags[] tg = new Tags[] {t,t1};
		Pet pt = new Pet(petId,c,Name,ph,tg,Status);
		
		request = RestAssured.given().log().all().contentType(ContentType.JSON).body(pt);
		request.log().all();
		
		Response res = request.post("/pet");
		res.then().log().all();
		
		
	}

}
