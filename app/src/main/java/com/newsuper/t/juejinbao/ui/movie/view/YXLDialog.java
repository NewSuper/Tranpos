package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.DialogXylBinding;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.bean.YXL;

import java.util.ArrayList;
import java.util.List;

//云线路选择
public class YXLDialog {
    private Context context;

    DialogXylBinding mViewBinding;

    private Dialog mDialog;

    private EasyAdapter adapter;
    private List<YXL> yxls = new ArrayList<>();

    private ChangeYXL changeYXL;


    public YXLDialog(Context context , List<YXL> yxls , ChangeYXL changeYXL) {
        this.context = context;
        this.yxls = yxls;
        this.changeYXL = changeYXL;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_xyl, null, false);
        mDialog = new Dialog(context, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);

        mViewBinding.llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });


        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(context));
        mViewBinding.rv.setNestedScrollingEnabled(false);

        mViewBinding.rv.setAdapter(adapter = new EasyAdapter(context, new EasyAdapter.CommonAdapterListener() {

            @Override
            public EasyAdapter.MyViewHolder getHeaderViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter.MyViewHolder(context, R.layout.item_yxl, viewGroup) {

                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean , int position) {
                        super.setData(typeBean , position);
                        object = typeBean;
                        YXL yxl = (YXL)object;

                        TextView tv = itemView.findViewById(R.id.tv);
                        tv.setText(yxl.name);
                        if(yxl.select){
                            tv.setTextColor(context.getResources().getColor(R.color.app_color));
                        }else{
                            tv.setTextColor(context.getResources().getColor(R.color.app_text));
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for(YXL yxl2 : yxls){
                                    yxl2.select = false;
                                }
                                yxl.select = true;
                                adapter.notifyDataSetChanged();
                                changeYXL.change(yxl.name ,yxl.url);
                                hide();
                            }
                        });

                    }

                    @Override
                    public void onClick(View view) {
                        super.onClick(view);

//                        SearchResultDataEntity.DataBean dataBean = (SearchResultDataEntity.DataBean)object;
//                        //文本改变
//                        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_INPUT_ONLY, dataBean.getVod_name()));
//                        //跳转详情
//                        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, dataBean.getVod_name()));


                    }
                };
            }

            @Override
            public EasyAdapter.MyViewHolder getFooterViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getBlankViewHolder(ViewGroup viewGroup) {
                return null;
            }
        }));

        mViewBinding.rlBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        adapter.update(yxls);

    }



    public void show() {
        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }

    public interface ChangeYXL{
        void change(String name, String url);
    }

}
