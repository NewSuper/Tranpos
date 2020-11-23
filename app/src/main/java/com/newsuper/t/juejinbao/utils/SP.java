package com.newsuper.t.juejinbao.utils;


import android.content.Context;

public class SP {

    private static ShareStorage accountStorage;
    private static ShareStorage userStorage;
    private static ShareStorage publicStorage;

    public static final String server = "server";
    private static final String accounts = "accounts";
    public static final String user = "user";
    private static final String publicSP = "publicSP";

    public static ShareStorage getAccount(Context activity) {
        if (accountStorage == null) {
            accountStorage = new ShareStorage(activity, accounts);
        }
        return accountStorage;
    }

}
