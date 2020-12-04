package com.newsuper.t.markert.ui.login.mvp;

import com.trans.network.HttpManger;
import com.trans.network.callback.StringCallback;
import com.trans.network.utils.GsonHelper;
import com.newsuper.t.markert.base.BaseApp;
import com.newsuper.t.markert.entity.RegistrationCode;
import com.newsuper.t.markert.base.HttpUrl;
import com.newsuper.t.markert.utils.DES;
import com.newsuper.t.markert.utils.KeyConstrant;
import com.newsuper.t.markert.utils.OpenApiUtils;
import com.transpos.tools.tputils.TPUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginModel implements LoginContract.Model{

    @Override
    public void login(String user_name, String pwd, StringCallback callback) {
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "worker.login");
        RegistrationCode auth = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", auth.getStoreId());
        reqData.put("posNo", auth.getPosNo());
        reqData.put("workerNo", user_name);
        reqData.put("passwd", DES.encrypt(pwd));
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(auth, parameters));

        HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL,parameters,callback);
    }
}
