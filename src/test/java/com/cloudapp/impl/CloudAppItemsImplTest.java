package com.cloudapp.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cloudapp.api.CloudAppException;
import com.cloudapp.api.model.CloudAppItem;

public class CloudAppItemsImplTest extends BaseTestCase {

  private File file;

  @Before
  public void setUp() {
    super.setUp();
    URL fileurl = getClass().getResource("/test_file.txt");
    file = new File(fileurl.getPath());
  }

  @Test
  public void testCreateMultipleBookmarks() throws CloudAppException {
    String[] site = new String[] { "Gaeremynck.com", "http://www.gaeremynck.com" };
    String[] blog = new String[] { "My blog", "http://blog.gaeremynck.com" };
    String[][] bookmarks = new String[][] { site, blog };
    List<CloudAppItem> l = api.createBookmarks(bookmarks);
    assertEquals(2, l.size());
  }

  @Test
  public void testDelete() throws JSONException, CloudAppException {
    api.upload(file);
    List<CloudAppItem> a = api.getItems(1, 10, null, false, null);
    CloudAppItem item = api.delete(a.get(0));
    Assert.assertEquals(true, item.isTrashed());
    List<CloudAppItem> b = api.getItems(1, 10, null, false, null);
    Assert.assertNotSame(b.get(0), a.get(0));
    item = api.recover(item);
    Assert.assertEquals(false, item.isTrashed());
  }

  @Test
  public void testUpload() throws CloudAppException, JSONException {
    CloudAppItem o = api.upload(file);
    assertEquals("test_file.txt", o.getName());
  }
}
