package com.cloudapp.impl;

import java.io.File;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.cloudapp.api.CloudAppException;

public class CloudAppItemsImplTest extends BaseTestCase {

  @Test
  public void testCreateMultipleBookmarks() throws CloudAppException {
    String[] site = new String[] { "Gaeremynck.com", "http://www.gaeremynck.com" };
    String[] blog = new String[] { "My blog", "http://blog.gaeremynck.com" };
    String[][] bookmarks = new String[][] { site, blog };
    JSONArray arr = api.createBookmarks(bookmarks);
    assertEquals(2, arr.length());
  }

  @Test
  public void testGetMyItems() throws JSONException, CloudAppException {
    JSONArray arr = api.getItems(1, 10, null, false, null);
    System.err.println(arr.toString(2));
  }
  
  @Test
  public void testUpload() throws CloudAppException, JSONException {
    URL fileurl = getClass().getResource("/test_file.txt");
    File file = new File(fileurl.getPath());
    JSONObject json = api.upload(file);
    assertEquals("test_file.txt", json.getString("name"));
  }
}
