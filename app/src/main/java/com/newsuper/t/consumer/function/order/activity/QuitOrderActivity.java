package com.newsuper.t.consumer.function.order.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.QuitOrderResason;
import com.newsuper.t.consumer.function.inter.IEditOrderFragmentView;
import com.newsuper.t.consumer.function.inter.ISelectQuitReason;
import com.newsuper.t.consumer.function.order.presenter.EditListPresenter;
import com.newsuper.t.consumer.function.order.request.EditOrderRequest;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.SelectReasonDialog;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class QuitOrderActivity extends BaseActivity implements CustomToolbar.CustomToolbarListener,View.OnClickListener ,ISelectQuitReason,IEditOrderFragmentView {


    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.ll_select_reseason)
    LinearLayout llSelectReseason;
    @BindView(R.id.et_reseason)
    EditText etReseason;
    @BindView(R.id.tv_text_num)
    TextView tvTextNum;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private ArrayList<QuitOrderResason> resasons=new ArrayList<>();
    private int reasonPos=0;
    private EditListPresenter editPresenter;
    private String flag;
    private int position;
    private LoadingDialog2 loadingDialog;
    private String order_no;

    @Override
    public void initData() {
        resasons.clear();
        resasons.add(new QuitOrderResason("配送时间太长了",false));
        resasons.add(new QuitOrderResason("商家联系我取消订单",false));
        resasons.add(new QuitOrderResason("点错了，我要重新点",false));
        resasons.add(new QuitOrderResason("临时有事，不想要了",false));
        resasons.add(new QuitOrderResason("其它原因",false));
        editPresenter = new EditListPresenter(this);
        flag=getIntent().getStringExtra("flag");
        position=getIntent().getIntExtra("position",0);
        order_no=getIntent().getStringExtra("order_no");
    }

    @Override
    public void quitReason(int reasonPos) {
       this.reasonPos=reasonPos;
       switch (reasonPos){
           case 1:
               tvSelect.setText("配送时间太长了");
               break;
           case 2:
               tvSelect.setText("商家联系我取消订单");
               break;
           case 3:
               tvSelect.setText("点错了，我要重新点");
               break;
           case 4:
               tvSelect.setText("临时有事，不想要了");
               break;
           case 5:
               tvSelect.setText("其它原因了");
               break;
           default:
               tvSelect.setText("");
               break;
       }
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_quit_order);
        ButterKnife.bind(this);
        toolbar.setTitleText("取消订单");
        toolbar.setCustomToolbarListener(this);
        toolbar.setTvMenuVisibility(View.INVISIBLE);

        llSelectReseason.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
        etReseason.addTextChangedListener(textWatcher);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        private int editStart;
        private int editEnd;
        private int maxLen = 50; // the max byte
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int len=s.toString().length();
            tvTextNum.setText(len+"");
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = etReseason.getSelectionStart();
            editEnd = etReseason.getSelectionEnd();
            // 先去掉监听器，否则会出现栈溢出
            etReseason.removeTextChangedListener(textWatcher);
            if (!TextUtils.isEmpty(etReseason.getText())) {
                String etstring = etReseason.getText().toString().trim();
                while (s.toString().length() > maxLen-1) {
                    s.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            }

            etReseason.setText(s);
            etReseason.setSelection(editStart);

            // 恢复监听器
            etReseason.addTextChangedListener(textWatcher);
            // end by zyf --------------------------
        }

    };


    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(this, R.style.transparentDialog);
        }
        loadingDialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_reseason:
                showSelectReasonDialog();
                break;
            case R.id.tv_commit:
                String select=tvSelect.getText().toString().trim();
                String reseason="";
                reseason=etReseason.getText().toString().trim();
                if(TextUtils.isEmpty(select)&&TextUtils.isEmpty(reseason)){
                    UIUtils.showToast("请添加取消原因");
                    return;
                }else{
                    //请求接口
                    showLoadingDialog();
                    HashMap<String, String> params = EditOrderRequest.quitOrderRequest(SharedPreferencesUtil.getToken(),Const.ADMIN_ID, order_no, reasonPos+"",reseason);
                    editPresenter.loadData(UrlConst.CANCELORDER, params, flag, position);
                }
                break;
        }
    }


    @Override
    public void notifyOrderList(String flag, int position) {
         //跳转订单列表刷新订单
        Intent intent=new Intent();
        intent.putExtra("flag",flag);
        intent.putExtra("position",position);
        setResult(RESULT_OK,intent);
        this.finish();
    }

    private void showSelectReasonDialog(){
        SelectReasonDialog dialog = new SelectReasonDialog(this,R.style.cartdialog,resasons,this);
        Window window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.x = 0;
        params.y = 0;
        params.dimAmount = 0.5f;
        window.setAttributes(params);
    }


    @Override
    public void onBackClick() {
        this.finish();
    }


    @Override
    public void onMenuClick() {

    }

    @Override
    public void dialogDismiss() {
        if(null!=loadingDialog){
            loadingDialog.cancel();
        }
    }

    @Override
    public void showToast(String s) {

    }


}
