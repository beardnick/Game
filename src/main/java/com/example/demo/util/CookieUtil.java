package com.example.demo.util;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static String get(HttpServletRequest request,
                             String name){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        for (Cookie c: cookies
             ) {
           if(c.getName().equals(name)){
               return c.getValue();
           }
        }
        return null;
    }

    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/login");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

}
