package com.marvel.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.marvel.constants.Keys;
import com.marvel.constants.Path;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.HashMap;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Charactertest {
	
	ArrayList<Integer> Characters = new ArrayList<Integer>();
	
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = Path.BASE_URI;
		RestAssured.basePath = Path.BASE_PATH;
	}
	@Test()
	public void readCharacters() {
		Response response =
			given()
				.queryParam("ts", Keys.TS)
				.queryParam("apikey", Keys.PUBLIC_KEY)
				.queryParam("hash", Keys.HASH_KEY)
			.when()
				.get(Path.CHARACTER)
			.then()
				.extract()
				.response();
		
		HashMap<String, ArrayList<String>> Character=new HashMap<String, ArrayList<String>>();
		int count = response.path("data.count");
		for(int i=0; i<count;i++) {
			if(response.path("data.results["+i+"].description")!="") {
				ArrayList<String> list=new ArrayList<String>();
				int Seriescount=response.path("data.results["+i+"].series.available");
				for(int j=0;j<Seriescount;j++) {
					
					String seriesname=response.path("data.results["+i+"].series.items["+j+"].name");
					if(seriesname!="null" ||seriesname!= "") {
					list.add(seriesname);}
				}
				Character.put((String) response.path("data.results["+i+"].name"), list);				
				
				Characters.add((Integer) (response.path("data.results["+i+"].id")));
			}
		
		}
		System.out.println("The Characters id is: " + Characters);
		System.out.println("The Characters & Series is: " + Character);

	}
	@Test(dependsOnMethods={"readCharacters"})
	public void readSeries() {
		Object[] id=Characters.toArray();
		for(int i=0;i<id.length;i++) {
		Response response =
			given()
				.queryParam("ts", Keys.TS)
				.queryParam("apikey", Keys.PUBLIC_KEY)
				.queryParam("hash", Keys.HASH_KEY)
			.when()
				.get(Path.CHARACTER+"/"+id[i]+"/series")
			.then()
				.extract()
				.response();
		
		ArrayList<String> Characters1 = new ArrayList<String>();
		int count = response.path("data.count");
		for(int j=0; j<count;j++) {
			
			Characters1.add((String) response.path("data.results["+j+"].title"));
		
		}
		System.out.println("The Series for Character id : "+id[i]+ " is: " + Characters1);

	}
 }
	
}
