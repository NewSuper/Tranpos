package com.newsuper.t.consumer.widget.spinerwidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.newsuper.t.R;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public abstract class AbstractSpinerAdapter<T> extends BaseAdapter {

	public static interface IOnItemSelectListener{
		public void onItemClick(int pos);
	};

	private List<T> mObjects = new ArrayList<>();

	private LayoutInflater mInflater;
	
	 public  AbstractSpinerAdapter(Context context){
		 init(context);
	 }
	 
	 public void refreshData(List<T> objects, int selIndex){
		 mObjects = objects;
		 if (selIndex < 0){
			 selIndex = 0;
		 }
		 if (selIndex >= mObjects.size()){
			 selIndex = mObjects.size() - 1;
		 }

		 int mSelectItem = selIndex;
	 }
	 
	 private void init(Context context) {
		 Context mContext = context;
	        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }
	    
	    
	@Override
	public int getCount() {

		return mObjects.size();
	}

	@Override
	public Object getItem(int pos) {
		return mObjects.get(pos).toString();
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		 ViewHolder viewHolder;
	     if (convertView == null) {
	    	 convertView = mInflater.inflate(R.layout.spiner_item_layout, null);
	         viewHolder = new ViewHolder();
	         viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textView);
	         convertView.setTag(viewHolder);
	     } else {
	         viewHolder = (ViewHolder) convertView.getTag();
	     }
	     String item = (String) getItem(pos);
		 viewHolder.mTextView.setText(item);
	     return convertView;
	}

	public static class ViewHolder
	{
	    public TextView mTextView;
    }

}
