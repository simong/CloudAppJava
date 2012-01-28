package com.cloudapp.api.model;

import java.util.Date;

import com.cloudapp.api.CloudAppException;

/**
 *
 */
public interface CloudAppItem {

  enum Type {
    AUDIO, BOOKMARK, IMAGE, UNKNOWN, VIDEO;
  }

  /**
   * @return A unique URL that points to this resource. ie:
   *         "http://my.cl.ly/items/1912729"
   * @throws CloudAppExtion
   */
  String getHref() throws CloudAppException;

  /**
   * @return The name of this resource.
   * @throws CloudAppExtion
   */
  String getName() throws CloudAppException;

  /**
   * @return Wether or not this is item is marked as private
   * @throws CloudAppExtion
   */
  boolean isPrivate() throws CloudAppException;

  /**
   * @return
   */
  boolean isSubscribed() throws CloudAppException;

  /**
   * @return Wether or not an item resides in the trash bin.
   */
  public boolean isTrashed() throws CloudAppException;

  /**
   * @return A short url to this resource. ie: "http://cl.ly/2wr4"
   * @throws CloudAppExtion
   */
  String getUrl() throws CloudAppException;

  /**
   * @return The url to the actual content of this resource. ie:
   *         "http://cl.ly/2wr4/CloudApp_Logo.png"
   * @throws CloudAppExtion
   */
  String getContentUrl() throws CloudAppException;

  /**
   * @return The type of this resource. ie: bookmark, image, ..
   * @throws CloudAppExtion
   */
  Type getItemType() throws CloudAppException;

  /**
   * @return How many times this item has been viewed.
   * @throws CloudAppExtion
   */
  long getViewCounter() throws CloudAppException;

  /**
   * @return A url that points to the icon of this resource's type. ie:
   *         "http://my.cl.ly/images/new/item-types/image.png"
   * @throws CloudAppExtion
   */
  String getIconUrl() throws CloudAppException;

  /**
   * @return A url that you can use to publicly point to this url. ie:
   *         "http://f.cl.ly/items/7c7aea1395c3db0aee18/CloudApp%20Logo.png"
   * @throws CloudAppExtion
   */
  String getRemoteUrl() throws CloudAppException;

  /**
   * @return
   * @throws CloudAppExtion
   */
  String getRedirectUrl() throws CloudAppException;
  
  /**
   * @return A url that points to a thumbnail of this item if one is available, 
   *         null otherwise. ie: "http://thumbs.cl.ly/2wr4"
   * @throws CloudAppException
   */
  String getThumbnailUrl() throws CloudAppException;

  /**
   * @return Identifies the app that uploaded this item. ie:
   *         "Cloud/1.5.1 CFNetwork/520.0.13 Darwin/11.0.0 (x86_64) (MacBookPro5%2C5)"
   * @throws CloudAppExtion
   */
  String getSource() throws CloudAppException;

  /**
   * @return When this item was created.
   * @throws CloudAppExtion
   */
  Date getCreatedAt() throws CloudAppException;

  /**
   * @return When this item was last updated. (or null if it has not been)
   * @throws CloudAppExtion
   */
  Date getUpdatedAt() throws CloudAppException;

  /**
   * @return When this item was deleted. (or null if it has not been)
   * @throws CloudAppExtion
   */
  Date getDeletedAt() throws CloudAppException;

}
