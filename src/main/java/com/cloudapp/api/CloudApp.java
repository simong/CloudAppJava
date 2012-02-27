package com.cloudapp.api;

import java.io.File;
import java.util.List;

import com.cloudapp.api.model.CloudAppAccount;
import com.cloudapp.api.model.CloudAppAccountStats;
import com.cloudapp.api.model.CloudAppItem;
import com.cloudapp.api.model.CloudAppProgressListener;

public interface CloudApp {

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
  public CloudAppAccount setDefaultSecurity(CloudAppAccount.DefaultSecurity security)
      throws CloudAppException;

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
  public CloudAppAccount setEmail(String newEmail, String currentPassword)
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
  public CloudAppAccount setPassword(String newPassword, String currentPassword)
      throws CloudAppException;

  /**
   * Dispatch an email containing a link to reset the account's password.
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
  public void resetPassword(String email) throws CloudAppException;

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
  public CloudAppAccount createAccount(String email, String password, boolean acceptTOS)
      throws CloudAppException;

  /**
   * Add or change the domain used for all links. Optionally, a URL may be provided to
   * redirect visitors to the custom domain's root. <b>Pro users only</b>
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
  public CloudAppAccount setCustomDomain(String domain, String domainHomePage)
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
  public CloudAppAccount getAccountDetails() throws CloudAppException;

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
  public CloudAppAccountStats getAccountStats() throws CloudAppException;

  //
  // ITEMS //
  //

  /**
   * Create a bookmark to a URL.
   * 
   * @see http://developer.getcloudapp.com/bookmark-link
   * @param name
   *          The name of your bookmark
   * @param url
   *          The url you wish to bookmark.
   * @throws CloudAppException
   * @return
   */
  public CloudAppItem createBookmark(String name, String url) throws CloudAppException;

  /**
   * Create multiple bookmarks in a single request.
   * 
   * @see http://developer.getcloudapp.com/bookmark-multiple-links
   * @param bookmarks
   *          An array of String arrays. Each String array should contain two Strings, the
   *          first being the name of the bookmark and the second the url.
   * @throws CloudAppException
   * @return
   */
  public List<CloudAppItem> createBookmarks(String[][] bookmarks)
      throws CloudAppException;

  /**
   * Get metadata about a cl.ly URL.
   * 
   * @see http://developer.getcloudapp.com/view-item
   * @param url
   *          The url
   * @return
   * @throws CloudAppException
   */
  public CloudAppItem getItem(String url) throws CloudAppException;

  /**
   * Page through your items.
   * 
   * @see http://developer.getcloudapp.com/list-items
   * @param page
   *          The page to requests, starts at 1.
   * @param perPage
   *          Number of items per page (minimum=5)
   * @param type
   *          (Optional) One of the avaiable {@link CloudAppItem.Type types}
   * @param showDeleted
   *          Include the trashed items?
   * @param source
   *          (Optional) Only show items that originate from a certain app.The CloudApp
   *          Mac app uses the User-Agent Cloud/1.5.1 so all items it creates will have
   *          that User-Agent as their source. To list only the items created using the
   *          Mac app, use the parameter source=Cloud. Get fancy and list only the items
   *          created using a specific version of with source=Cloud/1.5.1.
   * @return A list with your items.
   * @throws CloudAppException
   */
  public List<CloudAppItem> getItems(int page, int perPage, CloudAppItem.Type type,
      boolean showDeleted, String source) throws CloudAppException;

  /**
   * 
   * @see http://developer.getcloudapp.com/upload-file
   * @param file
   *          The file you wish to upload.
   * @throws CloudAppException
   * @return
   */
  public CloudAppItem upload(File file) throws CloudAppException;

  /**
   *
   * @see http://developer.getcloudapp.com/upload-file
   * @param file
   *          The file you wish to upload.
   * @param listener
   *          To receive progress updates during upload
   * @throws CloudAppException
   * @return
   */
  public CloudAppItem upload(File file, CloudAppProgressListener listener) throws CloudAppException;

  /**
   * Deletes an item
   * 
   * @param item
   *          The item to delete
   * @throws CloudAppException
   */
  public CloudAppItem delete(CloudAppItem item) throws CloudAppException;

  /**
   * Recover an item in the trash
   * 
   * @param item
   *          The item to recover
   * @throws CloudAppException
   */
  public CloudAppItem recover(CloudAppItem item) throws CloudAppException;

  /**
   * Makes an item either public or private.
   * 
   * @param item
   * @param is_private
   *          true=private, false=public.
   * @throws CloudAppException
   * @return
   */
  public CloudAppItem setSecurity(CloudAppItem item, boolean is_private)
      throws CloudAppException;

  /**
   * Change the name of an item
   * 
   * @param item
   * @param name
   * @throws CloudAppException
   * @return
   */
  public CloudAppItem rename(CloudAppItem item, String name) throws CloudAppException;

}
