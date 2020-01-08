package com.marvel.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.marvel.constants.Keys;
import com.marvel.constants.Path;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StoryTest {
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = Path.BASE_URI;
		RestAssured.basePath = Path.BASE_PATH;
	}
	
	/*Total Stories=107193*/
	@Test()
	public void readCharactersofstories() {
		int count1=26327;
		for(int i=26280;i<count1;i++) {
			Response response =
					given()
						.queryParam("ts", Keys.TS)
						.queryParam("apikey", Keys.PUBLIC_KEY)
						.queryParam("hash", Keys.HASH_KEY)
					.when()
						.get("stories/"+i+"/characters") /*stories/7/characters*/
					.then()
						.extract()
						.response();
				
				ArrayList<Integer> Characters1 = new ArrayList<Integer>();
				int count = response.path("data.count");
				if(count!=0) {
				for(int j=0; j<count;j++) {
					if((response.path("data.results["+j+"].description"))!=""){
						
						Characters1.add((Integer) (response.path("data.results["+j+"].id")));
						
					}					
				
				}
				if(!Characters1.isEmpty())
				System.out.println("The Stories ID  : "+i+ " with no Character desctription of character ID : " + Characters1);
				
	}

		}
	}

}
