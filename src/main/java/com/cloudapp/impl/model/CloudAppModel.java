package com.cloudapp.impl.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.cloudapp.api.CloudAppException;

public abstract class CloudAppModel {
  protected JSONObject json;
  protected static final DateFormat format = new SimpleDateFormat(
      "yyyy-MM-dd'T'HH:mm:ss'Z'");
  protected static final DateFormat formatBis = new SimpleDateFormat(
      "yyyy-MM-dd");

  protected String getString(String key) throws CloudAppException {
    try {
      return json.getString(key);
    } catch (JSONException e) {
      throw new CloudAppException(500, e.getMessage(), e);
    }
  }

  protected boolean getBoolean(String key) throws CloudAppException {
    try {
      return json.getBoolean(key);
    } catch (JSONException e) {
      throw new CloudAppException(500, e.getMessage(), e);
    }
  }

  protected long getLong(String key) throws CloudAppException {
    try {
      return json.getLong(key);
    } catch (JSONException e) {
      throw new CloudAppException(500, e.getMessage(), e);
    }
  }
}
