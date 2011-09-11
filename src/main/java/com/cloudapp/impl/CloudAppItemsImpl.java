package com.cloudapp.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

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
import com.cloudapp.api.CloudAppItems;

public class CloudAppItemsImpl extends CloudAppBase implements CloudAppItems {

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

  /**
   * 
   * {@inheritDoc}
   * 
   * @see com.cloudapp.api.CloudAppItems#upload(java.io.File)
   */
  public JSONObject upload(File file) throws CloudAppException {
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
          throw new CloudAppException(200, json.getString("message"), null);
        }
        throw new CloudAppException(500, "Missing params object from the CloudApp API.",
            null);
      }

      return uploadToAmazon(json, file);

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
   * @param json
   * @param file
   * @return
   * @throws JSONException
   * @throws CloudAppException
   * @throws ParseException
   * @throws IOException
   */
  private JSONObject uploadToAmazon(JSONObject json, File file) throws JSONException,
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
    InputStreamBody stream = new CloudAppInputStream(file);
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
      return new JSONObject(body);
    }
    throw new CloudAppException(status, "Was unable to upload the file to amazon:\n"
        + body, null);
  }
}
