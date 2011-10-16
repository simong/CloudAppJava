package com.cloudapp.impl.model;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONObject;

import com.cloudapp.api.CloudAppException;
import com.cloudapp.api.model.CloudAppAccount;

public class CloudAppAccountImpl extends CloudAppModel implements CloudAppAccount {

  public CloudAppAccountImpl(JSONObject json) {
    this.json = json;
  }

  public long getId() throws CloudAppException {
    return getLong("id");
  }

  public String getEmail() throws CloudAppException {
    return getString("email");
  }

  public String getDomain() throws CloudAppException {
    return getString("domain");
  }

  public String getDomainHomePage() throws CloudAppException {
    return getString("domain_home_page");
  }

  public DefaultSecurity getDefaultSecurity() throws CloudAppException {
    boolean p = getBoolean("private_items");
    if (p) {
      return DefaultSecurity.PRIVATE;
    } else {
      return DefaultSecurity.PUBLIC;
    }
  }

  public boolean isSubscribed() throws CloudAppException {
    return getBoolean("subscribed");
  }

  public boolean isAlpha() throws CloudAppException {
    return getBoolean("alpha");
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

  public Date ActivatedAt() throws CloudAppException {
    try {
      String d = getString("activated_at");
      return format.parse(d);
    } catch (ParseException e) {
      throw new CloudAppException(500, "Could not parse the date.", e);
    }
  }

}
