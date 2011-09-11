package com.cloudapp.impl;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudapp.api.CloudAppAccount;
import com.cloudapp.api.CloudAppException;

public class CloudAppAccountImpl extends CloudAppBase implements CloudAppAccount {

  private static final String REGISTER_URL = MY_CL_LY + "/register";
  private static final String ACCOUNT_URL = MY_CL_LY + "/account";
  private static final String ACCOUNT_STATS_URL = ACCOUNT_URL + "/stats";
  private static final String RESET_URL = MY_CL_LY + "/reset";
  private static final Logger LOGGER = LoggerFactory.getLogger(CloudAppAccountImpl.class);

  protected CloudAppAccountImpl(DefaultHttpClient client) {
    super(client);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setDefaultSecurity(com.cloudapp.api.CloudAppAccount.DefaultSecurity)
   */
  public JSONObject setDefaultSecurity(DefaultSecurity security) throws CloudAppException {
    try {
      JSONObject json = new JSONObject();
      JSONObject user = new JSONObject();
      user.put("private_items", (security == DefaultSecurity.PRIVATE) ? true : false);
      json.put("user", user);

      return (JSONObject) executePut(ACCOUNT_URL, json.toString(), 200);

    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setEmail(java.lang.String, java.lang.String)
   */
  public JSONObject setEmail(String newEmail, String currentPassword)
      throws CloudAppException {
    try {
      JSONObject json = new JSONObject();
      JSONObject user = new JSONObject();
      user.put("email", newEmail);
      user.put("current_password", currentPassword);
      json.put("user", user);

      return (JSONObject) executePut(ACCOUNT_URL, json.toString(), 200);

    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setPassword(java.lang.String, java.lang.String)
   */
  public JSONObject setPassword(String newPassword, String currentPassword)
      throws CloudAppException {
    try {
      JSONObject json = new JSONObject();
      JSONObject user = new JSONObject();
      user.put("password", newPassword);
      user.put("current_password", currentPassword);
      json.put("user", user);

      return (JSONObject) executePut(ACCOUNT_URL, json.toString(), 200);

    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#resetPassword(java.lang.String)
   */
  public JSONObject resetPassword(String email) throws CloudAppException {
    try {
      JSONObject json = new JSONObject();
      JSONObject user = new JSONObject();
      user.put("email", email);
      json.put("user", user);

      return (JSONObject) executePost(RESET_URL, json.toString(), 200);

    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#createAccount(java.lang.String,
   *      java.lang.String, boolean)
   */
  public JSONObject createAccount(String email, String password, boolean acceptTOS)
      throws CloudAppException {
    try {
      JSONObject json = new JSONObject();
      JSONObject user = new JSONObject();
      user.put("email", email);
      user.put("password", password);
      user.put("accept_tos", acceptTOS);
      json.put("user", user);
      return (JSONObject) executePost(REGISTER_URL, json.toString(), 201);
    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setCustomDomain(java.lang.String,
   *      java.lang.String)
   */
  public JSONObject setCustomDomain(String domain, String domainHomePage)
      throws CloudAppException {
    try {
      JSONObject json = new JSONObject();
      JSONObject user = new JSONObject();
      user.put("domain", domain);
      user.put("domain_home_page", domainHomePage);
      json.put("user", user);

      return (JSONObject) executePut(ACCOUNT_URL, json.toString(), 200);

    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#getAccountDetails()
   */
  public JSONObject getAccountDetails() throws CloudAppException {
    return (JSONObject) executeGet(ACCOUNT_URL);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#getAccountStats()
   */
  public JSONObject getAccountStats() throws CloudAppException {
    return (JSONObject) executeGet(ACCOUNT_STATS_URL);
  }
}