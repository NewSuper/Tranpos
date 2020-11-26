package com.newsuper.t.juejinbao.ui.my.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.qq.e.comm.util.StringUtil;

import java.text.DecimalFormat;


public class FragmentMyConfigDialog extends Dialog {
    private Context context;
    private int type;
    private OnDialogClickListener dialogClickListener;
    private LinearLayout llMyTitle, llCancel;
    private ImageView ivClose, ivGift;
    private TextView tvClose, tvMoney, tvMoneyDiscount, tvGiftHave, tvDetail, isNeed;
    private RelativeLayout rlTicket;
    private String url, title, money, moneyDiscount, peopleHave;
    private EditText editText;

    public boolean cancelAble = true;

    public void setCancelAble(boolean cancelAble) {
        this.cancelAble = cancelAble;
        if (llCancel != null) {
            llCancel.setVisibility(cancelAble ? View.VISIBLE : View.GONE);
            if (cancelAble) {
                isNeed.setText("(选填)");
            } else {
                isNeed.setText("(必填)");
            }

        }
    }

    public FragmentMyConfigDialog(@NonNull Context context, int type) {
        super(context);
        this.context = context;
        this.type = type;
        initView();
    }

    public FragmentMyConfigDialog(@NonNull Context context, int type, String url) {
        super(context);
        this.context = context;
        this.type = type;
        this.url = url;
        initView();
    }

    public FragmentMyConfigDialog(@NonNull Context context, int type, String url, String title, String money, String moneyDiscount, String peopleHave) {
        super(context);
        this.context = context;
        this.type = type;
        this.url = url;
        this.title = title;
        this.money = money;
        this.moneyDiscount = moneyDiscount;
        this.peopleHave = peopleHave;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_config_fragment_my, null);
        setContentView(view);
        Window dialogWindow = getWindow();
        initWindow(dialogWindow);
        switch (type) {
            case 1:
                llMyTitle = view.findViewById(R.id.dialog_config_my_title_about);
                ivClose = view.findViewById(R.id.dialog_config_my_title_close);
                tvClose = view.findViewById(R.id.dialog_config_my_title_know);
                llMyTitle.setVisibility(View.VISIBLE);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                break;
            case 2:

                rlTicket = view.findViewById(R.id.dialog_when_the_ticket);
                ivClose = view.findViewById(R.id.dialog_when_the_ticket_close);
                tvClose = view.findViewById(R.id.tv_close);
                llMyTitle = view.findViewById(R.id.dialog_when_the_ticket_content);
                llMyTitle.setBackgroundResource(R.drawable.shap_bg_white_round_8);
                rlTicket.setVisibility(View.VISIBLE);
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                break;
            case 3:
                rlTicket = view.findViewById(R.id.dialog_config_my_level);
                rlTicket.setVisibility(View.VISIBLE);
                ivClose = view.findViewById(R.id.dialog_config_my_level_bg);
                tvClose = view.findViewById(R.id.dialog_config_my_level_content);
                tvClose.setText("达到了" + url + "级!");
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                break;
            case 4:
                //加一根横线
                rlTicket = view.findViewById(R.id.dialog_config_my_goods);
                rlTicket.setVisibility(View.VISIBLE);
                ivClose = view.findViewById(R.id.dialog_config_my_goods_close);
                tvClose = view.findViewById(R.id.dialog_config_my_goods_gift_name);
                tvGiftHave = view.findViewById(R.id.dialog_config_my_goods_money_have);
                tvMoneyDiscount = view.findViewById(R.id.dialog_config_my_goods_money_lin);
                tvMoney = view.findViewById(R.id.dialog_config_my_goods_money);
                tvDetail = view.findViewById(R.id.dialog_config_my_goods_detail);
                ivGift = view.findViewById(R.id.dialog_config_my_goods_gift);
                tvClose.setText(title);
                tvMoney.setText("0.00");
                if (!StringUtil.isEmpty(moneyDiscount) && Float.valueOf(moneyDiscount) >= 10000) {
                    DecimalFormat df = new DecimalFormat("#.00");
                    tvMoneyDiscount.setText("¥" + df.format(Float.valueOf(moneyDiscount) / 10000) + "万元起");
                } else {
                    tvMoneyDiscount.setText("¥" + moneyDiscount + "元起");
                }
                tvMoneyDiscount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                //  tvGiftHave.setText("已有" + peopleHave + "人免费赚得");

                Glide.with(context).load(url).into(ivGift);
                tvDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_COMMON + Constant.MY_DIALOG_DETAIL);
                        BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_REWARD_LIST);
                        dismiss();
                    }
                });
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                break;
            //输入邀请码
            case 5:

                rlTicket = view.findViewById(R.id.dialog_config_edite_code);
                rlTicket.setVisibility(View.VISIBLE);
                tvGiftHave = view.findViewById(R.id.dialog_config_edite_code_edit_commit);
                editText = view.findViewById(R.id.dialog_config_edite_code_edit);
                ivClose = view.findViewById(R.id.dialog_config_edite_code_close);
                tvDetail = view.findViewById(R.id.dialog_config_edite_code_edit_commit_cancle);
                llCancel = view.findViewById(R.id.ll_cancel);
                isNeed = view.findViewById(R.id.dialog_config_edite_code_edit_choose);

                if (cancelAble) {
                    llCancel.setVisibility(View.VISIBLE);
                    isNeed.setText("(选填)");
                } else {
                    llCancel.setVisibility(View.GONE);
                    isNeed.setText("(必填)");
                }

                tvGiftHave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onOKClick(editText.getText().toString().trim());

                    }
                });
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onOKClick(editText.getText().toString().trim());

                    }
                });
                tvDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onCancelClick();
                    }
                });
                break;
            //一键登录
            case 6:
                rlTicket = view.findViewById(R.id.dialog_config_edite_login);
                rlTicket.setVisibility(View.VISIBLE);
                ivClose = view.findViewById(R.id.dialog_config_edite_login_close);
                tvDetail = view.findViewById(R.id.dialog_config_edite_login_phone);
                tvDetail.setText(url);
                tvGiftHave = view.findViewById(R.id.dialog_config_edite_login_to_psw);
                tvClose = view.findViewById(R.id.dialog_config_edite_login_this);
                tvGiftHave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onOKClick("");
                    }
                });
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onCancelClick();
                    }
                });

                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                break;
            //退出登录
            case 7:
                llMyTitle = view.findViewById(R.id.dialog_config_longin_out);
                llMyTitle.setVisibility(View.VISIBLE);
                tvClose = view.findViewById(R.id.dialog_config_longin_out_cancle);
                tvGiftHave = view.findViewById(R.id.dialog_config_longin_out_ok);
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                tvGiftHave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onOKClick("");
                    }
                });
                break;
            case 8:
                rlTicket = view.findViewById(R.id.dialog_when_the_ticket);
                ivClose = view.findViewById(R.id.dialog_when_the_ticket_close);
                tvClose = view.findViewById(R.id.tv_close);
                llMyTitle = view.findViewById(R.id.dialog_when_the_ticket_content);
                llMyTitle.setBackgroundResource(R.drawable.shap_bg_white_round_8);
                rlTicket.setVisibility(View.VISIBLE);
                tvMoney = view.findViewById(R.id.dialog_when_the_ticket_content_title);
                tvMoneyDiscount = view.findViewById(R.id.dialog_when_the_ticket_content_content);
                tvMoney.setText("昨天晚上有金币余额，\n今天没了，怎么回事？");
                tvMoney.setTextSize(19);
                tvMoneyDiscount.setText("每天凌晨，系统将自动把前一天的金币兑换成掘金宝原始股。具体兑换比例请参考钱包页面");
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                break;
            case 9:
                rlTicket = view.findViewById(R.id.dialog_when_the_ticket);
                ivClose = view.findViewById(R.id.dialog_when_the_ticket_close);
                tvClose = view.findViewById(R.id.tv_close);
                llMyTitle = view.findViewById(R.id.dialog_when_the_ticket_content);
                llMyTitle.setBackgroundResource(R.drawable.shap_bg_white_round_8);
                rlTicket.setVisibility(View.VISIBLE);
                tvMoney = view.findViewById(R.id.dialog_when_the_ticket_content_title);
                tvMoneyDiscount = view.findViewById(R.id.dialog_when_the_ticket_content_content);
                tvMoney.setText("我的阅读时间怎么不准确?");
                //tvMoney.setTextSize(19);
                tvMoneyDiscount.setText("资讯、短视频（不包含小视频）内的转圈计时系统转满一圈时才作为有效的阅读时间");
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                break;
            case 10:
                rlTicket = view.findViewById(R.id.dialog_when_the_ticket);
                ivClose = view.findViewById(R.id.dialog_when_the_ticket_close);
                tvClose = view.findViewById(R.id.tv_close);
                llMyTitle = view.findViewById(R.id.dialog_when_the_ticket_content);
                llMyTitle.setBackgroundResource(R.drawable.shap_bg_white_round_8);
                rlTicket.setVisibility(View.VISIBLE);
                tvMoney = view.findViewById(R.id.dialog_when_the_ticket_content_title);
                tvMoneyDiscount = view.findViewById(R.id.dialog_when_the_ticket_content_content);
                tvMoney.setText("签到奖励");
                //tvMoney.setTextSize(19);
                tvMoneyDiscount.setText("每日签到是给新老用户的福利！7天为一个周期，每天签到即可获得相应金币奖励，如中断签到或一周期签到结束后将从第一天开始重新计算");
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                break;
        }
    }

    /**
     * 添加黑色半透明背景
     */
    private void initWindow(Window dialogWindow) {

        dialogWindow.setBackgroundDrawable(new ColorDrawable(0));//设置window背景
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕尺寸
        lp.width = d.widthPixels; //宽度为屏幕80%
        lp.gravity = Gravity.CENTER;  //中央居中
        dialogWindow.setAttributes(lp);
    }

    public void setOnDialogClickListener(OnDialogClickListener clickListener) {
        dialogClickListener = clickListener;
    }

    /**
     * 添加按钮点击事件
     */
    public interface OnDialogClickListener {
        void onOKClick(String code);

        void onCancelClick();
    }
}
