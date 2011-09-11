package com.cloudapp.api;

import org.json.JSONObject;

public interface CloudAppAccount {

  public enum DefaultSecurity {
    PRIVATE, PUBLIC
  }

  /**
   * Change the security of newly created items to either private (long links) or public
   * (short links). Privacy can be set on a per-item basis after creation.
   * 
   * @see http://developer.getcloudapp.com/change-default-security
   * @throws CloudAppException
   *           Either CloudApp sent an unexpected response back or something went wrong
   *           locally. If it's an error response from cloudapp, then the HTTP status code
   *           will be set on the exception.
   * @return
   */
  public JSONObject setDefaultSecurity(DefaultSecurity security) throws CloudAppException;

  /**
   * Change the email address for an account.
   * 
   * @see http://developer.getcloudapp.com/change-email
   * @throws CloudAppException
   *           Either CloudApp sent an unexpected response back or something went wrong
   *           locally. If it's an error response from cloudapp, then the HTTP status code
   *           will be set on the exception.
   * @param newEmail
   *          The address you wish to use.
   * @param currentPassword
   *          Your current password (as a confirmation mechanism.)
   * @return
   */
  public JSONObject setEmail(String newEmail, String currentPassword)
      throws CloudAppException;

  /**
   * Changes the current password.
   * 
   * @see http://developer.getcloudapp.com/change-password
   * @throws CloudAppException
   *           Either CloudApp sent an unexpected response back or something went wrong
   *           locally. If it's an error response from cloudapp, then the HTTP status code
   *           will be set on the exception.
   * @param newPassword
   *          The new password you want to use.
   * @param currentPassword
   *          Your old password.
   * @return
   */
  public JSONObject setPassword(String newPassword, String currentPassword)
      throws CloudAppException;

  /**
   * Dispatch an email containing a link to reset the account’s password.
   * 
   * @see http://developer.getcloudapp.com/forgot-password
   * @param email
   *          The email address.
   * @throws CloudAppException
   *           Either CloudApp sent an unexpected response back or something went wrong
   *           locally. If it's an error response from cloudapp, then the HTTP status code
   *           will be set on the exception.
   * @return
   */
  public JSONObject resetPassword(String email) throws CloudAppException;

  /**
   * Create a CloudApp account. Obviously there is no need to provide existing credentials
   * in order to this method to succeed.
   * 
   * @see http://developer.getcloudapp.com/register
   * @param email
   *          Your email address you wish to sign up with.
   * @param password
   *          The password you wish to you.
   * @param acceptTOS
   *          Wether or not you accept the Terms Of Services. (Hint: you probably want to
   *          pass true along here..)
   * @throws CloudAppException
   *           Either CloudApp sent an unexpected response back or something went wrong
   *           locally. If it's an error response from cloudapp, then the HTTP status code
   *           will be set on the exception.
   * @return
   */
  public JSONObject createAccount(String email, String password, boolean acceptTOS)
      throws CloudAppException;

  /**
   * Add or change the domain used for all links. Optionally, a URL may be provided to
   * redirect visitors to the custom domain’s root. <b>Pro users only</b>
   * 
   * @see http://developer.getcloudapp.com/set-custom-domain
   * @param domain
   * @param domainHomePage
   * @throws CloudAppException
   *           Either CloudApp sent an unexpected response back or something went wrong
   *           locally. If it's an error response from cloudapp, then the HTTP status code
   *           will be set on the exception.
   * @return
   */
  public JSONObject setCustomDomain(String domain, String domainHomePage)
      throws CloudAppException;

  /**
   * Get the basic details of the authenticated account. This is the same response as
   * returned by most account endpoints.
   * 
   * @see http://developer.getcloudapp.com/view-account-details
   * @throws CloudAppException
   *           Either CloudApp sent an unexpected response back or something went wrong
   *           locally. If it's an error response from cloudapp, then the HTTP status code
   *           will be set on the exception.
   * @return
   */
  public JSONObject getAccountDetails() throws CloudAppException;

  /**
   * Get the total number of items created and total views for all items of the
   * authenticated account.
   * 
   * @see http://developer.getcloudapp.com/view-account-stats
   * @throws CloudAppException
   *           Either CloudApp sent an unexpected response back or something went wrong
   *           locally. If it's an error response from cloudapp, then the HTTP status code
   *           will be set on the exception.
   * @return
   */
  public JSONObject getAccountStats() throws CloudAppException;

}
