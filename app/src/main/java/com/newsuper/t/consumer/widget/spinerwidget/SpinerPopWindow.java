package com.newsuper.t.consumer.widget.spinerwidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.newsuper.t.R;

import java.util.List;

public class SpinerPopWindow extends PopupWindow implements OnItemClickListener{

	private Context mContext;
	private NormalSpinerAdapter mAdapter;
	private AbstractSpinerAdapter.IOnItemSelectListener mItemSelectListener;
	
	
	public SpinerPopWindow(Context context)
	{
		super(context);
		
		mContext = context;
		init();
	}
	
	
	public void setItemListener(AbstractSpinerAdapter.IOnItemSelectListener listener){
		mItemSelectListener = listener;
	}

	private ListView mListview;
    private LinearLayout ll_root;
	private void init()
	{
		@SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
		setContentView(view);		
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		
		setFocusable(true);
    	ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
		ll_root=(LinearLayout)view.findViewById(R.id.ll_root);
		mListview=(ListView)view.findViewById(R.id.listview);
		mAdapter = new NormalSpinerAdapter(mContext);
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(this);
	}
	
	public void setSpinnerColor(int drawableId, int color){
		this.ll_root.setBackgroundResource(drawableId);
		this.mListview.setDivider(new ColorDrawable(color));
		this.mListview.setDividerHeight(1);

	}


	public void refreshData(List<String> list, int selIndex)
	{
		if (list != null && selIndex  != -1)
		{
			mAdapter.refreshData(list, selIndex);
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mItemSelectListener != null){
			mItemSelectListener.onItemClick(pos);
		}
	}


	
}
