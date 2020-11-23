package com.newsuper.t.juejinbao.ui.song.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.craw.crawmusic.BeanMusic;
import com.juejinchain.android.module.song.manager.SongPlayManager;
import com.lzx.starrysky.provider.SongInfo;
import com.ys.network.base.PagerCons;

import java.util.List;

import static io.paperdb.Paper.book;


public class CrawMusicListDialog extends Dialog {

    boolean alDismiss;



    private EasyAdapter adapter;
    private RecyclerView rv;
    private TextView tv_close;

    public CrawMusicListDialog(Context context , List<BeanMusic> beanMusicList , CrawMusicListListener crawMusicListListener) {
        super(context);
        setContentView(R.layout.dialog_musiclist);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        getWindow().setAttributes(params);

//        setCancelable(false);
//        setCanceledOnTouchOutside(false);

        rv = findViewById(R.id.rv);
        tv_close = findViewById(R.id.tv_close);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(adapter = new EasyAdapter(context, new EasyAdapter.CommonAdapterListener() {
            @Override
            public EasyAdapter.MyViewHolder getHeaderViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter.MyViewHolder(context, R.layout.item_crawmusiclist, viewGroup) {
                    private BeanMusic beanMusic;

                    private TextView tv_songname;
                    private TextView tv_singer;


                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean , int position) {
                        super.setData(typeBean , position);
                        beanMusic = (BeanMusic) typeBean;
                        tv_songname = itemView.findViewById(R.id.tv_songname);
                        tv_singer = itemView.findViewById(R.id.tv_singer);

                        tv_songname.setText(beanMusic.getSongName());
                        tv_singer.setText(beanMusic.getSinger());
                    }

                    @Override
                    public void onClick(View view) {
                        super.onClick(view);
                        dismiss();

                        crawMusicListListener.onItemClick(beanMusic);

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

        adapter.update(beanMusicList);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }




    public static interface CrawMusicListListener{
        public void onItemClick(BeanMusic beanMusic);
    }

    @Override
    public void show() {
        try{
            super.show();
            alDismiss = false;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        alDismiss = true;
    }


}
