package com.github.commons.message;

/**
 * Created by Yang Tengfei on 4/23/15.
 */
public class DefaultMessageResponse implements IMessageResponse {

    public static final DefaultMessageResponse SUCCESS = new DefaultMessageResponse(0, "success");

    private final int errorCode;

    private final String errorMessage;

    public DefaultMessageResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
