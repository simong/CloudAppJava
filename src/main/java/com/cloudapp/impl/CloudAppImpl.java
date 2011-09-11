package com.cloudapp.impl;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudapp.api.CloudApp;
import com.cloudapp.api.CloudAppException;

public class CloudAppImpl implements CloudApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(CloudAppImpl.class);

  private CloudAppAccountImpl account;
  private CloudAppItemsImpl items;

  public CloudAppImpl(String mail, String pw) {
    DefaultHttpClient client = new DefaultHttpClient();
    client.setReuseStrategy(new DefaultConnectionReuseStrategy());

    // Try to authenticate.
    AuthScope scope = new AuthScope("my.cl.ly", 80);
    client.getCredentialsProvider().setCredentials(scope,
        new UsernamePasswordCredentials(mail, pw));
    LOGGER.debug("Authentication set.");

    account = new CloudAppAccountImpl(client);
    items = new CloudAppItemsImpl(client);
  }

  public CloudAppImpl() {
    DefaultHttpClient client = new DefaultHttpClient();
    client.setReuseStrategy(new DefaultConnectionReuseStrategy());
    account = new CloudAppAccountImpl(client);
    items = new CloudAppItemsImpl(client);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setDefaultSecurity(com.cloudapp.api.CloudAppAccount.DefaultSecurity)
   */
  public JSONObject setDefaultSecurity(DefaultSecurity security) throws CloudAppException {
    return account.setDefaultSecurity(security);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setEmail(java.lang.String, java.lang.String)
   */
  public JSONObject setEmail(String newEmail, String currentPassword)
      throws CloudAppException {
    return account.setEmail(newEmail, currentPassword);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setPassword(java.lang.String, java.lang.String)
   */
  public JSONObject setPassword(String newPassword, String currentPassword)
      throws CloudAppException {
    return account.setPassword(newPassword, currentPassword);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#resetPassword(java.lang.String)
   */
  public JSONObject resetPassword(String email) throws CloudAppException {
    return account.resetPassword(email);
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
    return account.createAccount(email, password, acceptTOS);
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
    return account.setCustomDomain(domain, domainHomePage);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#getAccountDetails()
   */
  public JSONObject getAccountDetails() throws CloudAppException {
    return account.getAccountDetails();
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#getAccountStats()
   */
  public JSONObject getAccountStats() throws CloudAppException {
    return account.getAccountStats();
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#createBookmark(java.lang.String,
   *      java.lang.String)
   */
  public JSONObject createBookmark(String name, String url) throws CloudAppException {
    return items.createBookmark(name, url);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#createBookmarks(java.lang.String[][])
   */
  public JSONArray createBookmarks(String[][] bookmarks) throws CloudAppException {
    return items.createBookmarks(bookmarks);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#getItems(int, int,
   *      com.cloudapp.api.CloudAppItems.Type, boolean, java.lang.String)
   */
  public JSONArray getItems(int page, int perPage, Type type, boolean showDeleted,
      String source) throws CloudAppException {
    return items.getItems(page, perPage, type, showDeleted, source);
  }
}
