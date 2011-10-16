package com.cloudapp.impl;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import com.cloudapp.api.CloudApp;
import com.cloudapp.api.CloudAppException;
import com.cloudapp.api.model.CloudAppAccount;
import com.cloudapp.api.model.CloudAppAccount.DefaultSecurity;

public class CloudAppAccountImplTest extends BaseTestCase {

  @Test
  public void testGetAccountStats() throws CloudAppException {
    api.getAccountStats();
  }

  @Test
  public void testDefaultSecurity() throws CloudAppException, JSONException {
    api.setDefaultSecurity(DefaultSecurity.PRIVATE);
    CloudAppAccount acc = api.getAccountDetails();
    Assert.assertEquals(acc.getDefaultSecurity(), DefaultSecurity.PRIVATE);

    api.setDefaultSecurity(DefaultSecurity.PUBLIC);
    acc = api.getAccountDetails();
    Assert.assertEquals(acc.getDefaultSecurity(), DefaultSecurity.PUBLIC);

    api.setDefaultSecurity(DefaultSecurity.PRIVATE);
    acc = api.getAccountDetails();
    Assert.assertEquals(acc.getDefaultSecurity(), DefaultSecurity.PRIVATE);
  }

  // @Test
  public void testCreateAccount() throws CloudAppException, JSONException {
    CloudApp api = new CloudAppImpl();
    String email = "<fill in your own email here>";
    CloudAppAccount acc = api.createAccount(email, "mycoolpw", true);
    Assert.assertEquals(email, acc.getEmail());
  }
}
