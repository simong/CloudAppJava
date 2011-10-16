package com.cloudapp.impl;

import java.io.File;
import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudapp.api.CloudApp;
import com.cloudapp.api.CloudAppException;
import com.cloudapp.api.model.CloudAppAccount;
import com.cloudapp.api.model.CloudAppAccount.DefaultSecurity;
import com.cloudapp.api.model.CloudAppAccountStats;
import com.cloudapp.api.model.CloudAppItem;

public class CloudAppImpl implements CloudApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(CloudAppImpl.class);

  private AccountImpl account;
  private CloudAppItemsImpl items;

  public CloudAppImpl(String mail, String pw) {
    DefaultHttpClient client = new DefaultHttpClient();
    client.setReuseStrategy(new DefaultConnectionReuseStrategy());

    // Try to authenticate.
    AuthScope scope = new AuthScope("my.cl.ly", 80);
    client.getCredentialsProvider().setCredentials(scope,
        new UsernamePasswordCredentials(mail, pw));
    LOGGER.debug("Authentication set.");

    account = new AccountImpl(client);
    items = new CloudAppItemsImpl(client);
  }

  public CloudAppImpl() {
    DefaultHttpClient client = new DefaultHttpClient();
    client.setReuseStrategy(new DefaultConnectionReuseStrategy());
    account = new AccountImpl(client);
    items = new CloudAppItemsImpl(client);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setDefaultSecurity(com.cloudapp.api.CloudAppAccount.DefaultSecurity)
   */
  public CloudAppAccount setDefaultSecurity(DefaultSecurity security)
      throws CloudAppException {
    return account.setDefaultSecurity(security);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setEmail(java.lang.String, java.lang.String)
   */
  public CloudAppAccount setEmail(String newEmail, String currentPassword)
      throws CloudAppException {
    return account.setEmail(newEmail, currentPassword);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#setPassword(java.lang.String, java.lang.String)
   */
  public CloudAppAccount setPassword(String newPassword, String currentPassword)
      throws CloudAppException {
    return account.setPassword(newPassword, currentPassword);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#resetPassword(java.lang.String)
   */
  public void resetPassword(String email) throws CloudAppException {
    account.resetPassword(email);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#createAccount(java.lang.String,
   *      java.lang.String, boolean)
   */
  public CloudAppAccount createAccount(String email, String password, boolean acceptTOS)
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
  public CloudAppAccount setCustomDomain(String domain, String domainHomePage)
      throws CloudAppException {
    return account.setCustomDomain(domain, domainHomePage);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#getAccountDetails()
   */
  public CloudAppAccount getAccountDetails() throws CloudAppException {
    return account.getAccountDetails();
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppAccount#getAccountStats()
   */
  public CloudAppAccountStats getAccountStats() throws CloudAppException {
    return account.getAccountStats();
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#createBookmark(java.lang.String,
   *      java.lang.String)
   */
  public CloudAppItem createBookmark(String name, String url) throws CloudAppException {
    return items.createBookmark(name, url);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#createBookmarks(java.lang.String[][])
   */
  public List<CloudAppItem> createBookmarks(String[][] bookmarks)
      throws CloudAppException {
    return items.createBookmarks(bookmarks);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#getItem(java.lang.String)
   */
  public CloudAppItem getItem(String url) throws CloudAppException {
    return items.getItem(url);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#getItems(int, int,
   *      com.cloudapp.api.CloudAppItems.Type, boolean, java.lang.String)
   */
  public List<CloudAppItem> getItems(int page, int perPage, CloudAppItem.Type type,
      boolean showDeleted, String source) throws CloudAppException {
    return items.getItems(page, perPage, type, showDeleted, source);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#upload(java.io.File)
   */
  public CloudAppItem upload(File file) throws CloudAppException {
    return items.upload(file);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#delete(com.cloudapp.api.model.CloudAppItem)
   */
  public CloudAppItem delete(CloudAppItem item) throws CloudAppException {
    return items.delete(item);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#recover(com.cloudapp.api.model.CloudAppItem)
   */
  public CloudAppItem recover(CloudAppItem item) throws CloudAppException {
    return items.recover(item);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#setSecurity(com.cloudapp.api.model.CloudAppItem,
   *      boolean)
   */
  public CloudAppItem setSecurity(CloudAppItem item, boolean is_private)
      throws CloudAppException {
    return items.setSecurity(item, is_private);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#rename(com.cloudapp.api.model.CloudAppItem,
   *      java.lang.String)
   */
  public CloudAppItem rename(CloudAppItem item, String name) throws CloudAppException {
    return items.rename(item, name);
  }

}
