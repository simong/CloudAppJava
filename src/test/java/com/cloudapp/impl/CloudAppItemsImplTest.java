package com.cloudapp.impl;

import org.json.JSONArray;
import org.json.JSONException;
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
}
