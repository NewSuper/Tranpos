package com.newsuper.t.juejinbao.bean;


public class LoginStateEvent {
    private boolean isLogin = false;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public LoginStateEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
