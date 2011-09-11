package com.cloudapp.api;

public class CloudAppException extends Exception {

  private static final long serialVersionUID = -2274919591220867229L;

  private int code;

  /**
   * @param code
   *          The HTTP Status code that was returned from CloudApi.
   * @param message
   *          A message explaining what went wrong.
   * @param cause
   *          an optional cause for this exception.
   */
  public CloudAppException(int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  /**
   * @return An HTTP status code that explains what went wrong. This doesn't always have
   *         to be coming from the CloudApi servers.
   */
  public int getCode() {
    return code;
  }
}
