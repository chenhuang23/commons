package com.github.commons.session.store;

import com.github.commons.session.SessionImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieSessionStore implements SessionStore {

    private HttpServletRequest  request;
    private HttpServletResponse response;

    @Override
    public void init() {

    }

    public CookieSessionStore(HttpServletRequest request, HttpServletResponse response){
        this.response = response;
        this.request = request;
    }

    public void put(String sessionId, String key, Object value) {
        String str = null;

        if (value != null) {
            if (value instanceof String) {
                str = String.valueOf(value);
            } else {
                str = value.toString();
            }

            Cookie cookie = new Cookie(key, str);
            cookie.setMaxAge(SessionImpl.COOKIE_ALIVE_TIME);
            cookie.setPath(SessionImpl.COOKIE_PATH_VAL);
            getResponse().addCookie(cookie);
        }
    }

    public Object get(String sessionId, String key) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String[] getAllKeys(String sessionId) {
        List<String> list = new ArrayList<String>();
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                list.add(cookie.getName());
            }
        }
        return (String[]) list.toArray();
    }

    public void clean(String sessionId) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                getResponse().addCookie(cookie);
            }
        }
    }

    public void delete(String sessionId, String key) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cookie.setMaxAge(0);
                    getResponse().addCookie(cookie);
                }
            }
        }
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
