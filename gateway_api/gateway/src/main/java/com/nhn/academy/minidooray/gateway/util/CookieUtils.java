package com.nhn.academy.minidooray.gateway.util;

import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {

  private CookieUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String getCookieValue(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length == 0) {
      return null;
    }
    return Arrays.stream(cookies)
        .filter(c -> c.getName().equals(cookieName))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);
  }

}
