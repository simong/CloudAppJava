package com.cloudapp.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
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
import com.cloudapp.api.model.CloudAppProgressListener;
import com.cloudapp.api.model.CloudAppItem;
import com.cloudapp.impl.model.CloudAppItemImpl;

public class CloudAppItemsImpl extends CloudAppBase {

  private static final Logger LOGGER = LoggerFactory.getLogger(CloudAppItemsImpl.class);
  private static final String ITEMS_URL = MY_CL_LY + "/items";
  private static final String NEW_ITEM_URL = ITEMS_URL + "/new";

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
  public CloudAppItem createBookmark(String name, String url) throws CloudAppException {
    try {
      JSONObject json = createBody(new String[] { "name", "redirect_url" }, new String[] {
          name, url });
      json = (JSONObject) executePost(ITEMS_URL, json.toString(), 200);
      return new CloudAppItemImpl(json);
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
  public List<CloudAppItem> createBookmarks(String[][] bookmarks)
      throws CloudAppException {
    try {
      JSONArray arr = new JSONArray();
      for (String[] bookmark : bookmarks) {
        arr.put(createJSONBookmark(bookmark[0], bookmark[1]));
      }

      JSONObject json = new JSONObject();
      json.put("items", arr);
      arr = (JSONArray) executePost(ITEMS_URL, json.toString(), 200);

      List<CloudAppItem> items = new ArrayList<CloudAppItem>();
      for (int i = 0; i < arr.length(); i++) {
        items.add(new CloudAppItemImpl(arr.getJSONObject(i)));
      }
      return items;
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
   *      com.cloudapp.api.CloudAppItem.Type, boolean, java.lang.String)
   */
  public List<CloudAppItem> getItems(int page, int perPage, CloudAppItem.Type type,
      boolean showDeleted, String source) throws CloudAppException {
    try {
      if (perPage < 5)
        perPage = 5;
      if (page == 0)
        page = 1;
      
      List<String> params = new ArrayList<String>();
      params.add("page="+page);
      params.add("per_page="+perPage);
      params.add("deleted="+ (showDeleted ? "true" : "false"));
      
      if (type != null)
      {
        params.add("type=" + type.toString().toLowerCase());
      }
      if (source != null)
      {
        params.add("source=" + source);
      }

      String queryString = StringUtils.join(params.iterator(), "&");
      HttpGet req = new HttpGet(ITEMS_URL + "?" + queryString);
      req.addHeader("Accept", "application/json");
      
      HttpResponse response = client.execute(req);
      int status = response.getStatusLine().getStatusCode();
      String responseBody = EntityUtils.toString(response.getEntity());
      if (status == 200) {
        JSONArray arr = new JSONArray(responseBody);
        List<CloudAppItem> items = new ArrayList<CloudAppItem>();
        for (int i = 0; i < arr.length(); i++) {
          items.add(new CloudAppItemImpl(arr.getJSONObject(i)));
        }
        return items;
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

    /**
     *
     * {@inheritDoc}
     *
     * @see com.cloudapp.api.CloudAppItems#upload(java.io.File)
     */
    public CloudAppItem upload(File file) throws CloudAppException {
        return upload( file, CloudAppProgressListener.NO_OP );
    }

    public CloudAppItem upload(File file, CloudAppProgressListener listener) throws CloudAppException {
        try {
            // Do a GET request so we have the S3 endpoint
            HttpGet req = new HttpGet(NEW_ITEM_URL);
            req.addHeader("Accept", "application/json");
            HttpResponse response = client.execute(req);
            int status = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            if (status != 200)
                throw new CloudAppException(status, responseBody, null);

            JSONObject json = new JSONObject(responseBody);
            if (!json.has("params")) {
                // Something went wrong, maybe we crossed the treshold?
                if (json.getInt("uploads_remaining") == 0) {
                    throw new CloudAppException(200, "Uploads remaining is 0", null);
                }
                throw new CloudAppException(500, "Missing params object from the CloudApp API.",
                        null);
            }

            return uploadToAmazon(json, file, listener);

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

  /**
   * Uploads a file to S3
   * 
   *
   * @param json
   * @param file
   * @param listener
   * @return
   * @throws JSONException
   * @throws CloudAppException
   * @throws ParseException
   * @throws IOException
   */
  private CloudAppItem uploadToAmazon(JSONObject json, File file, CloudAppProgressListener listener) throws JSONException,
      CloudAppException, ParseException, IOException {
    JSONObject params = json.getJSONObject("params");
    MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
    // Add all the plain parameters.
    @SuppressWarnings("rawtypes")
    Iterator keys = params.keys();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      entity.addPart(key, new StringBody(params.getString(key)));
    }

    // Add the actual file.
    // We have to use the 'file' parameter for the S3 storage.
    InputStreamBody stream = new CloudAppInputStream(file, listener);
    entity.addPart("file", stream);

    HttpPost uploadRequest = new HttpPost(json.getString("url"));
    uploadRequest.addHeader("Accept", "application/json");
    uploadRequest.setEntity(entity);

    // Perform the actual upload.
    // uploadMethod.setFollowRedirects(true);
    HttpResponse response = client.execute(uploadRequest);
    int status = response.getStatusLine().getStatusCode();
    String body = EntityUtils.toString(response.getEntity());
    if (status == 200) {
      return new CloudAppItemImpl(new JSONObject(body));
    }
    throw new CloudAppException(status, "Was unable to upload the file to amazon:\n"
        + body, null);
  }

  public CloudAppItem delete(CloudAppItem item) throws CloudAppException {
    JSONObject o = (JSONObject) executeDelete(item.getHref());
    return new CloudAppItemImpl(o);
  }

  public CloudAppItem recover(CloudAppItem item) throws CloudAppException {
    try {
      JSONObject json = createBody(new String[] { "deleted_at" },
          new Object[] { JSONObject.NULL });
      json.put("deleted", true);
      json = (JSONObject) executePut(item.getHref(), json.toString(), 200);
      return new CloudAppItemImpl(json);
    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  public CloudAppItem setSecurity(CloudAppItem item, boolean is_private)
      throws CloudAppException {
    try {
      JSONObject json = createBody(new String[] { "private" },
          new Object[] { is_private });
      json = (JSONObject) executePut(item.getHref(), json.toString(), 200);
      return new CloudAppItemImpl(json);
    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  public CloudAppItem rename(CloudAppItem item, String name) throws CloudAppException {
    try {
      JSONObject json = createBody(new String[] { "name" }, new Object[] { name });
      json = (JSONObject) executePut(item.getHref(), json.toString(), 200);
      return new CloudAppItemImpl(json);
    } catch (JSONException e) {
      LOGGER.error("Something went wrong trying to handle JSON.", e);
      throw new CloudAppException(500, "Something went wrong trying to handle JSON.", e);
    }
  }

  public CloudAppItem getItem(String url) throws CloudAppException {
    JSONObject json = (JSONObject) executeGet(url);
    return new CloudAppItemImpl(json);
  }

  private JSONObject createBody(String[] keys, Object[] values) throws JSONException {
    JSONObject json = new JSONObject();
    JSONObject item = new JSONObject();
    for (int i = 0; i < keys.length; i++) {
      item.put(keys[i], values[i]);
    }
    json.put("item", item);
    return json;
  }

}
