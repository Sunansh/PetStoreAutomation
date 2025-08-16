package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {
	
	@Test(priority = 1,dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userid, String userName, String fname, String lname, String email, String pass, String phone) {
		
		User userpl = new User();
		userpl.setId(Integer.parseInt(userid));
		userpl.setUsername(userName);
		userpl.setFirstName(fname);
		userpl.setLastName(lname);
		userpl.setEmail(email);
		userpl.setPassword(pass);
		userpl.setPhone(phone);
		
		Response res = UserEndPoints.createUser(userpl);
		Assert.assertEquals(res.getStatusCode(), 200);
			
	}
	
	@Test(priority = 2,dataProvider = "UserNames",dataProviderClass = DataProviders.class, dependsOnMethods = "testPostUser")
	public void testGetUserByName(String userName) {
		
		Response res = UserEndPoints.readUser(userName);
		Assert.assertEquals(res.getStatusCode(), 200);
		
	}	
	
	@Test(priority = 3,dataProvider = "UserNames",dataProviderClass = DataProviders.class, dependsOnMethods = "testGetUserByName")
	public void testDeleteUserByName(String userName) {
		
		Response res = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(res.getStatusCode(), 200);
		
	}
	
}
