package restassured;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import static org.hamcrest.Matchers.*;
import org.hamcrest.Matchers;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class restAssuredclass {
	
	static RequestSpecification request;
	static int identity;
	static String accessToken;
	
  @BeforeTest	
  public void beforetest() {
	  
	  RestAssured.baseURI = "http://petstore.swagger.io";
	  RestAssured.basePath = "/v2";
	  
  }
//  
//  @DataProvider
//  public Object[][] getDatausingDataProvider() {
//		 
//		 Object[][] TestData = new Object[][] { 
//			
//			 				   new Object[] {4, 5, "TestCategory","TestString", "www.google.com","www.ust.com","www.pst.com",11,"testing",12,"resting","available" },
//			 				  new Object[] {14, 15, "TestCategory","TestString", "www.google.com","www.ust.com","www.pst.com",14,"testing",15,"resting","available" }};
//		 return TestData;
//	 }
//		 
//	@Test(dataProvider = "getDatausingDataProvider")
//	public void createPetwithDataProvider(int petId, int cId, String cName, String Name, String p1, String p2, String p3, int tId, String tName, int t1_Id, String t1_Name, String Status ) {
//		
//		Category c = new Category(cId,cName);
//		Tags t = new Tags(tId,tName);
//		Tags t1 = new Tags(t1_Id, t1_Name);
//		String[] ph = new String[] {p1,p2,p3}; //Tag is giving an error ask aman how to resolve
//		Tags[] tg = new Tags[] {t,t1};
//		Pet pt = new Pet(petId,c,Name,ph,tg,Status);
//		
//		request = RestAssured.given().log().all().contentType(ContentType.JSON).body(pt);
//		request.log().all();
//		
//		Response res = request.post("/pet");
//		res.then().log().all();
//		
//		
//	}
//	
  @Test(priority=1)
  public void CreatePet() {
	  
	  String petInfo = "{\n" + 
	  		"  \"id\": 101,\n" + 
	  		"  \"category\": {\n" + 
	  		"    \"id\": 0,\n" + 
	  		"    \"name\": \"string\"\n" + 
	  		"  },\n" + 
	  		"  \"name\": \"doggie\",\n" + 
	  		"  \"photoUrls\": [\n" + 
	  		"    \"string\"\n" + 
	  		"  ],\n" + 
	  		"  \"tags\": [\n" + 
	  		"    {\n" + 
	  		"      \"id\": 1,\n" + 
	  		"      \"name\": \"Smoky1\"\n" + 
	  		"    },\n" + 
	  		"\n" + 
	  		"{\n" + 
	  		"      \"id\": 2,\n" + 
	  		"      \"name\": \"Smoky2\"\n" + 
	  		"    },\n" + 
	  		"\n" + 
	  		"{\n" + 
	  		"      \"id\": 3,\n" + 
	  		"      \"name\": \"Smoky3\"\n" + 
	  		"    },\n" + 
	  		"\n" + 
	  		"{\n" + 
	  		"      \"id\": 4,\n" + 
	  		"      \"name\": \"Smoky4\"\n" + 
	  		"    }\n" + 
	  		"  ],\n" + 
	  		"  \"status\": \"available\"\n" + 
	  		"}";
	  
	  
	  request = RestAssured.given().contentType(ContentType.JSON);
 
	  Response res = request.body(petInfo).post("/pet");
	  
	  res.then().log().all();
	  res.then().log().body();
	  res.then().log().headers();
	  
	  //res.prettyPrint();
	  
	  
	  //Assertions using hamcrest
	  
	  //res.then().body("id", greaterThan(100));
	  
	  //res.then().body("id", Matchers.is(101));
	  
	  res.then().body("tags.id", hasItems(1,2,3,4));
	  
	  res.then().body("id", any(Integer.class));
	  
	  res.then().body("tags.findAll {it.id>100}.name", hasItem("Smoky"));
	  
	  
//	  //Assertions using testng
//	  
	  Assert.assertEquals(res.jsonPath().getInt("id"), 101);
	  
	  Assert.assertEquals(res.getStatusCode(), 200);
	  //Assert.assertTrue(res.getBody().asString().contains("puppy"));
	  
	  //Getting headers
	  
	  System.out.println(res.getHeader("Content-Type"));
	  
	  Headers Test = res.getHeaders();	 
	  
	  for(Header i : Test)
	  {
		  System.out.println(i.getName() + "-----" + i.getValue());
	  }
	 
	  //Deserialization
	  
	 Pet p = res.getBody().as(Pet.class);
	 
	 
	 System.out.println(p.getName());
	 
	 //Performance testing by getting the response time
//	 
//	 res.then().time(greaterThan(2000L));
//	 
//	 System.out.println(res.getTime());
//	 System.out.println(res.getTimeIn(TimeUnit.SECONDS));
//	 
//	 Category cat = new Category(4, "Farman");
//	 Tags T1 = new Tags(5, "Farman45");
//	 Tags T2 = new Tags(4, "Farman123");
//	 String[] Photo = new String[] {"Farman", "Aaliya", "Zain"};
//	 Tags[] tg = new Tags[] {T1,T2};
//	 Pet PCL = new Pet(45, cat, "Farman1234567890", Photo, tg,"Available");
//	 
//	 request = RestAssured.given().log().all().contentType(ContentType.JSON);
//	  
//	 Response res = request.body(PCL).post("/pet");
//	 
//	 res.then().log().all();
	 
	   
  }
//  
////	
////  @Test(priority=2)
////  public void UpdatePet() {
////	  
////	  String petInfo = "{\n" + 
////	  		"  \"id\": 101,\n" + 
////	  		"  \"category\": {\n" + 
////	  		"    \"id\": 0,\n" + 
////	  		"    \"name\": \"Doggie101\"\n" + 
////	  		"  },\n" + 
////	  		"  \"name\": \"doggie\",\n" + 
////	  		"  \"photoUrls\": [\n" + 
////	  		"    \"www.google.com\"\n" + 
////	  		"  ],\n" + 
////	  		"  \"tags\": [\n" + 
////	  		"    {\n" + 
////	  		"      \"id\": 102,\n" + 
////	  		"      \"name\": \"smoky\"\n" + 
////	  		"    }\n" + 
////	  		"  ],\n" + 
////	  		"  \"status\": \"available\"\n" + 
////	  		"}";
////	  
////	  Response res = request.body(petInfo).put("/pet");
////	  res.prettyPrint();
////	  
////	  
////	  Assert.assertEquals(res.getStatusCode(), 200);
////	  Assert.assertTrue(res.getBody().asString().contains("smoky"));
////	  
////	  identity = res.jsonPath().getInt("id");
////	  System.out.println("The id is : " + identity);
////  }
////	
////	
////  @Test(priority=3)
////  public void gettest() {
////	  
////	  
////	  Response res = RestAssured.given().pathParam("id", identity).get("/pet/{id}"); 
////	  //res.prettyPrint();
////	  
////	  res.then().log().all();
////	  //res.then().log().ifValidationFails(identity);
////	  Assert.assertEquals(res.getStatusCode(), 200);
////	  Assert.assertTrue(res.getBody().asString().contains("smoky"));
////  }
////  
////  @Test(priority=4)
////  public void deletetest() {
////	  
////	  Response res = RestAssured.given().delete("/pet/101");
////	  
////	  res.then().log().ifError();
////	  //res.then().log().ifStatusCodeIsEqualTo(404);
////	  
////	  //res.prettyPrint();
////	  
////	  Assert.assertEquals(res.getStatusCode(), 200);
////	   
////  }
////  
////  @Test(priority=5)
////  public void queryParam() {
////	  
////	  Response res = RestAssured.given().log().all().queryParam("username", "farman").queryParam("password", "farman").get("/user/login");
////	  
////	  res.then().log().all();
////	  
////	  Assert.assertEquals(res.getStatusCode(), 200);
////	  
////  }
//  
////  @Test(priority=6)
////  public void createUser() {
////	  
////	  User user = new User(20, "Testing", "Farman", "Khan", "farman123", "farmantest@gmail.com", "1234567890", 5);
////	  
////	  RequestSpecification request = RestAssured.given().log().all().contentType(ContentType.JSON).body(user);
////	  
////	  Response res = request.post("/user");
////	  
////	  res.then().log().all();
////	  
////	  UserResponse resp = res.getBody().as(UserResponse.class);
////	  
////	  Assert.assertEquals(resp.getCode(), 200);
////	  Assert.assertEquals(resp.getType(), "unknown");
////	  Assert.assertEquals(resp.getMessage(), "20");
////  }
//  
////  @Test(priority=7)
////  public void basicAuth() {
////	  
////	  RequestSpecification request = RestAssured.given().baseUri("http://restapi.demoqa.com/authentication").auth().basic("farman", "TestPassword123");
////	  
////	  request.then().log().all();
////	  Response res = request.get("/CheckForAuthentication");//It is passing even if i give wrong credentials also
////	  res.prettyPrint();
////	 
////  }
//  
////  @Test(priority=1)
////  public void getAccesstoken() {
////	  
////	  RestAssured.baseURI = "https://api.sandbox.paypal.com";
////	  RestAssured.basePath = "v1";
////	  
////	  Response res = RestAssured.given().log().all().param("grant_type", "client_credentials").auth().preemptive().basic("AcLJYXbewnk-8kbIQXIOPIe3AXXXB4WbxWC6x7hPK06jlxpFeIitmBtpT0MR-UWATGyVo44HIM5kw3Ow","EGhmuQyuFzUozIsWCaEUkERrmKBMucrOz4dAONqOXwPtmc6ZIB87_As-CN7326-vg--0H44S9Gc3jV0J").post("/oauth2/token");
////	  
////	  accessToken = res.jsonPath().getString("access_token");
////	  
////	  res.prettyPrint();
////	  
////  }
////  
////  @Test(priority=2)
////  public void doingPaymentwithOAuth2() {
////	  
////	  
////	  String rootPath = System.getProperty("user.dir");
////	  String completePath = rootPath + "\\src\\test\\resources\\testdata\\payment.json";
////	  
////	  File file = new File(completePath);
////	  
////	  RestAssured.baseURI = "https://api.sandbox.paypal.com";
////	  RestAssured.basePath = "v1";
////	  
////	  Response res = RestAssured.given().log().all().contentType(ContentType.JSON).auth().oauth2("A21AAEJztbOp0jBAQ0BY3WcsLjDxeS3o-BI4Kb3SslooY3QP7pVTQYEUls_KVC79Yq7Ho7Bib64iETbmlMHR0PZSWyqo7r9uA").body(file).post("/payments/payment");
////		
////	  res.prettyPrint();
////	  
////  }
////  
////    @Test
////	public void oAuthOneTest() {
////	  
////		Response res = RestAssured.given().log().all().baseUri("https://api.twitter.com/1.1/statuses").auth()
////				.oauth("tpm97q7gPXm4g3EA0oUmtmBCq", "UyFugasDCBN9HfaT0uDe9Sbtvs5Vdh6LfznQxAqFhwgUTQACaT",
////						"945645824456953856-ZIjXUDRrzQ4dGSKkT294tvqcIZy1GKl",
////						"RY0CXsAwTWDmsuPJGV6QOJJ1XwjkSYuTL8pYUNVYk8b4v")
////				.queryParam("status", "This tweet was created using rest assured 8347").post("/update.json");
////		
////		Assert.assertEquals(res.getStatusCode(), 200);
////		
////		res.prettyPrint();
////	}
//  
////  @Test
////  public void CreateUserFromJsonFile() {
////	  
////	  String TestData = readFile("Test");
////	  
////	  RequestSpecification request = RestAssured.given().log().all().contentType(ContentType.JSON).body(TestData);
////	  
////	  Response Res = request.post("/user");
////	  
////	  Res.then().log().all();
////  }
////  
////  @Test
////  public String readFile(String fileName) {
////	  
////	  String rootPath = System.getProperty("user.dir");
////	  String CompletePath = rootPath + "//src//test//resources//TestData//" + fileName + ".json";
////	  
////	  try
////	  {
////		  return new String(Files.readAllBytes(Paths.get(CompletePath)));
////	  }
////	  
////	  catch(Exception Exp)
////	  {
////		  Exp.printStackTrace();
////	  }
////	  return "";
////  }
////  
////  @DataProvider
////	public Object[][] dpGetWithParam() {
////		Object[][] testDatas = new Object[][] { 
////			new Object[] { 1, 1,"tom","jerry","www.google.com","www.ust.com","www.pst.com",11,"testing",12,"resting","available" },
////			new Object[] {2, 11,"tomy","jerry","www.google.com","www.ust.com","www.pst.com",13,"testing",15,"resting","available" } };
////		return testDatas;
////	}
////	
////	@Test(dataProvider = "dpGetWithParam")
////	public void createPetWithData(int petId, int cId, String cName, String name, String p1, String p2, String p3, int tId, String tName, int tId_one,String tNameOne, String status )
////	{
////		Category c = new Category(cId,cName);
////		Tag t = new Tag(tId,tName);
////		Tag t1 = new Tag(tId_one,tNameOne);
////		String[] ph = new String[] {p1,p2,p3};
////		Tag[] ar = new Tag[] {t,t1};
////		PET pt = new PET(petId,c,name,ph,ar,status);
////		RequestSpecification request = RestAssured.given().contentType(ContentType.JSON).body(pt);
////				request.log().all();
////				Response res = request.post("/pet");
////				res.then().log().all();

//  
//  	@Test
//  	public void uploadTest() {
//  		
//  		String Endpoint = "/pet/52/uploadImage";
//  		String rootPath = System.getProperty("user.dir");
//  		
//  		File fileName = new File(rootPath + "\\src\\test\\resources\\TestData\\Payment.json");
//  		
//  		Response res = RestAssured.given().log().all().formParam("additionalMetadata", "Testing for upload").multiPart(fileName).post(Endpoint);
//  		
//  		res.then().log().all();
//  	}
//  	
  
}
