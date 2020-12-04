package com.newsuper.t.markert.utils.base64;

public class DecoderException extends IllegalStateException {

	private static final long serialVersionUID = -7689594987006703988L;

	private Throwable cause;

	DecoderException(String msg, Throwable cause) {
		super(msg);

		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}
}