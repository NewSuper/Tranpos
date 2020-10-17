package com.transpos.sale.utils.base64;

public class EncoderException extends IllegalStateException {
	
	private static final long serialVersionUID = 9188652731232445868L;
	
	private Throwable cause;

	EncoderException(String msg, Throwable cause) {
		super(msg);

		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}
}