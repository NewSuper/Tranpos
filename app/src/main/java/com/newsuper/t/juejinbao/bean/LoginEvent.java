package com.newsuper.t.juejinbao.bean;

import java.util.HashMap;


public class LoginEvent {

    //登录后跳转哪个界面
    public int intentEvent;
    //跳转界面需要的参数
    public HashMap<String , String> params;

//    public LoginEvent() {
//
//    }

    public LoginEvent(int intentEvent , HashMap<String , String> params){
        this.intentEvent = intentEvent;
        this.params = params;
    }

}
