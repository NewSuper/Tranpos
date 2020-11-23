package com.newsuper.t.juejinbao.ui.song.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lzx.starrysky.provider.SongInfo;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.BaseAdapter;
import com.newsuper.t.juejinbao.base.BaseHolder;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.ui.song.Event.MusicCancelEvent;
import com.newsuper.t.juejinbao.ui.song.Event.MusicPauseEvent;
import com.newsuper.t.juejinbao.ui.song.Event.MusicStartEvent;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.ui.song.view.AudioWaveView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MusicMenuAdapter extends BaseAdapter<SongInfo> {
    private static final String TAG = "MusicMenuAdapter";

    private OnSongClickListener listener;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicStartEvent(MusicStartEvent event) {
        Log.d(TAG, "onMusicStartEvent");
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

    public MusicMenuAdapter(List<SongInfo> list, Activity context) {
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

        tvPosition.setText(String.format("%s", position + 1));

        switch (songInfo.getMimeType()){
            case Constant.MUSIC_COLLECTION:
            case Constant.MUSIC_HISTORY:
                ivDel.setImageResource(R.mipmap.ic_music_del);
                break;
            default:
                if (SongPlayManager.getInstance().isPaused()) {
                    ivDel.setImageResource(R.mipmap.ic_play);
                } else {
                    if (SongPlayManager.getInstance().isCurMusicPlaying(songInfo.getSongId())) {
                        ivDel.setImageResource(R.mipmap.ic_pause);
                    } else {
                        ivDel.setImageResource(R.mipmap.ic_play);
                    }
                }
                break;
        }

        tvMusicName.setText(songInfo.getSongName());
        tvArtistName.setText(songInfo.getArtist());
        if(SongPlayManager.getInstance().isPaused()){
            audioWaveView.setVisibility(View.GONE);
            tvMusicName.setTextColor(mContext.getResources().getColor(R.color.c_333333));
            tvArtistName.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            tvPosition.setTextColor(mContext.getResources().getColor(R.color.c_333333));
        }else{
            if (SongPlayManager.getInstance().getSongList().size()>0 && SongPlayManager.getInstance().isCurMusicPlaying(songInfo.getSongId())&& SongPlayManager.getInstance().isPlaying()) {
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

    public void destory(){
        EventBus.getDefault().unregister(this);
    }

}
