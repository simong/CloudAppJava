package com.cloudapp.rest;

import java.io.File;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

public interface CloudApi {

  /**
   * Add a bookmark.<br />
   * Authentication is required.
   * 
   * @param name
   *          The name of your bookmark.
   * @param url
   *          The URL to which this bookmark should point.
   * @return A JSONObject which contains the following:
   * 
   *         <pre>
   *          {
   *             "href": "http://my.cl.ly/items/1",
   *             "name": "CloudApp",
   *             "url": "http://cl.ly/d837",
   *             "item_type": "bookmark",
   *             "view_counter": 0,
   *             "icon": "http://my.cl.ly/images/item_types/bookmark.png",
   *             "redirect_url": "http://getcloudapp.com",
   *             "created_at": "2010-04-01T12:00:00Z",
   *             "updated_at": "2010-04-01T12:00:00Z"
   *           }
   * </pre>
   */
  public JSONObject createBookmark(String name, String url) throws CloudApiException;

  /**
   * Wrapper around {@link CloudApi#uploadFile(InputStream)}.
   * 
   * @param file
   *          The file that needs to be uploaded.
   * @return
   * 
   *         <pre>
   * {
   *   "href": "http://my.cl.ly/items/3",
   *   "name": "Screen shot 2010-04-01 at 12.00.00 AM.png",
   *   "url": "http://cl.ly/6571",
   *   "content_url": "http://cl.ly/6571/content",
   *   "item_type": "image",
   *   "view_counter": 0,
   *   "icon": "http://my.cl.ly/images/item_types/image.png",
   *   "remote_url":"http://f.cl.ly/items/3d7ba41682802c301150/Screen shot 2010-04-01 at 12.00.00 AM.png",
   *   "created_at": "2010-04-01T12:00:00Z",
   *   "updated_at": "2010-04-01T12:00:00Z"
   * }
   * 
   * </pre>
   */
  public JSONObject uploadFile(File file) throws CloudApiException;

  /**
   * Retrieve the items of the authenticated user.
   * 
   * @param page
   *          For paginating, paginating starts at 1.
   * @param itemsPerPage
   *          Number of items on a page, the default is 5.
   * @param type
   *          Filter items by type, i.e: images, video, ..
   * @param showDeleted
   *          true = Only show the trashed ones, false = only show the non-trashed ones.
   * @return <pre>
   * [
   * .., {
   *   "href": "http://my.cl.ly/items/1",
   *   "name": "CloudApp",
   *   "url": "http://cl.ly/d837",
   *   "item_type": "bookmark",
   *   "view_counter": 42,
   *   "icon": "http://my.cl.ly/images/item_types/bookmark.png",
   *   "redirect_url": "http://getcloudapp.com",
   *   "created_at": "2010-04-01T12:00:00Z",
   *   "updated_at": "2010-04-01T12:00:00Z"
   * }, 
   * ..
   * ]
   * </pre>
   * @throws CloudApiException
   */
  public JSONArray getItems(int page, int itemsPerPage, String type, boolean showDeleted)
      throws CloudApiException;

  /**
   * Retrieve the information for a specific item.
   * 
   * @param id
   *          The id of the file you wish to retrieve more info for.
   * @return JSONObject containing the information.
   * @throws CloudApiException
   */
  public JSONObject getItem(String id) throws CloudApiException;

  /**
   * Delete an item.
   * 
   * @param id
   *          The id of the item you wish to delete.
   * @throws CloudApiException
   */
  public void deleteItem(String id) throws CloudApiException;

}
