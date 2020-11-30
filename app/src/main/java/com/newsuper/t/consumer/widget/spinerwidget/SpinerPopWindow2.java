package com.newsuper.t.consumer.widget.spinerwidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.newsuper.t.R;

import java.util.List;

public class SpinerPopWindow2 extends PopupWindow implements OnItemClickListener{

	private Context mContext;
	private NormalSpinerAdapter mAdapter;
	private AbstractSpinerAdapter.IOnItemSelectListener mItemSelectListener;


	public SpinerPopWindow2(Context context)
	{
		super(context);
		
		mContext = context;
		init();
	}
	
	
	public void setItemListener(AbstractSpinerAdapter.IOnItemSelectListener listener){
		mItemSelectListener = listener;
	}

	
	private void init()
	{
		@SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout2, null);
		setContentView(view);		
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		
		setFocusable(true);
    	ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);


		ListView mListView = (ListView) view.findViewById(R.id.listview);
		

		mAdapter = new NormalSpinerAdapter(mContext);	
		mListView.setAdapter(mAdapter);	
		mListView.setOnItemClickListener(this);
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
