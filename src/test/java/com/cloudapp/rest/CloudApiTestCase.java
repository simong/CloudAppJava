/*
 *  Copyright (c) 2010 Simon Gaeremynck <gaeremyncks@gmail.com>
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package com.cloudapp.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CloudApiTestCase {

  private static String mail;
  private static String password;
  private CloudApi api;

  @BeforeClass
  public static void beforeClass() throws IOException {
    InputStream in = CloudApiTestCase.class.getResourceAsStream("/credentials.properties");
    Properties props = new Properties();
    props.load(in);

    CloudApiTestCase.mail = props.getProperty("cred_mail", "alice@acme.com");
    CloudApiTestCase.password = props.getProperty("cred_password", "supersecretpassword");
  }

  @Before
  public void setUp() {
    api = new CloudApi(CloudApiTestCase.mail, CloudApiTestCase.password);
  }

  @Test
  public void testGetItems() throws JSONException, CloudApiException {
    api.getItems(1, 5, null, false);
    // If there is an exception than this test has failed..
  }

  @Test
  public void testFileUpload() throws CloudApiException, JSONException {
    JSONArray arr = api.getItems(1, 10, null, false);
    String name = arr.getJSONObject(0).getString("name");

    URL testFileURL = getClass().getResource("/test_file.txt");
    File testFile = new File(testFileURL.getPath());
    JSONObject o = api.uploadFile(testFile);
    Assert.assertEquals("test_file.txt", o.getString("name"));

    // Fetch the info.
    String url = o.getString("url");
    JSONObject item = api.getItem(url);
    Assert.assertEquals("test_file.txt", item.getString("name"));

    // Assert that it is in our list.
    arr = api.getItems(1, 10, null, false);
    Assert.assertNotSame(name, arr.getJSONObject(0).get("name"));

    // Now delete it.
    api.deleteItem(item.getString("href"));

    // Make sure it's from our list.
    arr = api.getItems(1, 10, null, false);
    Assert.assertEquals(name, arr.getJSONObject(0).get("name"));
  }

  @Test
  public void testBookmark() throws CloudApiException, JSONException {
    JSONObject o = api.createBookmark("My portfolio", "http://www.gaeremynck.com");
    Assert.assertEquals("My portfolio", o.getString("name"));
  }

}
