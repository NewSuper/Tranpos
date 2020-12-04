package com.newsuper.t.sale.utils;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

public class ToolScanner {

	private int MESSAGE_DELAY = 500;
	private boolean mCaps;
	private Handler mHandler;
	private StringBuffer mStringBufferResult;
	private Runnable mScanningFishedRunnable;
	private OnScanSuccessListener mOnScanSuccessListener;

	public interface OnScanSuccessListener {
		void onScanSuccess(String barcode);
	}

	private void performScanSuccess() {
		String barcode = mStringBufferResult.toString();
		if (mOnScanSuccessListener != null) {
			mOnScanSuccessListener.onScanSuccess(barcode);
		}
		mStringBufferResult.setLength(0);
	}

	public ToolScanner(OnScanSuccessListener listener) {
		mOnScanSuccessListener = listener;
		mStringBufferResult = new StringBuffer();
		mHandler = new Handler();
		mScanningFishedRunnable = new Runnable() {

			@Override
			public void run() {
				performScanSuccess();
			}
		};
	}

	public void analysisKeyEvent(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(event.getAction() == KeyEvent.KEYCODE_BACK){
			return;
		}
		checkLetterStatus(event);
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			char aChar = getInputCode(event);
			if (aChar != 0) {
				mStringBufferResult.append(aChar);
			}
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				// 若为回车键，直接返回
				mHandler.removeCallbacks(mScanningFishedRunnable);
				mHandler.postDelayed(mScanningFishedRunnable,MESSAGE_DELAY);
			}else {
				// 延迟post，若500ms内，有其他事件
				mHandler.removeCallbacks(mScanningFishedRunnable);
				mHandler.postDelayed(mScanningFishedRunnable, MESSAGE_DELAY);
			}


		}
	}

	private void checkLetterStatus(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if (keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				// 按着shift键，表示大写
				mCaps = true;
			} else {
				// 松开shift键，表示小写
				mCaps = false;
			}
		}
	}

	private char getInputCode(KeyEvent event) {
		int keyCode = event.getKeyCode();
		char aChar;
		if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
			//
			aChar = (char) ((mCaps ? 'A' : 'a') + keyCode - KeyEvent.KEYCODE_A);
		} else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
			// 数字
			aChar = (char) ('0' + keyCode - KeyEvent.KEYCODE_0);
		} else {
			// 其他符号
			switch (keyCode) {
			case KeyEvent.KEYCODE_MINUS:
				aChar = mCaps ? '_' : '-';
				break;
			case KeyEvent.KEYCODE_BACKSLASH:
				aChar = mCaps ? '|' : '\\';
				break;
			case KeyEvent.KEYCODE_LEFT_BRACKET:
				aChar = mCaps ? '{' : '[';
				break;
			case KeyEvent.KEYCODE_RIGHT_BRACKET:
				aChar = mCaps ? '}' : ']';
				break;
			case KeyEvent.KEYCODE_COMMA:
				aChar = mCaps ? '<' : ',';
				break;
			case KeyEvent.KEYCODE_PERIOD:
				aChar = mCaps ? '>' : '.';
				break;
			case KeyEvent.KEYCODE_SLASH:
				aChar = mCaps ? '?' : '/';
				break;
			case KeyEvent.KEYCODE_SEMICOLON:
				aChar = mCaps ? ':' : ';';
				break;
			case KeyEvent.KEYCODE_APOSTROPHE:
				aChar = mCaps ? '"' : '\'';
				break;
			default:
				aChar = 0;
				break;
			}

		}

		return aChar;
	}

}
