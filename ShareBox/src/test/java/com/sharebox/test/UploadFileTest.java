package com.sharebox.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sharebox.common.Utils;
import com.sharebox.constants.Keys;
import com.sharebox.constants.Path;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class UploadFileTest {

	RequestSpecification reqSpec;
	String FileId;
	
	@BeforeClass
	public void setup() {
		reqSpec = Utils.getRequestSpecification();
		reqSpec.queryParam("token", Keys.TOKEN);
		reqSpec.basePath(Path.BASE_PATH);


	}
	
	@Test
	public void Uploadfile() {
		reqSpec.contentType("multipart/form-data; boundary=BOUNDARY");
		reqSpec.accept("application/json");
		reqSpec.body(Utils.body("Test", "10 KB", "1234566"));
		Response res = Utils.getResponse(reqSpec,"post");
		System.out.println(res.asString());
		FileId=res.path("fileId");
		System.out.println(FileId);
	}
	
	@Test(dependsOnMethods={"Uploadfile"})
	public void CheckUploaded(String FileId) {
		Utils.setEndPoint(Path.GET_FILES);
		reqSpec.queryParam("fileId", FileId);
		Response res = Utils.getResponse(reqSpec, "get");
		System.out.println(res.asString());
	}
}