package com.newsuper.t.juejinbao.ui.my.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juejinchain.android.R;
import com.juejinchain.android.utils.DataCleanManager;
import com.qq.e.comm.util.StringUtil;

import io.paperdb.Paper;

public class ConfigDialog extends Dialog {
    private Context context;
    private int type;
    private DialogClickListener dialogClickListener;
    private LinearLayout llMyTitle;
    private TextView tvClose, tvGiftHave, tvContent, tvContinue;
    private String content;
    private int cancleType;

    public ConfigDialog(Context context, int type) {
        super(context);
        this.context = context;
        this.type = type;
        try{
            initView();
        }catch (Exception e){
            e.printStackTrace();
            dismiss();
        }
    }

    public ConfigDialog(Context context, int type, String content, int cancleType) {
        super(context);
        this.context = context;
        this.type = type;
        this.content = content;
        this.cancleType = cancleType;
        try{
            initView();
        }catch (Exception e){
            e.printStackTrace();
            dismiss();
        }

    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_config_fragment_my, null);
        setContentView(view);
        initWindow();
        switch (type) {
            //退出登录
            case 1:
                llMyTitle = view.findViewById(R.id.dialog_config_longin_out);
                llMyTitle.setVisibility(View.VISIBLE);
                tvClose = view.findViewById(R.id.dialog_config_longin_out_cancle);
                tvGiftHave = view.findViewById(R.id.dialog_config_longin_out_ok);
                tvContent = view.findViewById(R.id.dialog_config_longin_out_content);
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
            case 2:
                llMyTitle = view.findViewById(R.id.dialog_config_longin_code_no_have);
                llMyTitle.setVisibility(View.VISIBLE);
                tvClose = view.findViewById(R.id.dialog_config_longin_code_no_have_cancle);
                tvGiftHave = view.findViewById(R.id.dialog_config_longin_code_no_have_ok);
                tvContent = view.findViewById(R.id.dialog_config_longin_code_no_content);
                if (!StringUtil.isEmpty(content)) {
                    tvContent.setText(content);
                }
                if (cancleType == 0) {
                    tvGiftHave.setText("手机短信登录");
                } else if (cancleType == 1) {
                    tvGiftHave.setText("找回密码");
                }
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
            case 3:
                llMyTitle = view.findViewById(R.id.dialog_config_longin_out);
                llMyTitle.setVisibility(View.VISIBLE);
                tvClose = view.findViewById(R.id.dialog_config_longin_out_cancle);
                tvGiftHave = view.findViewById(R.id.dialog_config_longin_out_ok);
                tvContent = view.findViewById(R.id.dialog_config_longin_out_content);

                tvContent.setText("是否清除缓存");
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
            case 4: //权限设置
                llMyTitle = view.findViewById(R.id.dialog_config_accredit);
                llMyTitle.setVisibility(View.VISIBLE);
                tvGiftHave = view.findViewById(R.id.dialog_config_accredit_cancle);
                tvContinue = view.findViewById(R.id.tv_continue);
                tvGiftHave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onOKClick("");
                    }
                });
                tvGiftHave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onOKClick("");
                    }
                });
                tvContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.onCancelClick();
                    }
                });
                break;
            case 5:
                //绑定手机号操作
                llMyTitle = view.findViewById(R.id.dialog_config_longin_bind_code);
                llMyTitle.setVisibility(View.VISIBLE);
                tvGiftHave = view.findViewById(R.id.dialog_config_longin_bind_code_no_have_ok);
                tvClose = view.findViewById(R.id.dialog_config_longin_bind_code_no_have_cancle);
                tvContent = view.findViewById(R.id.dialog_config_longin_bind_code_no_content);
                tvContent.setText(StringUtil.isEmpty(content) ? "" : content);
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onCancelClick();
                    }
                });
                tvGiftHave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onOKClick("");
                    }
                });
                break;
            case 6:
                llMyTitle = view.findViewById(R.id.dialog_config_longin_bind_code);
                llMyTitle.setVisibility(View.VISIBLE);
                tvGiftHave = view.findViewById(R.id.dialog_config_longin_bind_code_no_have_ok);
                tvGiftHave.setText("去登陆");
                tvClose = view.findViewById(R.id.dialog_config_longin_bind_code_no_have_cancle);
                tvClose.setVisibility(View.GONE);
                tvContent = view.findViewById(R.id.dialog_config_longin_bind_code_no_content);
                tvContent.setText(StringUtil.isEmpty(content) ? "" : content);
                tvGiftHave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogClickListener.onOKClick("");
                    }
                });
                break;
        }
    }

    /**
     * 添加黑色半透明背景
     */
    private long exitTime = 0;

    private void initWindow() {
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(0));//设置window背景
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕尺寸
        lp.width = d.widthPixels; //宽度为屏幕80%
        lp.gravity = Gravity.CENTER;  //中央居中
        dialogWindow.setAttributes(lp);
        if (type == 4) {
            setCanceledOnTouchOutside(false);
            setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {

                    if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        if (System.currentTimeMillis() - exitTime > 2000) {
                            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                            exitTime = System.currentTimeMillis();
                        } else {
                            dialogClickListener.onCancelClick();
                            System.exit(0);
                        }
                        return true;
                    }
                    return true;
                }
            });
        }

    }

    public void setOnDialogClickListener(DialogClickListener clickListener) {
        dialogClickListener = clickListener;
    }

    /**
     * 添加按钮点击事件
     */
    public interface DialogClickListener {
        void onOKClick(String code);

        void onCancelClick();
    }
}
