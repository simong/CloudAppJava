CloudApp REST APIs Java Wrapper
===============================
These set of Java classes allow you to easily talk to the CloudApp REST Apis.

Usage Example
-------------

    CloudApi api = new CloudApiImpl("Your email here", "Your password here");

    // Add a new bookmark
    JSONObject json = api.createBookmark('Simon Gaeremynck's portfolio', 'gaeremynck.com');

    // Add file
    JSONObject json = api.uploadFile(new File("/path/to/file"));

    // Get the first 10 items, regardless of category who aren't in the trash.
    JSONArray array = api.getItems(1, 10, null, false);

    // Get a specific item (http://cl.ly/bD5)
    JSONObject json = api.getItem('bD5');
    
    // Delete item (by 'href' value)
    api.deleteItem("http://my.cl.ly/items/1058986");
    

Requirements
------------
Java
Maven (only for building)


Building
--------
A jar can be compiled trough maven by running the following command.
`mvn clean install -Dmaven.test.skip`

However, this will skip the unit tests.
If you want to have the unit tests, you will need to fill 
in your email and password in the CloudApiTestCase.java file.
`mvn clean install`


License
-------

 Copyright (c) 2010 <gaeremyncks@gmail.com>

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
