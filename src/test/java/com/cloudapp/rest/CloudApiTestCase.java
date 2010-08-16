package com.cloudapp.rest;

import java.io.File;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cloudapp.rest.CloudApi;
import com.cloudapp.rest.CloudApiException;
import com.cloudapp.rest.CloudApiImpl;

public class CloudApiTestCase {

  private CloudApi api;

  @Before
  public void setUp() {
    api = new CloudApiImpl("mail", "password");
  }

  @Test
  public void testGetItem() throws CloudApiException, JSONException {
    JSONObject o = api.getItem("ce60269c33960cddc7ad");
    System.out.println(o.toString(4));

  }

  @Test
  public void testGetItems() throws JSONException, CloudApiException {
    JSONArray arr = api.getItems(1, 5, null, false);
    System.out.println(arr.toString(4));

    Assert.assertEquals(5, arr.length());
  }

  @Test
  public void testFileUpload() throws CloudApiException, JSONException {
    JSONArray arr = api.getItems(1, 10, null, false);
    int length = arr.length();

    URL testFileURL = getClass().getResource("/test_file.txt");
    File testFile = new File(testFileURL.getPath());
    JSONObject o = api.uploadFile(testFile);
    System.out.println(o.toString(2));
    Assert.assertEquals("test_file.txt", o.getString("name"));

    // Fetch the info.
    String id = o.getString("private_slug");
    JSONObject item = api.getItem(id);
    System.out.println(item.toString(2));
    Assert.assertEquals("test_file.txt", item.getString("name"));

    // Assert that it is in our list.
    arr = api.getItems(1, 10, null, false);
    Assert.assertEquals(length + 1, arr.length());

    // Now delete it.
    api.deleteItem(item.getString("href"));

    // Make sure it's from our list.
    arr = api.getItems(1, 10, null, false);
    Assert.assertEquals(length, arr.length());

  }

}
