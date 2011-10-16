package com.cloudapp.impl.model;

import com.cloudapp.api.model.CloudAppAccountStats;

public class CloudAppAccountStatsImpl implements CloudAppAccountStats {

  private long items;
  private long views;

  public CloudAppAccountStatsImpl(long items, long views) {
    this.items = items;
    this.views = views;
  }

  public long getItems() {
    return items;
  }

  public long getViews() {
    return views;
  }

}
