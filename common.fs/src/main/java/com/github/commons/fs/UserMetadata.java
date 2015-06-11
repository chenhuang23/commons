package com.github.commons.fs;

public class UserMetadata {

    /*
     * Standard HTTP Headers
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    private String             key;
    private String             value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
