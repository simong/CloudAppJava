package com.cloudapp.api.model;

import java.util.Date;

import com.cloudapp.api.CloudAppException;

public interface CloudAppAccount {

  enum DefaultSecurity {
    PRIVATE, PUBLIC
  }

  /**
   * @return Your ID.
   */
  public long getId() throws CloudAppException;

  /**
   * @return Your email address.
   */
  public String getEmail() throws CloudAppException;

  /**
   * @return Your domain.
   */
  public String getDomain() throws CloudAppException;

  /**
   * @return Your domain homepage.
   */
  public String getDomainHomePage() throws CloudAppException;

  /**
   * @return Your default security for new files.
   */
  public DefaultSecurity getDefaultSecurity() throws CloudAppException;

  /**
   * @return
   */
  public boolean isSubscribed() throws CloudAppException;

  public boolean isAlpha() throws CloudAppException;

  /**
   * @return the date you signed up
   */
  public Date getCreatedAt() throws CloudAppException;

  /**
   * @return The date you last updated your account.
   */
  public Date getUpdatedAt() throws CloudAppException;

  /**
   * @return The date you activated your account.
   */
  public Date ActivatedAt() throws CloudAppException;

}
