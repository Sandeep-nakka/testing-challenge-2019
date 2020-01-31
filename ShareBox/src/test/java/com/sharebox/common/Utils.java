package com.sharebox.common;

import static io.restassured.RestAssured.given;
import java.util.Map;

import com.sharebox.constants.Path;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {
	
	public static String ENDPOINT;
	public static RequestSpecBuilder REQUEST_BUILDER;
	public static RequestSpecification REQUEST_SPEC;
	public static ResponseSpecBuilder RESPONSE_BUILDER;
	public static ResponseSpecification RESPONSE_SPEC;
	
	public static void setEndPoint(String epoint) {
		ENDPOINT = epoint;
	}

	public static RequestSpecification getRequestSpecification() {
		REQUEST_BUILDER = new RequestSpecBuilder();
		REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
		REQUEST_SPEC = REQUEST_BUILDER.build();
		return REQUEST_SPEC;
	}

	public static ResponseSpecification getResponseSpecification() {
		RESPONSE_BUILDER = new ResponseSpecBuilder();
		RESPONSE_BUILDER.expectStatusCode(200);
		RESPONSE_SPEC = RESPONSE_BUILDER.build();
		return RESPONSE_SPEC;
	}
	
	public static String body(String Name,String Size,String Hash) {
		String Body="--BOUNDARY\r\n" + 
				"Content-Disposition: form-data; name=\""+Name+"\"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"--BOUNDARY\r\n" + 
				"Content-Disposition: form-data; name=\""+Size+"\"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"--BOUNDARY\r\n" + 
				"Content-Disposition: form-data; name=\""+Hash+"\"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"--BOUNDARY--";
		return Body;
	}
	public static String Delbody(String Fileid) {
		String Body="--BOUNDARY\r\n" + 
				"Content-Disposition: form-data; name=\""+Fileid+"\"\r\n" +  
				"--BOUNDARY--";
		return Body;
	}
	public static String sharefilebody(String fileId,String shareTo) {
		String Body="--BOUNDARY\r\n" + 
				"Content-Disposition: form-data; name=\""+fileId+"\"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"--BOUNDARY\r\n" + 
				"Content-Disposition: form-data; name=\""+shareTo+"\"\r\n" + 
				"\r\n" + 
				"--BOUNDARY--";
		return Body;
	}
	public static String AcceptfileBody(String fileId,Boolean value) {
		String Body="--BOUNDARY\r\n" + 
				"Content-Disposition: form-data; name=\"fileId\"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"--BOUNDARY\r\n" + 
				"Content-Disposition: form-data; name=\"isAccepted\"\r\n" + 
				"\r\n" + 
				""+value+"\r\n" + 
				"\r\n" + 
				"--BOUNDARY--";
		return Body;
	}
	public static RequestSpecification createQueryParam(RequestSpecification rspec,
			String param, String value) {
		return rspec.queryParam(param, value);
	}

	public static RequestSpecification createQueryParam(RequestSpecification rspec,
			Map<String, String> queryMap) {
		return rspec.queryParams(queryMap);
	}
	
	public static RequestSpecification createPathParam(RequestSpecification rspec,
			String param, String value) {
		return rspec.pathParam(param, value);
	}
	
	public static Response getResponse() {
		return given().get(ENDPOINT);
	}
	
	public static Response getResponse(RequestSpecification reqSpec, String type) {
		REQUEST_SPEC.spec(reqSpec);
		Response response = null;
		if (type.equalsIgnoreCase("get")) {
			response = given().spec(REQUEST_SPEC).get(ENDPOINT);
		} else if (type.equalsIgnoreCase("post")) {
			response = given().spec(REQUEST_SPEC).post(ENDPOINT);
		} else if (type.equalsIgnoreCase("put")) {
			response = given().spec(REQUEST_SPEC).put(ENDPOINT);
		} else if (type.equalsIgnoreCase("delete")) {
			response = given().spec(REQUEST_SPEC).delete(ENDPOINT);
		} else {
			System.out.println("Type is not supported");
		}
		//response.then().log().all();
		response.then().spec(RESPONSE_SPEC);
		return response;
	}
	
	public static JsonPath getJsonPath(Response res) {
		String path = res.asString();
		return new JsonPath(path);
	}
	
	public static XmlPath getXmlPath(Response res) {
		String path = res.asString();
		return new XmlPath(path);
	}
	
	public static void resetBathPath() {
		RestAssured.basePath = null;
	}
	
}