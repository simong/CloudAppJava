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

package com.cloudapp.impl;

import java.io.*;

import com.cloudapp.api.model.CloudAppProgressListener;
import org.apache.http.entity.mime.content.InputStreamBody;

public class CloudAppInputStream extends InputStreamBody {

  private final long length;
  private final CloudAppProgressListener listener;

  public CloudAppInputStream(InputStream in, String filename, long length, CloudAppProgressListener listener) {
    super(in, filename);
    this.length = length;
    this.listener = (listener == null) ? CloudAppProgressListener.NO_OP : listener;
  }

  protected CloudAppInputStream(File file, CloudAppProgressListener listener) throws FileNotFoundException {
    this(new FileInputStream(file), file.getName(), file.length(), listener);
  }

  @Override
  public void writeTo(OutputStream out) throws IOException {
    super.writeTo( new ListeningOutputStream(out) );
  }

  @Override
  public long getContentLength() {
    return length;
  }

  private class ListeningOutputStream extends FilterOutputStream {

    private long bytesWritten;

    public ListeningOutputStream(OutputStream out) {
      super(out);
      bytesWritten = 0L;
    }

    @Override
    public void write(int b) throws IOException {
      out.write(b);
      listener.transferred(++bytesWritten, length);
    }

    @Override
    public void write(byte[] b) throws IOException {
      out.write(b);
      bytesWritten += b.length;
      listener.transferred(bytesWritten, length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      out.write(b, off, len);
      bytesWritten += (len - off);
      listener.transferred(bytesWritten, length);
    }
  }

}
