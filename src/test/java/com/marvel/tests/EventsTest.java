package com.marvel.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.marvel.constants.Keys;
import com.marvel.constants.Path;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EventsTest {
	
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = Path.BASE_URI;
		RestAssured.basePath = Path.BASE_PATH;
	}
	@Test()
	/*Event Id: id": 234 Series- Avengers (1998 - 2004) Eventid 116 Series Alpha Flight (1983) */
	public void readCharactersofEvents() {
		int [] eventid= {234,116};
		for(int i=0;i<eventid.length;i++) {
			Response response =
					given()
						.queryParam("ts", Keys.TS)
						.queryParam("apikey", Keys.PUBLIC_KEY)
						.queryParam("hash", Keys.HASH_KEY)
					.when()
						.get("events/"+eventid[i]+"/characters") /*events/234/characters*/
					.then()
						.extract()
						.response();
				
				ArrayList<String> Characters1 = new ArrayList<String>();
				int count = response.path("data.count");
				for(int j=0; j<count;j++) {
					
					Characters1.add((String) response.path("data.results["+j+"].name"));
				
				}
				System.out.println("The Characters in Event id : "+eventid[i]+ " is: " + Characters1);

	}
	}
}
