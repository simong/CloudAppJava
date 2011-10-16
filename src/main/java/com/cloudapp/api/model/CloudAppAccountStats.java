package com.cloudapp.api.model;

public interface CloudAppAccountStats {

  /**
   * @return The total number of items.
   */
  public long getItems();

  /**
   * @return The total number of vies for all your items.
   */
  public long getViews();

}
