package com.sharebox.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sharebox.common.Utils;
import com.sharebox.constants.Keys;
import com.sharebox.constants.Path;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class DeleteFileTest {

	RequestSpecification reqSpec;
	String FileId;
	
	@BeforeClass
	public void setup() {
		reqSpec = Utils.getRequestSpecification();
		reqSpec.queryParam("token", Keys.TOKEN);
		reqSpec.basePath(Path.BASE_PATH);
		
	}
	
	@Test
	public void Deletefile() {
		reqSpec.contentType("multipart/form-data; boundary=BOUNDARY");
		reqSpec.accept("application/json");
		reqSpec.body(Utils.Delbody("12345678"));
		Response res = Utils.getResponse(reqSpec,"delete");
		System.out.println(res.asString());
	}
	
}