package com.newsuper.t.juejinbao.utils.jsbridge;



public interface WebViewJavascriptBridge {
	
	void send(String data);
	void send(String data, CallBackFunction responseCallback);
	
	

}
