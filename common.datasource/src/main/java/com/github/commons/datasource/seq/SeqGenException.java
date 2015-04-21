package com.github.commons.datasource.seq;

/**
 * 统一生成器异常
 * 
 * @author xiaofeng.zhouxf
 *
 */
public class SeqGenException extends RuntimeException {

	private static final long serialVersionUID = -1619103647988492198L;

	public SeqGenException() {
		super();
	}

	public SeqGenException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SeqGenException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeqGenException(String message) {
		super(message);
	}

	public SeqGenException(Throwable cause) {
		super(cause);
	}

}
