package com.cloudapp.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;

import com.cloudapp.api.CloudApp;

public class BaseTestCase {

  private static String mail;
  private static String password;
  protected CloudApp api;

  @BeforeClass
  public static void beforeClass() throws IOException {
    InputStream in = BaseTestCase.class
        .getResourceAsStream("/credentials.properties");
    Properties props = new Properties();
    props.load(in);

    BaseTestCase.mail = props.getProperty("cred_mail", "alice@acme.com");
    BaseTestCase.password = props.getProperty("cred_password", "supersecretpassword");
  }

  @Before
  public void setUp() {
    api = new CloudAppImpl(mail, password);
  }
}
