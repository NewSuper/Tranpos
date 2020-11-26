package com.newsuper.t.juejinbao.ui.song.entity;


import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.util.List;

public class LatestMusicListEntity {

    /**
     * code : 0
     * msg : success
     * data : [{"id":5425,"song_name":"形容","singer":"沈以诚","special_name":"初遇","image":"http://p2.music.126.net/1iLwRvMtUMYLZUNR-HQW7Q==/109951163957708692.jpg?param=130y130","lyric":"[00:00.000] 作曲 : 沈以诚\n[00:01.000] 作词 : 沈以诚\n[00:10.939]编曲：李星宇\n[00:15.939]\n[00:25.939]就像是那 灰色天空中的小雨\n[00:31.938]下下停停 不动声色淋湿土地\n[00:36.440]尽管总是阴晴不定\n[00:39.187]但偶尔也会闪出星星\n[00:42.437]这都是形容你的眼睛\n[00:49.188]就像是那 古老城堡里的油画\n[00:54.938]突然抬头 定格在黄昏的晚霞\n[00:59.686]远看一片苍苍蒹葭\n[01:02.687]近处抚摸软似棉花\n[01:05.438]这都是形容你的长发\n[01:11.688]原谅我不可自拔\n[01:13.688]可能不经意看你一眼\n[01:16.190]百米冲刺都会停下\n[01:18.189]只恨科技不够发达\n[01:20.189]逆着时光回去陪你从小长大\n[01:23.937]风里还没有细沙\n[01:28.937]不切实际的想法\n[01:34.937]\n[01:58.437]就像是那 错综复杂的小枝丫\n[02:04.438]过去未来 冥冥中对悠长宿命微妙地潜移默化\n[02:10.188]很细腻 不年轻\n[02:12.937]想轻轻把它抚平\n[02:15.938]这是形容你的手掌心\n[02:20.938]原谅我不可自拔\n[02:23.185]可能不经意看你一眼\n[02:25.437]心里石头都会落下\n[02:27.435]只恨科技不够发达\n[02:29.685]逆着时光回去陪你从小长大\n[02:33.438]风里还没有细沙\n[02:38.438]地球还没有老化\n[02:44.187]原谅我不可自拔\n[02:46.187]可能不经意看你一眼\n[02:48.686]心里石头都会落下\n[02:50.689]只恨科技不够发达\n[02:52.689]逆着时光回去陪你从小长大\n[02:56.439]风里还没有细沙\n[03:01.440]不切实际的想法\n[03:07.437]原谅我不可自拔\n[03:09.437]可能不经意看你一眼\n[03:11.937]百米冲刺都会停下\n[03:13.688]只恨科技不够发达\n[03:15.938]逆着时光回去陪你从小长大\n[03:19.437]风里还没有细沙\n[03:24.689]地球还没有老化\n[03:30.688]不切实际的想法\n[03:38.000]\n[03:38.388]企划：小粉\n[03:38.888]统筹：黄鲲\n[03:39.388]制作人：李星宇\n[03:39.888]监制：moonik蛛蛛\n[03:40.388]吉他：陈卉\n[03:40.888]鼓：刘星星\n[03:41.388]和声编写：李星宇\n[03:41.888]和声：沈以诚/李星宇\n[03:42.388]吉他录音：刘晶晶（TTL）\n[03:42.888]鼓录音：李马科（RC）\n[03:43.388]人声录音：大伟（上声）\n[03:43.888]混音/母带：李马科（RC）\n[03:44.000]封面设计：武中奇\n","song_url":"http://music.163.com/song/media/outer/url?id=1336856864.mp3","playlist_name":"热门新歌","play_count":266}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends EasyAdapter.TypeBean {
        /**
         * id : 5425
         * song_name : 形容
         * singer : 沈以诚
         * special_name : 初遇
         * image : http://p2.music.126.net/1iLwRvMtUMYLZUNR-HQW7Q==/109951163957708692.jpg?param=130y130
         * lyric : [00:00.000] 作曲 : 沈以诚
         [00:01.000] 作词 : 沈以诚
         [00:10.939]编曲：李星宇
         [00:15.939]
         [00:25.939]就像是那 灰色天空中的小雨
         [00:31.938]下下停停 不动声色淋湿土地
         [00:36.440]尽管总是阴晴不定
         [00:39.187]但偶尔也会闪出星星
         [00:42.437]这都是形容你的眼睛
         [00:49.188]就像是那 古老城堡里的油画
         [00:54.938]突然抬头 定格在黄昏的晚霞
         [00:59.686]远看一片苍苍蒹葭
         [01:02.687]近处抚摸软似棉花
         [01:05.438]这都是形容你的长发
         [01:11.688]原谅我不可自拔
         [01:13.688]可能不经意看你一眼
         [01:16.190]百米冲刺都会停下
         [01:18.189]只恨科技不够发达
         [01:20.189]逆着时光回去陪你从小长大
         [01:23.937]风里还没有细沙
         [01:28.937]不切实际的想法
         [01:34.937]
         [01:58.437]就像是那 错综复杂的小枝丫
         [02:04.438]过去未来 冥冥中对悠长宿命微妙地潜移默化
         [02:10.188]很细腻 不年轻
         [02:12.937]想轻轻把它抚平
         [02:15.938]这是形容你的手掌心
         [02:20.938]原谅我不可自拔
         [02:23.185]可能不经意看你一眼
         [02:25.437]心里石头都会落下
         [02:27.435]只恨科技不够发达
         [02:29.685]逆着时光回去陪你从小长大
         [02:33.438]风里还没有细沙
         [02:38.438]地球还没有老化
         [02:44.187]原谅我不可自拔
         [02:46.187]可能不经意看你一眼
         [02:48.686]心里石头都会落下
         [02:50.689]只恨科技不够发达
         [02:52.689]逆着时光回去陪你从小长大
         [02:56.439]风里还没有细沙
         [03:01.440]不切实际的想法
         [03:07.437]原谅我不可自拔
         [03:09.437]可能不经意看你一眼
         [03:11.937]百米冲刺都会停下
         [03:13.688]只恨科技不够发达
         [03:15.938]逆着时光回去陪你从小长大
         [03:19.437]风里还没有细沙
         [03:24.689]地球还没有老化
         [03:30.688]不切实际的想法
         [03:38.000]
         [03:38.388]企划：小粉
         [03:38.888]统筹：黄鲲
         [03:39.388]制作人：李星宇
         [03:39.888]监制：moonik蛛蛛
         [03:40.388]吉他：陈卉
         [03:40.888]鼓：刘星星
         [03:41.388]和声编写：李星宇
         [03:41.888]和声：沈以诚/李星宇
         [03:42.388]吉他录音：刘晶晶（TTL）
         [03:42.888]鼓录音：李马科（RC）
         [03:43.388]人声录音：大伟（上声）
         [03:43.888]混音/母带：李马科（RC）
         [03:44.000]封面设计：武中奇
         * song_url : http://music.163.com/song/media/outer/url?id=1336856864.mp3
         * playlist_name : 热门新歌
         * play_count : 266
         */

        private int id;
        private String song_name;
        private String singer;
        private String special_name;
        private String image;
        private String lyric;
        private String song_url;
        private String playlist_name;
        private int play_count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSong_name() {
            return song_name;
        }

        public void setSong_name(String song_name) {
            this.song_name = song_name;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getSpecial_name() {
            return special_name;
        }

        public void setSpecial_name(String special_name) {
            this.special_name = special_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLyric() {
            return lyric;
        }

        public void setLyric(String lyric) {
            this.lyric = lyric;
        }

        public String getSong_url() {
            return song_url;
        }

        public void setSong_url(String song_url) {
            this.song_url = song_url;
        }

        public String getPlaylist_name() {
            return playlist_name;
        }

        public void setPlaylist_name(String playlist_name) {
            this.playlist_name = playlist_name;
        }

        public int getPlay_count() {
            return play_count;
        }

        public void setPlay_count(int play_count) {
            this.play_count = play_count;
        }
    }
}
