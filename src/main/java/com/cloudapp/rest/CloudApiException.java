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

public class CloudApiException extends Exception {

  private static final long serialVersionUID = -987558445571987498L;
  private int code;

  /**
   * @param code
   *          The HTTP Status code that was returned from CloudApi.
   * @param message
   *          A message explaining what went wrong.
   * @param cause
   *          an optional cause for this exception.
   */
  public CloudApiException(int code, String message, Throwable cause) {
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
