package com.newsuper.t.juejinbao.utils.network;

import rx.functions.Func1;

public class HttpResultFunc<T> implements Func1<T,T> {
    @Override
    public T call(T t) {
//        if(t!=null&&t instanceof BaseResultBean){
//            int code=((BaseResultBean) t).getCode();
//            switch (code){
//                case 1001:
//                    ((BaseResultBean) t).setMessage("资源不存在");
//                    break;
//                case 1002:
//                    ((BaseResultBean) t).setMessage("参数不全");
//                    break;
//                //这里不再全部写出，实际根据情况写自己的业务处理
//            }
//        }
        return t;
    }
}
