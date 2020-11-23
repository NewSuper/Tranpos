package com.newsuper.t.juejinbao.ui.song.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ItemSongList2Binding;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.song.Activity.SongActivity;
import com.juejinchain.android.module.song.Event.MusicCancelEvent;
import com.juejinchain.android.module.song.Event.MusicPauseEvent;
import com.juejinchain.android.module.song.Event.MusicStartEvent;
import com.juejinchain.android.module.song.manager.SongPlayManager;
import com.lzx.starrysky.provider.SongInfo;
import com.ys.network.base.LoginEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SongListAdapter2 extends RecyclerView.Adapter<SongListAdapter2.ItemView> {
    protected Context context;
    protected List<SongInfo> beans = new ArrayList<>();

//    private OnItemClickListener onItemClickListener;

    public SongListAdapter2(Context context) {
        this.context = context;
        EventBus.getDefault().register(this);
//        this.onItemClickListener = onItemClickListener;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicStartEvent(MusicStartEvent event) {
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicPauseEvent(MusicPauseEvent event) {
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicCancelEveMusicCancelEventnt(MusicCancelEvent event) {
        notifyDataSetChanged();
    }


    public void update(List<SongInfo> beans) {
        this.beans = beans;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemView(DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)),
                R.layout.item_song_list2, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(ItemView holder, int position) {
        holder.setData(beans.get(position) , position);
    }


    @Override
    public int getItemCount() {
        return beans != null ? beans.size() : 0;
    }


    class ItemView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SongInfo songInfo;
        private ItemSongList2Binding mViewBinding;
        private int position;

        public ItemView(ItemSongList2Binding mViewBinding) {
            super((ViewGroup) mViewBinding.getRoot());
            this.mViewBinding = mViewBinding;
            itemView.setOnClickListener(this);
        }

        public void setData(SongInfo object, int position) {
            this.songInfo = object;
            this.position = position;

            if (SongPlayManager.getInstance().isPaused()) {
                mViewBinding.ivDel.setImageResource(R.mipmap.ic_play);
            } else {
                if (SongPlayManager.getInstance().isCurMusicPlaying(songInfo.getSongId())) {
                    mViewBinding.ivDel.setImageResource(R.mipmap.ic_pause);
                } else {
                    mViewBinding.ivDel.setImageResource(R.mipmap.ic_play);
                }
            }

            if(SongPlayManager.getInstance().isPaused()){
                mViewBinding.audioWaveView.setVisibility(View.GONE);
                mViewBinding.tvMusicName.setTextColor(context.getResources().getColor(R.color.c_333333));
                mViewBinding.tvArtistName.setTextColor(context.getResources().getColor(R.color.c_999999));
            }else{
                if (SongPlayManager.getInstance().getSongList().size()>0 && SongPlayManager.getInstance().isCurMusicPlaying(songInfo.getSongId())) {
                    mViewBinding.audioWaveView.setVisibility(View.VISIBLE);
                    mViewBinding.tvMusicName.setTextColor(context.getResources().getColor(R.color.c_ff3300));
                    mViewBinding.tvArtistName.setTextColor(context.getResources().getColor(R.color.c_ff3300));
                } else {
                    mViewBinding.audioWaveView.setVisibility(View.GONE);
                    mViewBinding.tvMusicName.setTextColor(context.getResources().getColor(R.color.c_333333));
                    mViewBinding.tvArtistName.setTextColor(context.getResources().getColor(R.color.c_999999));
                }
            }

            mViewBinding.tvMusicName.setText(songInfo.getSongName());
            mViewBinding.tvArtistName.setText(songInfo.getArtist());


        }

        @Override
        public void onClick(View v) {
            toSongActivity(position);

//            onItemClickListener.itemClick(songInfo , position);
        }


    }

//    public static interface OnItemClickListener{
//        public void itemClick(SongInfo songInfo , int position);
//    }


    private void toSongActivity(int position) {

        if(!LoginEntity.getIsLogin()){
            GuideLoginActivity.start(context,false,"");
            return;
        }

        if(position >= beans.size()){
            notifyDataSetChanged();
            return;
        }

//        SongPlayManager.getInstance().addSongAndPlay(beans.get(position));
        SongPlayManager.getInstance().addSongListAndPlay(beans, position);

        SongActivity.intentMe(context,beans.get(position));

    }

    public void destory(){
        EventBus.getDefault().unregister(this);
    }

}
