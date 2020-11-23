package com.newsuper.t.juejinbao.ui.song.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.song.Event.MusicPauseEvent;
import com.juejinchain.android.module.song.Event.MusicStartEvent;
import com.juejinchain.android.module.song.manager.SongPlayManager;
import com.juejinchain.android.module.song.view.AudioWaveView;
import com.lzx.starrysky.provider.SongInfo;
import com.ys.network.base.BaseAdapter;
import com.ys.network.base.BaseHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongListAdapter extends BaseAdapter<SongInfo> {
    private static final String TAG = "SongListAdapter";

    private OnSongClickListener listener;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicStartEvent(MusicStartEvent event) {
        Log.d(TAG, "onMusicStartEvent");
        notifyDataSetChanged(SongPlayManager.getInstance().getSongList());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicPauseEvent(MusicPauseEvent event) {
        Log.d(TAG, "onMusicPauseEvent");
        notifyDataSetChanged(SongPlayManager.getInstance().getSongList());
    }

    public SongListAdapter(List<SongInfo> list, Activity context) {
        super(list, context);
        EventBus.getDefault().register(this);
    }

    public void notifyDataSetChanged(List<SongInfo> dataList) {
        setList(dataList);
    }

    @Override
    protected int getContentView(int viewType) {
        return R.layout.item_song_list;
    }

    @Override
    protected void covert(BaseHolder holder, SongInfo songInfo, int position) {
        TextView tvMusicName = holder.getView().findViewById(R.id.tv_music_name);
        TextView tvArtistName = holder.getView().findViewById(R.id.tv_artist_name);
        TextView tvPosition = holder.getView().findViewById(R.id.tv_position);
        AudioWaveView audioWaveView = holder.getView().findViewById(R.id.audioWaveView);
        ImageView ivDel = holder.getView().findViewById(R.id.iv_del);
        RelativeLayout rlSongPlay = holder.getView().findViewById(R.id.rl_song_play);

        tvMusicName.setText(songInfo.getSongName());
        tvPosition.setText(position + 1 + "");
        tvArtistName.setText(songInfo.getArtist());
        if(SongPlayManager.getInstance().isPaused()){
            audioWaveView.setVisibility(View.GONE);
            tvMusicName.setTextColor(mContext.getResources().getColor(R.color.c_333333));
            tvArtistName.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            tvPosition.setTextColor(mContext.getResources().getColor(R.color.c_333333));
        }else{
            if (SongPlayManager.getInstance().isCurMusicPlaying(songInfo.getSongId())
                    && SongPlayManager.getInstance().isPlaying()) {
                audioWaveView.setVisibility(View.VISIBLE);
                tvMusicName.setTextColor(mContext.getResources().getColor(R.color.c_ff3300));
                tvArtistName.setTextColor(mContext.getResources().getColor(R.color.c_ff3300));
                tvPosition.setTextColor(mContext.getResources().getColor(R.color.c_ff3300));
            } else {
                audioWaveView.setVisibility(View.GONE);
                tvMusicName.setTextColor(mContext.getResources().getColor(R.color.c_333333));
                tvArtistName.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                tvPosition.setTextColor(mContext.getResources().getColor(R.color.c_333333));
            }
        }
        rlSongPlay.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMusicClick(position);
            }
        });

        ivDel.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelClick(position);
            }
        });

    }

    public interface OnSongClickListener {
        void onMusicClick(int position);

        void onDelClick(int position);
    }

    public void setListener(OnSongClickListener listener) {
        this.listener = listener;
    }
}
