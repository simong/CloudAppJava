package com.cloudapp.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.cloudapp.api.CloudApp;
import com.cloudapp.api.CloudAppException;
import com.cloudapp.api.CloudAppAccount.DefaultSecurity;

public class CloudAppAccountImplTest extends BaseTestCase {

  @Test
  public void testGetAccountStats() throws CloudAppException {
    JSONObject json = api.getAccountStats();
    Assert.assertEquals(json.has("items"), true);
    Assert.assertEquals(json.has("views"), true);
  }

  @Test
  public void testGetAccountDetails() throws CloudAppException, JSONException {
    JSONObject json = api.getAccountDetails();
    Assert.assertEquals(json.has("email"), true);
  }
  
  @Test
  public void testDefaultSecurity() throws CloudAppException, JSONException {
    api.setDefaultSecurity(DefaultSecurity.PRIVATE);
    JSONObject json = api.getAccountDetails();
    Assert.assertEquals(json.getBoolean("private_items"), true);
    

    api.setDefaultSecurity(DefaultSecurity.PUBLIC);
    json = api.getAccountDetails();
    Assert.assertEquals(json.getBoolean("private_items"), false);

    api.setDefaultSecurity(DefaultSecurity.PRIVATE);
    json = api.getAccountDetails();
    Assert.assertEquals(json.getBoolean("private_items"), true);
  }

  //@Test
  public void testCreateAccount() throws CloudAppException, JSONException {
    CloudApp api = new CloudAppImpl();
    String email = "<fill in your own email here>";
    JSONObject o = api.createAccount(email, "mycoolpw", true);
    Assert.assertEquals(email, o.getString("email"));
  }
}
