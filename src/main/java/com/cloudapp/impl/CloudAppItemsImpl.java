package com.cloudapp.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudapp.api.CloudAppException;
import com.cloudapp.api.CloudAppItems;

public class CloudAppItemsImpl extends CloudAppBase implements CloudAppItems {

  private static final Logger LOGGER = LoggerFactory.getLogger(CloudAppItemsImpl.class);
  private static final String ITEMS_URL = MY_CL_LY + "/items";

  public CloudAppItemsImpl(DefaultHttpClient client) {
    super(client);
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#createBookmark(java.lang.String,
   *      java.lang.String)
   */
  public JSONObject createBookmark(String name, String url) throws CloudAppException {
    try {
      JSONObject json = new JSONObject();
      json.put("item", createJSONBookmark(name, url));
      return (JSONObject) executePost(ITEMS_URL, json.toString(), 200);
    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#createBookmarks(java.lang.String[][])
   */
  public JSONArray createBookmarks(String[][] bookmarks) throws CloudAppException {
    try {
      JSONArray arr = new JSONArray();
      for (String[] bookmark : bookmarks) {
        arr.put(createJSONBookmark(bookmark[0], bookmark[1]));
      }
      JSONObject json = new JSONObject();
      json.put("items", arr);
      return (JSONArray) executePost(ITEMS_URL, json.toString(), 200);
    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  private JSONObject createJSONBookmark(String name, String url) throws JSONException {
    JSONObject item = new JSONObject();
    item.put("name", name);
    item.put("redirect_url", url);
    return item;
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
    try {
      if (perPage < 5)
        perPage = 5;
      if (page == 0)
        page = 1;

      HttpGet req = new HttpGet(ITEMS_URL);
      req.addHeader("Accept", "application/json");
      HttpParams params = new BasicHttpParams();
      params.setIntParameter("page", page);
      params.setIntParameter("per_page", perPage);
      params.setBooleanParameter("deleted", showDeleted);
      if (type != null) {
        params.setParameter("type", type.toString().toLowerCase());
      }
      if (source != null) {
        params.setParameter("source", source);
      }
      req.setParams(params);

      HttpResponse response = client.execute(req);
      int status = response.getStatusLine().getStatusCode();
      String responseBody = EntityUtils.toString(response.getEntity());
      if (status == 200) {
        return new JSONArray(responseBody);
      }

      throw new CloudAppException(status, responseBody, null);
    } catch (ClientProtocolException e) {
      LOGGER.error("Something went wrong trying to contact the CloudApp API.", e);
      throw new CloudAppException(500,
          "Something went wrong trying to contact the CloudApp API", e);
    } catch (IOException e) {
      LOGGER.error("Something went wrong trying to contact the CloudApp API.", e);
      throw new CloudAppException(500,
          "Something went wrong trying to contact the CloudApp API.", e);
    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }
}
