package com.cloudapp.impl.model;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONObject;

import com.cloudapp.api.CloudAppException;
import com.cloudapp.api.model.CloudAppItem;

public class CloudAppItemImpl extends CloudAppModel implements CloudAppItem {

  public CloudAppItemImpl(JSONObject json) {
    this.json = json;
  }

  public String getHref() throws CloudAppException {
    return getString("href");
  }

  public String getName() throws CloudAppException {
    return getString("name");
  }

  public boolean isPrivate() throws CloudAppException {
    return getBoolean("private");
  }

  public boolean isSubscribed() throws CloudAppException {
    return getBoolean("subscribed");
  }

  public boolean isTrashed() throws CloudAppException {
    String d = getString("deleted_at");
    return (d == null || d == "null");
  }

  public String getUrl() throws CloudAppException {
    return getString("url");
  }

  public String getContentUrl() throws CloudAppException {
    return getString("content_url");
  }

  public Type getItemType() throws CloudAppException {
    String t = getString("item_type");
    try {
      return Type.valueOf(t.toUpperCase());
    } catch (IllegalArgumentException e) {
      return Type.UNKNOWN;
    }
  }

  public long getViewCounter() throws CloudAppException {
    return getLong("view_counter");
  }

  public String getIconUrl() throws CloudAppException {
    return getString("icon");
  }

  public String getRemoteUrl() throws CloudAppException {
    return getString("remote_url");
  }

  public String getRedirectUrl() throws CloudAppException {
    return getString("redirect_url");
  }

  public String getSource() throws CloudAppException {
    return getString("source");
  }

  public Date getCreatedAt() throws CloudAppException {
    try {
      String d = getString("created_at");
      return format.parse(d);
    } catch (ParseException e) {
      throw new CloudAppException(500, "Could not parse the date.", e);
    }
  }

  public Date getUpdatedAt() throws CloudAppException {
    try {
      String d = getString("updated_at");
      return format.parse(d);
    } catch (ParseException e) {
      throw new CloudAppException(500, "Could not parse the date.", e);
    }
  }

  public Date getDeletedAt() throws CloudAppException {
    try {
      String d = getString("deleted_at");
      return format.parse(d);
    } catch (ParseException e) {
      throw new CloudAppException(500, "Could not parse the date.", e);
    }
  }

}
