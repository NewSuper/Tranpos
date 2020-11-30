package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.top.adapter.PorterAdapter;
import com.newsuper.t.consumer.function.top.adapter.PorterAdapter2;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 广告海报
 */

public class WAdvertismentPorterView extends LinearLayout implements View.OnClickListener{
    private AdvertismentItemClick advertismentItemClick;
    private Context context;
    //1左2右：每张图片宽高都固定（大图375X750，小图375X375）等比压缩后居中裁剪
    public static int STYLE_LEFT_ONE_RIGHT_TWO = 1;
    //1左3右：每张图片宽高都固定（大图375X750，中图375X375，小图188X375）等比压缩后居中裁剪
    public static int STYLE_LEFT_ONE_RIGHT_THREE = 3;

    //1左4右：每张图片宽高都固定（大图250X500，小图250X250）等比压缩后居中裁剪
    public static int STYLE_LINE_ONE_RIGHT_FOUR = 4;
    //2左2右：每张图片宽高都固定（375X375）等比压缩后居中裁剪
    public static int STYLE_LEFT_TWO_RIGHT_TWO = 2;

    //1行2（3、4）个：每张图片宽固定（屏幕宽/N）等比计算出第一张图片高度，再等比计算出其余图片高度，
    // 比第一张图片高的上下裁剪；比第一张图片矮的先等比拉伸至第一张图片高再左右裁剪
    public static int STYLE_ROW_ONE_COLUMN_TWO = 5;
    public static int STYLE_ROW_ONE_COLUMN_THREE = 6;
    public static int STYLE_ROW_ONE_COLUMN_FOUR = 7;

    //1上2下：每张图片宽高都固定（大图750X375，小图375X375）等比压缩后居中裁剪
    public static int STYLE_TOP_ONE_BOTTOM_TWO = 8;
    //2上4下：每张图片宽高都固定（大图375X288，小图188X244）等比压缩后居中裁剪
    public static int STYLE_TOP_TWO_BOTTOM_FOUR = 9;
    public  int SHOW_STYLE = 1;

    private int viewWidth;
    private int viewHeight;
    private float rate;
    private View mView;
    private GridViewHolder gridViewHolder;
    private GridViewHolder2 gridViewHolder2;
    private GridViewHolder3 gridViewHolder3;
    public WAdvertismentPorterView(Context context) {
        super(context);
        viewWidth = UIUtils.getWindowWidth();
        initView(context);
    }
    public WAdvertismentPorterView(Context context,int style,int space,int pageSpace) {
        super(context);
        viewWidth = UIUtils.getWindowWidth();
        SHOW_STYLE = style;
        this.space = space;
        this.pageSpace = pageSpace;
        initView(context);
    }
    public WAdvertismentPorterView(Context context,int style) {
        super(context);
        viewWidth = UIUtils.getWindowWidth();

        initView(context);
    }

    public WAdvertismentPorterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public WAdvertismentPorterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    boolean radioVal;
    int space,pageSpace;


    public void setRadioVal(boolean radioVal) {
        this.radioVal = !radioVal;
        LogUtil.log("WAdvertismentPorterView", "setRadioVal  == "+this.radioVal);
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void initView(Context context) {
        this.context = context;
        setPadding(pageSpace,pageSpace,pageSpace,pageSpace);
        LogUtil.log("WAdvertismentPorterView","w == "+ UIUtils.getWindowWidth()+"  SHOW_STYLE ==  "+SHOW_STYLE);
        //1左4右 每张图片宽高都固定（大图250X500，小图250X250）等比压缩后居中裁剪
        if (SHOW_STYLE == STYLE_LINE_ONE_RIGHT_FOUR){
            float rate = ((float)500) / ((float) 750);
            viewHeight = (int)( viewWidth * rate);
            LogUtil.log("WAdvertismentPorterView","h == " + viewHeight + "   rate == "+rate);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_advertisement, null);
            gridViewHolder2 = new GridViewHolder2(view);
            gridViewHolder2.gridview.setNumColumns(1);
            gridViewHolder2.gridview2.setNumColumns(2);
            gridViewHolder2.gridview2.setVerticalSpacing(space);
            gridViewHolder2.gridview2.setHorizontalSpacing(space);
            gridViewHolder2.vw.getLayoutParams().width = space;
            gridViewHolder2.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(0);
                }
            });
            gridViewHolder2.gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position + 1);
                }
            });
            addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,viewHeight));
        }
        //2上4下 每张图片宽高都固定（大图375X288，小图188X244）等比压缩后居中裁剪
        else if (SHOW_STYLE == STYLE_TOP_TWO_BOTTOM_FOUR){
            float rate = ((float)532) / ((float) 750);
            viewHeight = (int)( viewWidth * rate);
            LogUtil.log("WAdvertismentPorterView","h == " + viewHeight + "   rate == "+rate);
            mView = LayoutInflater.from(context).inflate(R.layout.layout_advertisement_2, null);
            gridViewHolder2 = new GridViewHolder2(mView);
            gridViewHolder2.gridview.setNumColumns(2);
            gridViewHolder2.gridview2.setNumColumns(4);
            gridViewHolder2.gridview.setHorizontalSpacing(space);
            gridViewHolder2.gridview2.setHorizontalSpacing(space);
            gridViewHolder2.vw.getLayoutParams().height = space;
            gridViewHolder2.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position);
                }
            });
            gridViewHolder2.gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position + 2);
                }
            });
            addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT,viewHeight));
        }
       ////1左2右 每张图片宽高都固定（大图375X750，小图375X375）等比压缩后居中裁剪
        else if (SHOW_STYLE == STYLE_LEFT_ONE_RIGHT_TWO){
            viewHeight = viewWidth;
            mView = LayoutInflater.from(context).inflate(R.layout.layout_advertisement_f_1_r_2, null);
            gridViewHolder2 = new GridViewHolder2(mView);
            gridViewHolder2.gridview.setNumColumns(1);
            gridViewHolder2.gridview2.setNumColumns(1);
            gridViewHolder2.gridview2.setVerticalSpacing(space);
            gridViewHolder2.gridview2.setHorizontalSpacing(space);
            gridViewHolder2.vw.getLayoutParams().width = space;
            gridViewHolder2.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position);
                }
            });
            gridViewHolder2.gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position + 1);
                }
            });
            addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT,viewWidth));
        }
        //1左3右 每张图片宽高都固定（大图375X750，中图375X375，小图188X375）等比压缩后居中裁剪
        else if (SHOW_STYLE == STYLE_LEFT_ONE_RIGHT_THREE ){
            mView = LayoutInflater.from(context).inflate(R.layout.layout_advertisement_f_1_r_3, null);
            viewHeight = viewWidth;
            gridViewHolder3 = new GridViewHolder3(mView);
            gridViewHolder3.gridview.setNumColumns(1);
            gridViewHolder3.gridview2.setNumColumns(1);
            gridViewHolder3.gridview3.setNumColumns(2);
            gridViewHolder3.gridview3.setHorizontalSpacing(space);
            gridViewHolder3.vw.getLayoutParams().width = space;
            gridViewHolder3.vw2.getLayoutParams().height = space;
            gridViewHolder3.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position);
                }
            });
            gridViewHolder3.gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position + 1);
                }
            });
            gridViewHolder3.gridview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position + 2);
                }
            });
            addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT,viewWidth));
        }
        //1上2下
        else if (SHOW_STYLE ==  STYLE_TOP_ONE_BOTTOM_TWO){
            viewHeight = viewWidth;
            LogUtil.log("WAdvertismentPorterView","h == " + viewHeight + "   rate == "+rate);
            mView = LayoutInflater.from(context).inflate(R.layout.layout_advertisement_r_1_c_2, null);
            gridViewHolder2 = new GridViewHolder2(mView);
            gridViewHolder2.gridview.setNumColumns(1);
            gridViewHolder2.gridview2.setNumColumns(2);
            gridViewHolder2.gridview2.setHorizontalSpacing(space);
            gridViewHolder2.vw.getLayoutParams().height = space;
            gridViewHolder2.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position);
                }
            });
            gridViewHolder2.gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position + 1);
                }
            });
            addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT,viewWidth));
        }

        //1行2个
        else if (SHOW_STYLE == STYLE_ROW_ONE_COLUMN_TWO){
            mView = LayoutInflater.from(context).inflate(R.layout.layout_advertisement_1_2, null);
            gridViewHolder = new GridViewHolder(mView);
            gridViewHolder.gridview.setHorizontalSpacing(space);
            gridViewHolder.gridview.setNumColumns(2);
            gridViewHolder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position);
                }
            });
        }
        //1行3个
        else if (SHOW_STYLE == STYLE_ROW_ONE_COLUMN_THREE){
            mView = LayoutInflater.from(context).inflate(R.layout.layout_advertisement_1_2, null);
            gridViewHolder = new GridViewHolder(mView);
            gridViewHolder.gridview.setHorizontalSpacing(space);
            gridViewHolder.gridview.setNumColumns(3);
            gridViewHolder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position);
                }
            });
        }
        //1行4个
        else if (SHOW_STYLE == STYLE_ROW_ONE_COLUMN_FOUR){
            mView = LayoutInflater.from(context).inflate(R.layout.layout_advertisement_1_2, null);
            gridViewHolder = new GridViewHolder(mView);
            gridViewHolder.gridview.setHorizontalSpacing(space);
            gridViewHolder.gridview.setNumColumns(4);
            gridViewHolder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position);
                }
            });

        }
        //2行2个
        else if (SHOW_STYLE == STYLE_LEFT_TWO_RIGHT_TWO){
            mView = LayoutInflater.from(context).inflate(R.layout.layout_advertisement_1_2, null);
            gridViewHolder = new GridViewHolder(mView);
            gridViewHolder.gridview.setNumColumns(2);
            gridViewHolder.gridview.setHorizontalSpacing(space);
            gridViewHolder.gridview.setVerticalSpacing(space);
            gridViewHolder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertismentItemClick.onItemClick(position);
                }
            });
        }
    }


    public void setAdvertismentData(final WTopBean.AdvertismentPosterData data, AdvertismentItemClick advertismentItemClick){
        this.advertismentItemClick = advertismentItemClick;
         if (SHOW_STYLE == STYLE_ROW_ONE_COLUMN_TWO || SHOW_STYLE == STYLE_ROW_ONE_COLUMN_THREE || SHOW_STYLE == STYLE_ROW_ONE_COLUMN_FOUR){
             if (data.list != null && data.list.size() > 0){
                 int height = FormatUtil.numInteger(data.list.get(0).height); //px
                 int width = FormatUtil.numInteger(data.list.get(0).width); //px
                 final float cbi = ((float)height) / ((float) width);
                 int mWidth = UIUtils.getWindowWidth() / data.list.size() ;
                 int mHeight = (int)( mWidth * cbi);
                 LogUtil.log("WAdvertismentPorterView", "bili== " + cbi);
                 LogUtil.log("WAdvertismentPorterView", "mWidth== " + mWidth);
                 LogUtil.log("WAdvertismentPorterView", "mHeight== " + mHeight);
                 gridViewHolder.gridview.setAdapter(new PorterAdapter(context,data.list,mHeight,radioVal));
                 addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT,mHeight));
             }
        }else if (SHOW_STYLE == STYLE_LEFT_TWO_RIGHT_TWO){
             int h = (viewWidth - space) / 2;
             gridViewHolder.gridview.setAdapter(new PorterAdapter(context,data.list,h,radioVal));
             addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT,viewWidth));
         }else if (SHOW_STYLE == STYLE_LINE_ONE_RIGHT_FOUR){
             ArrayList<String> list1 = new ArrayList<>();
             ArrayList<String> list2 = new ArrayList<>();
             for (int i = 0 ; i < data.list.size();i++){
                 String url = data.list.get(i).image;
                 if (i == 0){
                     list1.add(url);
                 }else {
                     list2.add(url);
                 }
             }
             int h = (viewHeight - space) / 2;
             gridViewHolder2.gridview.setAdapter(new PorterAdapter2(context,list1,viewHeight,radioVal));
             gridViewHolder2.gridview2.setAdapter(new PorterAdapter2(context,list2,h,radioVal));
         } else if (SHOW_STYLE == STYLE_LEFT_ONE_RIGHT_TWO){
             ArrayList<String> list1 = new ArrayList<>();
             ArrayList<String> list2 = new ArrayList<>();
             for (int i = 0 ; i < data.list.size();i++){
                 String url = data.list.get(i).image;
                 if (i == 0){
                     list1.add(url);
                 }else {
                     list2.add(url);
                 }
             }
             int h = (viewHeight - space) / 2;
             gridViewHolder2.gridview.setAdapter(new PorterAdapter2(context,list1,viewHeight,radioVal));
             gridViewHolder2.gridview2.setAdapter(new PorterAdapter2(context,list2,h,radioVal));
         }
         else if (SHOW_STYLE == STYLE_TOP_TWO_BOTTOM_FOUR){
             ArrayList<String> list1 = new ArrayList<>();
             ArrayList<String> list2 = new ArrayList<>();
             for (int i = 0 ; i < data.list.size();i++){
                 String url = data.list.get(i).image;
                 if (i < 2){
                     list1.add(url);
                 }else {
                     list2.add(url);
                 }
             }
             int h = (viewHeight - space) / 2;
             gridViewHolder2.gridview.setAdapter(new PorterAdapter2(context,list1,h,radioVal));
             gridViewHolder2.gridview2.setAdapter(new PorterAdapter2(context,list2,h,radioVal));
         }else if (SHOW_STYLE == STYLE_TOP_ONE_BOTTOM_TWO){
             ArrayList<String> list1 = new ArrayList<>();
             ArrayList<String> list2 = new ArrayList<>();
             for (int i = 0 ; i < data.list.size();i++){
                 String url = data.list.get(i).image;
                 if (i == 0){
                     list1.add(url);
                 }else {
                     list2.add(url);
                 }
             }
             int h = (viewHeight - space) / 2;
             gridViewHolder2.gridview.setAdapter(new PorterAdapter2(context,list1,h,radioVal));
             gridViewHolder2.gridview2.setAdapter(new PorterAdapter2(context,list2,h,radioVal));
         }else if (SHOW_STYLE  == STYLE_LEFT_ONE_RIGHT_THREE){
             ArrayList<String> list1 = new ArrayList<>();
             ArrayList<String> list2 = new ArrayList<>();
             ArrayList<String> list3 = new ArrayList<>();
             for (int i = 0 ; i < data.list.size();i++){
                 String url = data.list.get(i).image;
                 if (i ==  0){
                     list1.add(url);
                 }else if (i == 1){
                     list2.add(url);
                 }else {
                     list3.add(url);
                 }
             }
             int h = (viewHeight - space) / 2;
             gridViewHolder3.gridview.setAdapter(new PorterAdapter2(context,list1,viewHeight,radioVal));
             gridViewHolder3.gridview2.setAdapter(new PorterAdapter2(context,list2,h,radioVal));
             gridViewHolder3.gridview3.setAdapter(new PorterAdapter2(context,list3,h,radioVal));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_ad_1:
                advertismentItemClick.onItemClick(0);
                break;
            case R.id.iv_ad_2:
                advertismentItemClick.onItemClick(1);
                break;
            case R.id.iv_ad_3:
                advertismentItemClick.onItemClick(2);
                break;
            case R.id.iv_ad_4:
                advertismentItemClick.onItemClick(3);
                break;
        }
    }
    static class GridViewHolder {
        @BindView(R.id.gridview)
        WGridView gridview;

        GridViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class GridViewHolder2 {
        @BindView(R.id.gridview)
        WGridView gridview;
        @BindView(R.id.gridview2)
        WGridView gridview2;
        @BindView(R.id.vw)
        View vw;

        GridViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class GridViewHolder3 {
        @BindView(R.id.gridview)
        WGridView gridview;
        @BindView(R.id.gridview2)
        WGridView gridview2;
        @BindView(R.id.vw)
        View vw;
        @BindView(R.id.gridview3)
        WGridView gridview3;
        @BindView(R.id.vw2)
        View vw2;

        GridViewHolder3(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface AdvertismentItemClick{
       void onItemClick(int position);
    }
}
