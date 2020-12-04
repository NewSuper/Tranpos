package com.newsuper.t.sale.ui.scan.mvp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.sale.base.mvp.BasePresenter;
import com.newsuper.t.sale.db.manger.LineSalesSettingDbManger;
import com.newsuper.t.sale.db.manger.MemberLevelDbManger;
import com.newsuper.t.sale.db.manger.MemberPointRuleDbManger;
import com.newsuper.t.sale.db.manger.MultipleQueryProductDbManger;
import com.newsuper.t.sale.db.manger.OrderObjectDbManger;
import com.newsuper.t.sale.db.manger.ProductDbManger;
import com.newsuper.t.sale.entity.LineSalesSetting;
import com.newsuper.t.sale.entity.Member;
import com.newsuper.t.sale.entity.MemberLevel;
import com.newsuper.t.sale.entity.MemberPointRule;
import com.newsuper.t.sale.entity.MultipleQueryProduct;
import com.newsuper.t.sale.entity.OrderObject;
import com.newsuper.t.sale.entity.Product;
import com.newsuper.t.sale.entity.state.PostWayFlag;
import com.newsuper.t.sale.ui.scan.manger.OrderItemManger;
import com.newsuper.t.sale.ui.scan.manger.OrderManger;
import com.newsuper.t.sale.utils.DateUtil;
import com.newsuper.t.sale.utils.StringUtils;
import com.newsuper.t.sale.utils.UiUtils;
import com.newsuper.t.sale.view.GlideCircleTransform;
import com.transpos.sale.thread.ThreadDispatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PromptScanPresenter extends BasePresenter<PromptScanContract.Model,PromptScanContract.View> implements PromptScanContract.Presenter{
    @Override
    protected PromptScanContract.Model createModule() {
        return new PromptScanModel();
    }

    @Override
    public void startAnimationAndLoad(View layout_product,MultipleQueryProduct product) {
        layout_product.setVisibility(View.VISIBLE);
        ImageView iv_product = layout_product.findViewById(R.id.iv_product);
        TextView tv_product_name = layout_product.findViewById(R.id.tv_product_name);
        TextView tv_producrt_price = layout_product.findViewById(R.id.tv_producrt_price);
        tv_product_name.setText(product.getName());
        tv_producrt_price.setText("￥"+product.getSalePrice());
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_product_big_default)
                .error(R.mipmap.ic_product_big_default);
        Glide.with(getContext()).load(product.getStorageAddress()).apply(options).into(iv_product);
        final int left = layout_product.getLeft();
        final int top = layout_product.getTop()- UiUtils.dp2px(45,getContext());

        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(600);
        layout_product.startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                float [] x= {0f,-left};
                float [] y= {0f,-top};
                AnimatorSet animatorSet = new AnimatorSet();
                AnimatorSet animatorSet2 = new AnimatorSet();
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(layout_product,"translationX", x);
                objectAnimatorX.setDuration(300);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(layout_product,"translationY", y);
                objectAnimatorY.setDuration(300);
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(layout_product, "scaleX", 1f, 0f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(layout_product, "scaleY", 1f, 0f);
                scaleX.setDuration(100);
                scaleY.setDuration(100);


                animatorSet.play(objectAnimatorX).with(objectAnimatorY);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        animatorSet2.play(scaleX).with(scaleY);
                        animatorSet2.start();
                        animatorSet2.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                layout_product.setVisibility(View.INVISIBLE);
                                layout_product.setTranslationX(0);
                                layout_product.setTranslationY(0);
                                layout_product.setScaleX(1);
                                layout_product.setScaleY(1);
                                getView().animationEnd(product);
                            }
                        });
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void queryProductByBarcode(String barCode) {
        ThreadDispatcher.getDispatcher().post(()->{
            boolean isEmpty = false;
            //根据条码
            List<MultipleQueryProduct> multipleQueryProduct = MultipleQueryProductDbManger.INSTANCE.queryOneProductByCode(barCode);
            //条码未查询到，再自编码查询
            if(multipleQueryProduct == null || multipleQueryProduct.isEmpty()){
                multipleQueryProduct = MultipleQueryProductDbManger.INSTANCE.queryOneProductBysubCode(barCode);
            }
            //条码和自编码未查询到 ，再去查询allcode
            if(multipleQueryProduct == null || multipleQueryProduct.isEmpty()){
                multipleQueryProduct = MultipleQueryProductDbManger.INSTANCE.queryOneProductByAllcode(barCode);
                if(multipleQueryProduct == null || multipleQueryProduct.isEmpty()){
                    isEmpty = true;
                } else {
                    Iterator<MultipleQueryProduct> iterator = multipleQueryProduct.iterator();
                    while (iterator.hasNext()){
                        MultipleQueryProduct product = iterator.next();
                        String allCode = product.getAllCode();
                        boolean isExits = false;
                        if(!TextUtils.isEmpty(allCode)){
                            String[] codeArr = allCode.split(",");
                            for (String s : codeArr) {
                                if(s.equals(barCode)){
                                    isExits = true;
                                }
                            }

                        }
                        if(!isExits){
                            iterator.remove();
                        }
                    }
                }
            }
            if(multipleQueryProduct == null || multipleQueryProduct.isEmpty()){
                isEmpty = true;
            }
            post(isEmpty,multipleQueryProduct);
        });
    }

    private void post(boolean isEmpty, List<MultipleQueryProduct> multipleQueryProduct) {
        ThreadDispatcher.getDispatcher().postOnMain(()->{
            if(isEmpty){
                UiUtils.showToastShort("无法识别商品码");
            } else {
                if(isViewAttached()){
                    getView().queryProductSuccess(multipleQueryProduct);
                }
            }
        });
    }

    @Override
    public void createOrder() {
        OrderManger.INSTANCE.initalize();
    }

    @Override
    public void addOrderItem(MultipleQueryProduct product, OrderItemManger.AddItemState state, int position, Member member, OrderItemManger.JoinTypeEnmu joinType) {
        OrderItemManger.INSTANCE.createItems(product,state,position,member,joinType);
    }

    public void setOrderValue(List<MultipleQueryProduct> list, float discountAmount, float totalPrice, Member mMember){
        OrderObject orderBean = OrderManger.INSTANCE.getOrderBean();
        if(orderBean != null){
            orderBean.setItemCount(list.size());
            orderBean.setPayCount(1);
            orderBean.setTotalQuantity(this.getTotalAmount(list));
            orderBean.setAmount(this.getOriginTotalPrice(list));
            orderBean.setDiscountAmount(discountAmount);
            orderBean.setReceivableAmount(totalPrice);
            orderBean.setPaidAmount(totalPrice);
            orderBean.setReceivedAmount(totalPrice);
            orderBean.setMalingAmount(0);
            orderBean.setChangeAmount(0);
            orderBean.setInvoicedAmount(0);
            orderBean.setOverAmount(0);
            orderBean.setPrintTimes(0);
            orderBean.setPrintStatus(0);
            orderBean.setPostWay(PostWayFlag.ZITI_STATE);
            orderBean.setDiscountRate(orderBean.getAmount() == 0 ? 0 : discountAmount / orderBean.getAmount());
            orderBean.setIsMember(mMember != null ? 1 : 0);
            orderBean.setMemberNo( mMember != null ? mMember.getNo() : "");
            orderBean.setMemberName(mMember != null ? mMember.getName() : "");
            orderBean.setMemberMobileNo(mMember != null ? mMember.getMobile() : "");
            orderBean.setPrePoint(mMember != null ? mMember.getTotalPoint() : 0);
            orderBean.setAddPoint(calcPoint(mMember,totalPrice));
            orderBean.setAftPoint(orderBean.getPrePoint()+orderBean.getAddPoint());
//            orderBean.setAftAmount();
            orderBean.setMemberId(mMember != null ? mMember.getId() : "");
            orderBean.setReceivableRemoveCouponAmount(orderBean.getReceivableAmount());
            orderBean.setModifyDate(DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT));
            orderBean.setExt2(mMember != null ? StringUtils.Companion.formatPrice((float) mMember.getTotalAmount()) : "");//设置会员余额
            OrderObjectDbManger.INSTANCE.update(orderBean);
        }

    }

    /**
     * 计算积分
     * @param member
     * @param totalPrice
     * @return
     */
    private float calcPoint(Member member, float totalPrice){
        int p = 0;
        if(member != null){
            String memberLevelId = null;
            List<MemberPointRule> pointRules = MemberPointRuleDbManger.getInstance().loadAll();
            List<MemberLevel> memberLevels = MemberLevelDbManger.getInstance().loadAll();
            for (MemberLevel level : memberLevels) {
                if(level.getNo().equals(member.getMemberLevelNo())){
                    memberLevelId = level.getId();
                    break;
                }
            }
            MemberPointRule memberPointRule = null;
            if(pointRules != null && pointRules.size() > 0){
                for (MemberPointRule rule : pointRules) {
                    //会员等级id未找到  走通用（等级id==1）
                    if(rule.getLevelId().equals("1")){
                        memberPointRule = rule;
                    }
                    if(rule.getLevelId().equals(memberLevelId)){
                        memberPointRule = rule;
                        break;
                    }
                }
            }
            if(memberPointRule != null){
                if(memberPointRule.getPointType() == 1){
                    p = (int) (totalPrice * memberPointRule.getRate());
                }
            }
        }

        return p * 1.0f;
    }


    public int getTotalAmount(List<MultipleQueryProduct> datas){
        int amount = 0;
        for (MultipleQueryProduct data : datas) {
            amount += data.getmAmount();
        }
        return amount;
    }

    /**
     * 获取总原价
     * @param datas
     * @return
     */
    public float getOriginTotalPrice(List<MultipleQueryProduct> datas){
        float p = 0f;
        for (MultipleQueryProduct data : datas) {
            p += data.getmAmount() * Float.parseFloat(data.getSalePrice());
        }
        return StringUtils.Companion.formatPriceFloat(p);
    }

    public float[] getVipPrice(Member member,List<MultipleQueryProduct> datas){
        float price = 0f;
        float sPrice = 0f;
        float discount = 1f;
        float reduction = 0f;
        List<LineSalesSetting> settings = new ArrayList<>();

        if(member == null){
            for (MultipleQueryProduct data : datas) {
                if(Float.parseFloat(data.getSalePrice() ) < Float.parseFloat(data.getMinPrice())){
                    sPrice += (Float.parseFloat(data.getMinPrice()) * data.getmAmount());
                } else{
                    sPrice += (Float.parseFloat(data.getSalePrice()) * data.getmAmount());
                }
            }
        } else {
            MemberLevel level = member.getMemberLevel();
            if(level != null){

                if(level.getDiscountWay() == 2){
                    discount = (float) level.getDiscount() / 100;
                } else if(level.getDiscountWay() == 3){
                    discount = (float) level.getDiscount() / 100;
                }
            }
            for (MultipleQueryProduct data : datas) {
                price = 0f;
                if(level != null) {
                    switch (level.getDiscountWay()) {
                        case 0:
                            break;
                        //按会员价 1 5 6 7 8
                        case 1:
                            price = Float.parseFloat(data.getVipPrice());
                            break;
                        case 5:
                            price = Float.parseFloat(data.getVipPrice2());
                            break;
                        case 6:
                            price = Float.parseFloat(data.getVipPrice3());
                            break;
                        case 7:
                            price = Float.parseFloat(data.getVipPrice4());
                            break;
                        case 8:
                            price = Float.parseFloat(data.getVipPrice5());
                            break;

                        case 2:
                            //会员价折扣
                            if(settings.isEmpty()){
                                settings = LineSalesSettingDbManger.getInstance().loadAll();
                            }

                            String tag = "0";
                            for (LineSalesSetting setting : settings) {
                                if(setting.getSetKey().equals("allow_vip_discount_for_bandiscount_product")){
                                    tag = setting.getSetValue();
                                }
                            }
                            if(tag.equals("1")){
                                price = Float.parseFloat(data.getVipPrice()) * discount;
                            }
                            break;
                        case 3:
                            //售价打折
                            {
                                if(settings.isEmpty()){
                                    settings = LineSalesSettingDbManger.getInstance().loadAll();
                                }

                                String allow = "0";
                                for (LineSalesSetting setting : settings) {
                                    if(setting.getSetKey().equals("allow_vip_discount_for_bandiscount_product")){
                                        allow = setting.getSetValue();
                                    }
                                }
                                if(allow.equals("1")){
                                    price = Float.parseFloat(data.getSalePrice()) * discount;
                                }
                            }
                            break;
                        case 4:
                            price = Float.parseFloat(data.getBatchPrice());
                            break;
                    }
                }

                if(price == 0){
                    price = Float.parseFloat(data.getSalePrice());
                }
                //最低价 0.01
                if(price < 0.01){
                    price = 0.01f;
                }
                if(price < Float.parseFloat(data.getMinPrice())){
                    price = Float.parseFloat(data.getMinPrice());
                }
                reduction += (Float.parseFloat(data.getSalePrice()) - price) * data.getmAmount();
                sPrice += price * data.getmAmount();
            }

        }

        return new float[]{StringUtils.Companion.formatPriceFloat(sPrice),StringUtils.Companion.formatPriceFloat(reduction)};
    }
}
