package com.github.commons.session;

import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * session 的封装
 */
public class SessionImpl implements HttpSession {

    private SessionRequestWrapper              request;
    private HttpServletResponse                response;
    private int                                maxInactiveInterval;
    private boolean                            isRequestedSessionIdValid;
    private boolean                            isSessionIdFromURL    = false;
    private boolean                            isSessionIdFromCookie = true;
    private static String                      SESSION_ID_NAME       = "ne_ln_jsession_id";
    private static String                      SESSION_CREATE_TIME   = "ne_ln_jsession_create_time";
    public static String                       COOKIE_PATH_VAL       = "/";
    private SessionIDGenerator                 sidGenerator          = new DefaultSessionIDGenerator();
    public static int                          COOKIE_ALIVE_TIME     = 3600 * 24 * 365 * 100;
    private SessionInternal                    session;
    private ServletContext                     servletContext;
    private SessionStoreHolder                 holder;
    public static ThreadLocal<SessionInternal> localSession          = new ThreadLocal<SessionInternal>();

    public SessionImpl(SessionRequestWrapper request, HttpServletResponse response, ServletContext servletContext,
                       SessionStoreHolder holder){
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
        this.holder = holder;
        init();
    }

    private void init() {
        this.session = restoreSessionState(request);
        synchronized (request) {

            if (StringUtils.isEmpty(session.getSessionId()) && !request.isSessionNew()) {
                request.setSessionNew(true);
                this.session = createNewSession();
                return;
            }
        }

        request.setSessionNew(false);
        session.setLastAccessTime(new Date().getTime());

        validateSession();
    }

    private void validateSession() {
        long createTime = 0;
        try {
            createTime = Long.valueOf(session.getCreationTime());
        } catch (Exception ex) {
            // throw new RuntimeException("parse create time error:", ex);
            createTime = 0L;
        }
        if (new Date().getTime() - createTime > holder.getSessionInvalidTime()) {
            invalidate();
        }
    }

    private SessionInternal createNewSession() {
        SessionInternal session = new SessionInternal();
        String id = sidGenerator.generateId();
        Cookie sid = new Cookie(SESSION_ID_NAME, id);
        sid.setMaxAge(COOKIE_ALIVE_TIME);
        sid.setPath(COOKIE_PATH_VAL);
        String time = String.valueOf(new Date().getTime());
        Cookie createTime = new Cookie(SESSION_CREATE_TIME, time);
        createTime.setMaxAge(COOKIE_ALIVE_TIME);
        createTime.setPath(COOKIE_PATH_VAL);
        response.addCookie(sid);
        response.addCookie(createTime);
        session.setSessionId(id);
        session.setCreationTime(time);
        localSession.set(session);
        return session;
    }

    private SessionInternal restoreSessionState(HttpServletRequest request) {
        SessionInternal session = localSession.get();
        if (session != null) {
            return session;
        }

        session = new SessionInternal();
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SESSION_ID_NAME.equalsIgnoreCase(cookie.getName())) {
                    session.setSessionId(cookie.getValue());
                    continue;
                }
                if (SESSION_CREATE_TIME.equalsIgnoreCase(cookie.getName())) {
                    session.setCreationTime(cookie.getValue());
                    continue;
                }

            }
        }

        return session;
    }

    public boolean isRequestedSessionIdValid() {
        return isRequestedSessionIdValid;
    }

    public void setRequestedSessionIdValid(boolean isRequestedSessionIdValid) {
        this.isRequestedSessionIdValid = isRequestedSessionIdValid;
    }

    public boolean isSessionIdFromURL() {
        return isSessionIdFromURL;
    }

    public void setSessionIdFromURL(boolean isSessionIdFromURL) {
        this.isSessionIdFromURL = isSessionIdFromURL;
    }

    public boolean isSessionIdFromCookie() {
        return isSessionIdFromCookie;
    }

    public void setSessionIdFromCookie(boolean isSessionIdFromCookie) {
        this.isSessionIdFromCookie = isSessionIdFromCookie;
    }

    public long getCreationTime() {
        if (session == null || StringUtils.isEmpty(session.getCreationTime())) {
            return 0;
        }
        return Long.valueOf(session.getCreationTime());
    }

    public String getId() {
        return session == null ? null : session.getSessionId();
    }

    public long getLastAccessedTime() {
        return session == null ? 0 : session.getLastAccessTime();
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    public int getMaxInactiveInterval() {
        return this.maxInactiveInterval;
    }

    @SuppressWarnings("deprecation")
    public javax.servlet.http.HttpSessionContext getSessionContext() {
        throw new IllegalAccessError("this method can't use in our work,is deprecated.");
    }

    public Object getAttribute(String name) {
        return holder.getStore().get(this.getId(), name);
    }

    public Object getValue(String name) {
        return getAttribute(name);
    }

    public Enumeration<String> getAttributeNames() {
        List<String> array = new ArrayList<String>();
        Collections.addAll(array, getValueNames());
        return Collections.enumeration(array);
    }

    public String[] getValueNames() {
        Set<String> set = new HashSet<String>();
        String[] names = holder.getStore().getAllKeys(this.getId());
        if (names != null) {
            for (String name : names) {
                set.add(name);
            }
        }
        return set.toArray(new String[0]);
    }

    public void setAttribute(String name, Object value) {
        holder.getStore().put(this.getId(), name, value);
    }

    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    public void removeAttribute(String name) {
        holder.getStore().delete(this.getId(), name);
    }

    public void removeValue(String name) {
        removeAttribute(name);
    }

    public void invalidate() {
        holder.getStore().clean(this.getId());
        createNewSession();
    }

    public boolean isNew() {
        return request.isSessionNew();
    }

    private static class SessionInternal {

        private String sessionId;
        private String creationTime;
        private long   lastAccessTime;

        public long getLastAccessTime() {
            return lastAccessTime;
        }

        public void setLastAccessTime(long lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

    }

}
