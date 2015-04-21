package com.github.commons.session;

import com.github.commons.session.store.CookieSessionStore;
import com.github.commons.session.store.SessionStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session 管理
 */
public class SessionStoreHolder {

    private SessionStore store;
    private int          sessionInvalidTime;

    public SessionStoreHolder(HttpServletRequest request, HttpServletResponse response, int sessionInvalidTime){
        this.sessionInvalidTime = sessionInvalidTime;

        store = new CookieSessionStore(request, response);
    }

    public SessionStoreHolder(int sessionInvalidTime, SessionStore store){
        this.sessionInvalidTime = sessionInvalidTime;
        this.store = store;
    }

    public int getSessionInvalidTime() {
        return sessionInvalidTime;
    }

    public void setSessionInvalidTime(int sessionInvalidTime) {
        this.sessionInvalidTime = sessionInvalidTime;
    }

    public SessionStore getStore() {
        return store;
    }
}
