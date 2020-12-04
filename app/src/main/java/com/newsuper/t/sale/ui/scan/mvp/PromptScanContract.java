package com.newsuper.t.sale.ui.scan.mvp;

import com.newsuper.t.sale.base.mvp.IBaseModel;
import com.newsuper.t.sale.base.mvp.IBaseView;
import com.newsuper.t.sale.entity.Member;
import com.newsuper.t.sale.entity.MultipleQueryProduct;
import com.newsuper.t.sale.entity.Product;
import com.newsuper.t.sale.ui.scan.manger.OrderItemManger;

import java.util.List;

public interface PromptScanContract {

    interface Model extends IBaseModel{

    }


    interface View extends IBaseView{
        void queryProductSuccess(List<MultipleQueryProduct> product);
        void animationEnd(MultipleQueryProduct product);
    }


    interface Presenter {
        void startAnimationAndLoad(android.view.View view, MultipleQueryProduct product);
        void queryProductByBarcode(String barCode);
        void createOrder();
        void addOrderItem(MultipleQueryProduct product, OrderItemManger.AddItemState state,
                          int position, Member member, OrderItemManger.JoinTypeEnmu joinType);
    }
}
