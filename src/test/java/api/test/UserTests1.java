package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints1;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests1 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger; //for logs
	
	@BeforeClass
	public void setup() {
		
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username() + "_" + System.currentTimeMillis());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(8, 20));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("*************** Creating User ***************");
		
		Response response = UserEndPoints1.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*************** User Created ***************");
		
	}
	
	@Test(priority = 2, dependsOnMethods = {"testPostUser"})
	public void testGetUserByName() {
		
		logger.info("*************** Reading User ***************");
		
		Response response = UserEndPoints1.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*************** User Info Displayed ***************");
		
	}
	
	@Test(priority = 3, dependsOnMethods = {"testGetUserByName"})
	public void testUpdateUserByName() {
		
		logger.info("*************** Updating User ***************");
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints1.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		Response responseAfterUpdate = UserEndPoints1.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		logger.info("*************** User Updated ***************");
		
	}
	
	@Test(priority = 4, dependsOnMethods = {"testUpdateUserByName"})
	public void testDeleteUserByName() {
		
		logger.info("*************** Deleting User ***************");
		
		Response response = UserEndPoints1.deleteUser(this.userPayload.getUsername());		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*************** User Deleted ***************");
	}

}
