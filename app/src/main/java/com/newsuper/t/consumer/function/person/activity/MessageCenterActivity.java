package com.newsuper.t.consumer.function.person.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.MsgCenterBean;
import com.xunjoy.lewaimai.consumer.function.person.adapter.MessageAdapter;
import com.xunjoy.lewaimai.consumer.function.person.internal.IMessageCenterView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.MessageCenterPresenter;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageCenterActivity extends BaseActivity implements IMessageCenterView{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.ms_listView)
    ListView msgListView;
    private MessageAdapter msgAdapter;
    private MessageCenterPresenter centerPresenter;
    private ArrayList<MsgCenterBean.MsgCenterData> msgLsit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);
        toolbar.setTitleText("消息中心");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        msgLsit = new ArrayList<>();
        msgAdapter = new MessageAdapter(this,msgLsit);
        msgListView.setAdapter(msgAdapter);
        msgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msgLsit.get(position).is_read = "1";
                msgAdapter.notifyDataSetChanged();
                String info_id = msgLsit.get(position).info_id;
                Intent intent = new Intent(MessageCenterActivity.this,MessageDetailActivity.class);
                intent.putExtra("id",info_id);
                startActivity(intent);
            }
        });
        centerPresenter = new MessageCenterPresenter(this);
        centerPresenter.getMsgList();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void getMessageDataSuccess(MsgCenterBean bean) {
        if (bean.data != null){
            msgLsit.clear();
            msgLsit.addAll(bean.data);
            msgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getMessageDataFail() {

    }
}
