package com.cloudapp.api;

import java.io.File;
import java.util.List;

import com.cloudapp.api.model.CloudAppItem;

public interface CloudAppItems {

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
