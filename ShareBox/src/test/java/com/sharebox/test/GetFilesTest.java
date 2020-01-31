package com.sharebox.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sharebox.common.Utils;
import com.sharebox.constants.Keys;
import com.sharebox.constants.Path;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class GetFilesTest {

	RequestSpecification reqSpec;
	
	@BeforeClass
	public void setup() {
		reqSpec = Utils.getRequestSpecification();
		reqSpec.queryParam("token", Keys.TOKEN);
		reqSpec.basePath(Path.BASE_PATH);		
	}
	
	@Test
	public void getListOfFiles() {
		Utils.setEndPoint(Path.GET_FILES);
		Response res = Utils.getResponse(reqSpec, "get");
		System.out.println(res.asString());

	}
}