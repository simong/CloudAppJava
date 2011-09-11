package com.cloudapp.api;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

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
  public JSONObject createBookmark(String name, String url) throws CloudAppException;

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
  public JSONArray createBookmarks(String[][] bookmarks) throws CloudAppException;

  public enum Type {
    ARCHIVE, AUDIO, BOOKMARK, IMAGE, TEXT, UNKNOWN, VIDEO
  }

  /**
   * Page through your items.
   * 
   * @see http://developer.getcloudapp.com/list-items
   * @param page
   *          The page to requests, starts at 1.
   * @param perPage
   *          Number of items per page (minimum=5)
   * @param type
   *          (Optional) One of the avaiable {@link Type types}
   * @param showDeleted
   *          Include the trashed items?
   * @param source
   *          (Optional) Only show items that originate from a certain app.The CloudApp
   *          Mac app uses the User-Agent Cloud/1.5.1 so all items it creates will have
   *          that User-Agent as their source. To list only the items created using the
   *          Mac app, use the parameter source=Cloud. Get fancy and list only the items
   *          created using a specific version of with source=Cloud/1.5.1.
   * @return A JSONArray with your items.
   * @throws CloudAppException
   */
  public JSONArray getItems(int page, int perPage, Type type, boolean showDeleted,
      String source) throws CloudAppException;

  /**
   * 
   * @see http://developer.getcloudapp.com/upload-file
   * @param file
   *          The file you wish to upload.
   * @throws CloudAppException
   * @return
   */
  public JSONObject upload(File file) throws CloudAppException;
}
