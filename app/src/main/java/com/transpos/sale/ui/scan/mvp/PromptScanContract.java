package com.transpos.sale.ui.scan.mvp;

import com.transpos.sale.base.mvp.IBaseModel;
import com.transpos.sale.base.mvp.IBaseView;
import com.transpos.sale.entity.Member;
import com.transpos.sale.entity.MultipleQueryProduct;
import com.transpos.sale.entity.Product;
import com.transpos.sale.ui.scan.manger.OrderItemManger;

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
