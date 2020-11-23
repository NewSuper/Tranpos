package com.newsuper.t.juejinbao.ui.movie.entity;

import com.juejinchain.android.module.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class MovieIndexRecommendEntity {


    /**
     * code : 0
     * msg : success
     * data : {"hot_play":[{"alias":"tv_hot_all","cn":"综合","lists":[{"vod_id":"27174318","vod_title":"神探南茜","vod_subtitle":"2019 / 美国 / 犯罪 悬疑 惊悚 / 拉里·滕 / 肯尼迪·麦克曼 利·刘易斯","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567068632.webp","vod_score":"5.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"肯尼迪·麦克曼,利·刘易斯,滕吉·卡斯姆","vod_director":"拉里·滕"},{"vod_id":"30283263","vod_title":"职责/羞耻","vod_subtitle":"2019 / 英国 / 犯罪 / 朱利安·法里诺 / 平岳大 凯莉·麦克唐纳","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571927458.webp","vod_score":"7.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"平岳大,凯莉·麦克唐纳,洼冢洋介","vod_director":"朱利安·法里诺"},{"vod_id":"30400802","vod_title":"通灵少女 第二季","vod_subtitle":"2019 / 中国台湾 / 剧情 悬疑 奇幻 / 劉彥甫 / 郭书瑶 范少勋","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569796002.webp","vod_score":"6.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"郭书瑶,范少勋,温贞菱","vod_director":"劉彥甫"},{"vod_id":"34446034","vod_title":"TOP LEAGUE","vod_subtitle":"2019 / 日本 / 剧情 / 星野和成 中前勇儿 / 玉山铁二 池内博之","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567350249.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"玉山铁二,池内博之,佐久间由衣","vod_director":"星野和成"},{"vod_id":"34462015","vod_title":"红白黑黄 第七季","vod_subtitle":"2019 / 美国 / 剧情 动画 / 林赛·琼斯 卡拉·埃伯利","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2568040446.webp","vod_score":"9.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"林赛·琼斯,卡拉·埃伯利,艾伦·泽克","vod_director":""},{"vod_id":"34806269","vod_title":"选拔屋：猎头的做派","vod_subtitle":"2019 / 日本 / 剧情 / 西浦正记 / 松下奈绪 内田有纪","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571199317.webp","vod_score":"6.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"松下奈绪,内田有纪,小手伸也","vod_director":"西浦正记"}]},{"alias":"tv_hot_china","cn":"国产剧","lists":[{"vod_id":"34927318","vod_title":"生命缘·生命的礼物 第三季","vod_subtitle":"2019 / 中国大陆 / 纪录片","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2578471362.webp","vod_score":"8.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"30372333","vod_title":"恋恋江湖","vod_subtitle":"2019 / 中国大陆 / 喜剧 爱情 武侠 / 李梅 / 姜贞羽 杨仕泽","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571534456.webp","vod_score":"6.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"姜贞羽,杨仕泽,何宜谦","vod_director":"李梅"},{"vod_id":"30283301","vod_title":"过街英雄","vod_subtitle":"2020 / 中国香港 / 剧情 喜剧 爱情 / 吴冠宇 / 森美 黄翠如","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2576399273.webp","vod_score":"6.1","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"森美,黄翠如,江嘉敏","vod_director":"吴冠宇"},{"vod_id":"30249361","vod_title":"解决师","vod_subtitle":"2019 / 中国香港 / 剧情 爱情 悬疑 / 刘家豪 徐遇安 陈志江 / 王浩信 唐诗咏","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571692703.webp","vod_score":"6.1","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"王浩信,唐诗咏,陈敏之","vod_director":"刘家豪"},{"vod_id":"30136655","vod_title":"丫鬟大联盟","vod_subtitle":"2019 / 中国香港 / 古装 / 欧耀兴 / 洪永城 黄心颖","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579219164.webp","vod_score":"5.1","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"洪永城,黄心颖,汤洛雯","vod_director":"欧耀兴"},{"vod_id":"27199568","vod_title":"热爱","vod_subtitle":"2019 / 中国大陆 / 剧情 / 王小列 / 杨玏 啜妮","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575245813.webp","vod_score":"5.1","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"杨玏,啜妮,刘敏涛","vod_director":"王小列"}]},{"alias":"tv_hot_korea","cn":"韩剧","lists":[{"vod_id":"30352988","vod_title":"我的王国","vod_subtitle":"2019 / 韩国 / 剧情 历史 爱情 / 金镇元 / 梁世宗 禹棹焕","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569552075.webp","vod_score":"7.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"梁世宗,禹棹焕,金雪炫","vod_director":"金镇元"},{"vod_id":"34851291","vod_title":"没有第二次机会","vod_subtitle":"2019 / 韩国 / 剧情 / 崔元锡 / 尹汝贞 朱铉","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2572843143.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"尹汝贞,朱铉,韩振熙","vod_director":"崔元锡"},{"vod_id":"34851216","vod_title":"喜欢","vod_subtitle":"2019 / 韩国 / 爱情 / 김유안 金江珉","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570885804.webp","vod_score":"6.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"김유안,金江珉,유정우","vod_director":""},{"vod_id":"34930864","vod_title":"大数据恋爱","vod_subtitle":"2019 / 韩国 / 爱情 / 宋再临 全昭旻","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2578747844.webp","vod_score":"6.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"宋再临,全昭旻","vod_director":""},{"vod_id":"30255073","vod_title":"绿豆传","vod_subtitle":"2019 / 韩国 / 喜剧 爱情 / 金东辉 / 金所泫 张东润","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569130817.webp","vod_score":"7.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"金所泫,张东润,姜泰伍","vod_director":"金东辉"},{"vod_id":"34908201","vod_title":"延南洞吻神","vod_subtitle":"2019 / 韩国 / 喜剧 爱情 悬疑 / 洪承熙 金冠洙","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2576098572.webp","vod_score":"8.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"洪承熙,金冠洙,裴仁赫","vod_director":""}]},{"alias":"tv_hot_janpan","cn":"日剧","lists":[{"vod_id":"30304031","vod_title":"同一屋檐下的四个女人","vod_subtitle":"2019 / 日本 / 剧情 / 深川荣洋 / 中谷美纪 永作博美","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570082426.webp","vod_score":"6.4","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"中谷美纪,永作博美,吉冈里帆","vod_director":"深川荣洋"},{"vod_id":"34692196","vod_title":"晴～综合商社之女～","vod_subtitle":"2019 / 日本 / 土方政人 都筑淳一 三木茂 / 中谷美纪 藤木直人","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571011980.webp","vod_score":"5.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"中谷美纪,藤木直人,寺田心","vod_director":"土方政人"},{"vod_id":"34463430","vod_title":"真心的符号","vod_subtitle":"2019 / 日本 / 深田晃司 / 森崎温 土村芳","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579925427.webp","vod_score":"6.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"森崎温,土村芳,宇野祥平","vod_director":"深田晃司"},{"vod_id":"34446034","vod_title":"TOP LEAGUE","vod_subtitle":"2019 / 日本 / 剧情 / 星野和成 中前勇儿 / 玉山铁二 池内博之","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567350249.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"玉山铁二,池内博之,佐久间由衣","vod_director":"星野和成"},{"vod_id":"34822485","vod_title":"左撇子艾伦","vod_subtitle":"2019 / 日本 / 後藤庸介 / 神尾枫珠 池田依来沙","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570266915.webp","vod_score":"6.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"神尾枫珠,池田依来沙,石崎Huwie","vod_director":"後藤庸介"},{"vod_id":"33464535","vod_title":"令和元年版 怪谈牡丹灯笼","vod_subtitle":"2019 / 日本 / 奇幻 恐怖 / 源孝志 / 尾野真千子 柄本佑","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569234427.webp","vod_score":"7.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"尾野真千子,柄本佑,若叶龙也","vod_director":"源孝志"}]},{"alias":"tv_hot_enus","cn":"英美剧","lists":[{"vod_id":"24753762","vod_title":"敢不敢挑战我","vod_subtitle":"2019 / 美国 / 惊悚 / 斯戴芙·格林 / 薇拉·菲茨杰拉德 扎齐·罗伊瑞格","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575916154.webp","vod_score":"0.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"薇拉·菲茨杰拉德,扎齐·罗伊瑞格,坦米·布兰查德","vod_director":"斯戴芙·格林"},{"vod_id":"30237248","vod_title":"狄金森 第一季","vod_subtitle":"2019 / 美国 / 剧情 喜剧 传记 / 史黛西·帕松 大卫·戈登·格林 西拉斯·霍华德 琳·谢尔顿 帕特里克·R·诺里斯 / 海莉·斯坦菲尔德 安娜·巴瑞辛尼科夫","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569210674.webp","vod_score":"8.1","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"海莉·斯坦菲尔德,安娜·巴瑞辛尼科夫,艾拉·亨特","vod_director":"史黛西·帕松"},{"vod_id":"30280217","vod_title":"高堡奇人 第四季","vod_subtitle":"2019 / 美国 / 剧情 科幻 惊悚 / 丹尼尔·珀西瓦尔 约翰·福西特 / 艾莉克莎·黛瓦洛斯 乔尔·德拉富恩特","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2572140417.webp","vod_score":"7.4","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"艾莉克莎·黛瓦洛斯,乔尔·德拉富恩特,卢夫斯·塞维尔","vod_director":"丹尼尔·珀西瓦尔"},{"vod_id":"30428027","vod_title":"犯罪心理 第十五季","vod_subtitle":"2020 / 美国 / 剧情 犯罪 悬疑 / 乔·曼特纳 马修·格雷·古柏勒","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2560011498.webp","vod_score":"0.0","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"乔·曼特纳,马修·格雷·古柏勒,A·J·库克","vod_director":""},{"vod_id":"27163289","vod_title":"克莉丝汀·基勒的审判","vod_subtitle":"2019 / 英国 / 剧情 / 安德丽亚·哈金 / 索菲·库克森 詹姆斯·诺顿","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2580095727.webp","vod_score":"7.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"索菲·库克森,詹姆斯·诺顿,艾丽·巴姆博","vod_director":"安德丽亚·哈金"},{"vod_id":"30450247","vod_title":"邪恶力量 第十五季","vod_subtitle":"2019 / 美国 / 剧情 奇幻 惊悚 / 约翰·F·修华特 / 贾德·帕达里克 詹森·阿克斯","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2564856425.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"贾德·帕达里克,詹森·阿克斯,米沙·克林斯","vod_director":"约翰·F·修华特"}]}],"rank":[{"alias":"tv_real_time_hotest","title":"实时热门电视","lists":[{"vod_id":"33418361","vod_title":"但是还有书籍","vod_subtitle":"2019 / 中国大陆 / 纪录片 / 罗颖鸾 杨骊珠 王悦阳 郑苏杭 刘倩瑜 彭欣宇 林宸西 / 朱岳 俞国林","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2577256090.webp","vod_score":"9.4","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"34906701","vod_title":"下辈子我再好好过","vod_subtitle":"2020 / 日本 / 喜剧 爱情 / 三木康一郎 / 内田理央 小关裕太","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579743163.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"33438250","vod_title":"别对映像研出手！","vod_subtitle":"2020 / 日本 / 动画 / 汤浅政明 / 伊藤沙莉 田村睦心","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575204199.webp","vod_score":"9.7","vod_category":"tv","vod_year":"2020","vod_district":"日本","vod_star":"5.0","vod_tag":"动画","vod_actor":"","vod_director":""},{"vod_id":"27625457","vod_title":"去他*的世界 第二季","vod_subtitle":"2019 / 英国 / 剧情 喜剧 爱情 / 戴斯特尼·埃卡拉加 露西·福布斯 / 杰西卡·巴登 埃里克斯·劳瑟","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570938064.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""}]},{"alias":"tv_chinese_best_weekly","title":"华语口碑剧集","lists":[{"vod_id":"25853071","vod_title":"庆余年","vod_subtitle":"2019 / 中国大陆 / 剧情 古装 / 孙皓 / 张若昀 李沁","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575362797.webp","vod_score":"7.9","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"26903977","vod_title":"镇魂街 第二季","vod_subtitle":"2019 / 中国大陆 / 剧情 动作 动画 / 白海涛 / 图特哈蒙 黎筱濛","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2574775186.webp","vod_score":"8.1","vod_category":"tv","vod_year":"2019","vod_district":"中国大陆","vod_star":"4.0","vod_tag":"剧情,动作,动画","vod_actor":"","vod_director":""},{"vod_id":"27107123","vod_title":"锦衣之下","vod_subtitle":"2019 / 中国大陆 / 爱情 悬疑 古装 / 尹涛 / 任嘉伦 谭松韵","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579081159.webp","vod_score":"7.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"30168034","vod_title":"输不起","vod_subtitle":"2020 / 中国大陆 / 剧情 / 邢冬冬 / 邵庄 李逸男","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2578597672.webp","vod_score":"7.4","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"邵庄,李逸男,高煜霏","vod_director":"邢冬冬"}]},{"alias":"tv_global_best_weekly","title":"全球口碑剧集榜","lists":[{"vod_id":"30317887","vod_title":"魔法纪录：魔法少女小圆外传","vod_subtitle":"2020 / 日本 / 剧情 动作 动画 / 剧团狗咖喱（泥犬） 宫本幸裕 / 悠木碧 麻仓桃","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2568212091.webp","vod_score":"9.0","vod_category":"cartoon","vod_year":"2020","vod_district":"日本","vod_star":"4.5","vod_tag":"剧情,动作,动画","vod_actor":"悠木碧,麻仓桃,花泽香菜,加藤英美里,宫下早纪,陶山恵実里,鸡冠井美智子,夏川椎菜,雨宫天,佐仓绫音,小仓唯,小松未可子,大桥彩香,石原夏织","vod_director":"剧团狗咖喱（泥犬）,宫本幸裕"},{"vod_id":"30344167","vod_title":"曼达洛人 第一季","vod_subtitle":"2019 / 美国 / 科幻 奇幻 / 黛博拉·周 瑞克·法穆易瓦 戴夫·菲洛尼 布莱丝·达拉斯·霍华德 塔伊加·维迪提 / 佩德罗·帕斯卡 Kyle Pacek","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2566627804.webp","vod_score":"9.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"30458442","vod_title":"动物狂想曲","vod_subtitle":"2019 / 日本 / 剧情 动画 悬疑 / 松见真一 / 小林亲弘 千本木彩花","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2562089872.webp","vod_score":"9.2","vod_category":"cartoon","vod_year":"2019","vod_district":"日本","vod_star":"4.5","vod_tag":"剧情,动画,悬疑","vod_actor":"小林亲弘,千本木彩花,小野友树,种崎敦美,榎木淳弥,内田雄马,大冢刚央,小林直人,下妻由幸,冈本信彦","vod_director":"松见真一"},{"vod_id":"33379430","vod_title":"齐木楠雄的灾难 Ψ始动篇","vod_subtitle":"2019 / 日本 / 喜剧 动画 / 樱井弘明 / 神谷浩史 小野大辅","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2574157911.webp","vod_score":"9.2","vod_category":"cartoon","vod_year":"2019","vod_district":"日本","vod_star":"4.5","vod_tag":"喜剧,动画","vod_actor":"神谷浩史,小野大辅,岛崎信长,日野聪,花江夏树,茅野爱衣,鸟海浩辅,东山奈央","vod_director":"樱井弘明"}]}],"recommend":{"total":6,"per_page":15,"current_page":1,"last_page":1,"data":[{"vod_id":"30371751","vod_title":"伍六七之最强发型师","vod_subtitle":"2019 / 中国大陆 / 动画 / 何小疯 / 何小疯 姜广涛","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571634144.webp","vod_actor":"","vod_director":"","vod_tag":"动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-23","vod_district":"中国大陆","vod_hot_num":"17907.00","vod_score":"9.0","vod_star":"4.5"},{"vod_id":"34845689","vod_title":"剑网3·侠肝义胆沈剑心 第二季","vod_subtitle":"2019 / 中国大陆 / 剧情 喜剧 动画 / 周沬 王淼 / 宝木中阳 藤新","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570041713.webp","vod_actor":"宝木中阳,藤新,杨默","vod_director":"周沬","vod_tag":"剧情,喜剧,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-27","vod_district":"中国大陆","vod_hot_num":"497.00","vod_score":"8.9","vod_star":"4.5"},{"vod_id":"26903977","vod_title":"镇魂街 第二季","vod_subtitle":"2019 / 中国大陆 / 剧情 动作 动画 / 白海涛 / 图特哈蒙 黎筱濛","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2574775186.webp","vod_actor":"","vod_director":"","vod_tag":"剧情,动作,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-28","vod_district":"中国大陆","vod_hot_num":"2589.00","vod_score":"8.1","vod_star":"4.0"},{"vod_id":"34896134","vod_title":"今天开始做明星","vod_subtitle":"2019 / 中国大陆 / 喜剧 动画 / 于沺 / 吟良犬 马正阳","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575446558.webp","vod_actor":"吟良犬,马正阳,藤新","vod_director":"于沺","vod_tag":"喜剧,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-27","vod_district":"中国大陆","vod_hot_num":"253.00","vod_score":"7.4","vod_star":"3.5"},{"vod_id":"34845506","vod_title":"那年那兔那些事儿 第五季","vod_subtitle":"2019 / 中国大陆 / 历史 动画 / 王强 / 小连杀 叮当","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570226970.webp","vod_actor":"小连杀,叮当","vod_director":"王强","vod_tag":"历史,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-01","vod_district":"中国大陆","vod_hot_num":"511.00","vod_score":"6.8","vod_star":"3.5"},{"vod_id":"30453093","vod_title":"厨神小当家","vod_subtitle":"2019 / 日本 中国大陆 / 剧情 动画 / 川崎逸朗 / 藤原夏海 茅野爱衣","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2554618756.webp","vod_actor":"藤原夏海,茅野爱衣,藤井雪代","vod_director":"川崎逸朗","vod_tag":"剧情,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-12","vod_district":"中国大陆","vod_hot_num":"2016.00","vod_score":"4.7","vod_star":"2.5"}]}}
     * time : 1578540064
     * vsn : 1.8.8.3
     */

    private int code;
    private String msg;
    private DataBeanX data;
    private int time;
    private String vsn;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }

    public static class DataBeanX {
        /**
         * hot_play : [{"alias":"tv_hot_all","cn":"综合","lists":[{"vod_id":"27174318","vod_title":"神探南茜","vod_subtitle":"2019 / 美国 / 犯罪 悬疑 惊悚 / 拉里·滕 / 肯尼迪·麦克曼 利·刘易斯","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567068632.webp","vod_score":"5.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"肯尼迪·麦克曼,利·刘易斯,滕吉·卡斯姆","vod_director":"拉里·滕"},{"vod_id":"30283263","vod_title":"职责/羞耻","vod_subtitle":"2019 / 英国 / 犯罪 / 朱利安·法里诺 / 平岳大 凯莉·麦克唐纳","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571927458.webp","vod_score":"7.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"平岳大,凯莉·麦克唐纳,洼冢洋介","vod_director":"朱利安·法里诺"},{"vod_id":"30400802","vod_title":"通灵少女 第二季","vod_subtitle":"2019 / 中国台湾 / 剧情 悬疑 奇幻 / 劉彥甫 / 郭书瑶 范少勋","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569796002.webp","vod_score":"6.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"郭书瑶,范少勋,温贞菱","vod_director":"劉彥甫"},{"vod_id":"34446034","vod_title":"TOP LEAGUE","vod_subtitle":"2019 / 日本 / 剧情 / 星野和成 中前勇儿 / 玉山铁二 池内博之","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567350249.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"玉山铁二,池内博之,佐久间由衣","vod_director":"星野和成"},{"vod_id":"34462015","vod_title":"红白黑黄 第七季","vod_subtitle":"2019 / 美国 / 剧情 动画 / 林赛·琼斯 卡拉·埃伯利","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2568040446.webp","vod_score":"9.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"林赛·琼斯,卡拉·埃伯利,艾伦·泽克","vod_director":""},{"vod_id":"34806269","vod_title":"选拔屋：猎头的做派","vod_subtitle":"2019 / 日本 / 剧情 / 西浦正记 / 松下奈绪 内田有纪","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571199317.webp","vod_score":"6.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"松下奈绪,内田有纪,小手伸也","vod_director":"西浦正记"}]},{"alias":"tv_hot_china","cn":"国产剧","lists":[{"vod_id":"34927318","vod_title":"生命缘·生命的礼物 第三季","vod_subtitle":"2019 / 中国大陆 / 纪录片","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2578471362.webp","vod_score":"8.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"30372333","vod_title":"恋恋江湖","vod_subtitle":"2019 / 中国大陆 / 喜剧 爱情 武侠 / 李梅 / 姜贞羽 杨仕泽","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571534456.webp","vod_score":"6.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"姜贞羽,杨仕泽,何宜谦","vod_director":"李梅"},{"vod_id":"30283301","vod_title":"过街英雄","vod_subtitle":"2020 / 中国香港 / 剧情 喜剧 爱情 / 吴冠宇 / 森美 黄翠如","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2576399273.webp","vod_score":"6.1","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"森美,黄翠如,江嘉敏","vod_director":"吴冠宇"},{"vod_id":"30249361","vod_title":"解决师","vod_subtitle":"2019 / 中国香港 / 剧情 爱情 悬疑 / 刘家豪 徐遇安 陈志江 / 王浩信 唐诗咏","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571692703.webp","vod_score":"6.1","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"王浩信,唐诗咏,陈敏之","vod_director":"刘家豪"},{"vod_id":"30136655","vod_title":"丫鬟大联盟","vod_subtitle":"2019 / 中国香港 / 古装 / 欧耀兴 / 洪永城 黄心颖","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579219164.webp","vod_score":"5.1","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"洪永城,黄心颖,汤洛雯","vod_director":"欧耀兴"},{"vod_id":"27199568","vod_title":"热爱","vod_subtitle":"2019 / 中国大陆 / 剧情 / 王小列 / 杨玏 啜妮","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575245813.webp","vod_score":"5.1","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"杨玏,啜妮,刘敏涛","vod_director":"王小列"}]},{"alias":"tv_hot_korea","cn":"韩剧","lists":[{"vod_id":"30352988","vod_title":"我的王国","vod_subtitle":"2019 / 韩国 / 剧情 历史 爱情 / 金镇元 / 梁世宗 禹棹焕","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569552075.webp","vod_score":"7.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"梁世宗,禹棹焕,金雪炫","vod_director":"金镇元"},{"vod_id":"34851291","vod_title":"没有第二次机会","vod_subtitle":"2019 / 韩国 / 剧情 / 崔元锡 / 尹汝贞 朱铉","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2572843143.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"尹汝贞,朱铉,韩振熙","vod_director":"崔元锡"},{"vod_id":"34851216","vod_title":"喜欢","vod_subtitle":"2019 / 韩国 / 爱情 / 김유안 金江珉","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570885804.webp","vod_score":"6.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"김유안,金江珉,유정우","vod_director":""},{"vod_id":"34930864","vod_title":"大数据恋爱","vod_subtitle":"2019 / 韩国 / 爱情 / 宋再临 全昭旻","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2578747844.webp","vod_score":"6.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"宋再临,全昭旻","vod_director":""},{"vod_id":"30255073","vod_title":"绿豆传","vod_subtitle":"2019 / 韩国 / 喜剧 爱情 / 金东辉 / 金所泫 张东润","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569130817.webp","vod_score":"7.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"金所泫,张东润,姜泰伍","vod_director":"金东辉"},{"vod_id":"34908201","vod_title":"延南洞吻神","vod_subtitle":"2019 / 韩国 / 喜剧 爱情 悬疑 / 洪承熙 金冠洙","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2576098572.webp","vod_score":"8.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"洪承熙,金冠洙,裴仁赫","vod_director":""}]},{"alias":"tv_hot_janpan","cn":"日剧","lists":[{"vod_id":"30304031","vod_title":"同一屋檐下的四个女人","vod_subtitle":"2019 / 日本 / 剧情 / 深川荣洋 / 中谷美纪 永作博美","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570082426.webp","vod_score":"6.4","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"中谷美纪,永作博美,吉冈里帆","vod_director":"深川荣洋"},{"vod_id":"34692196","vod_title":"晴～综合商社之女～","vod_subtitle":"2019 / 日本 / 土方政人 都筑淳一 三木茂 / 中谷美纪 藤木直人","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571011980.webp","vod_score":"5.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"中谷美纪,藤木直人,寺田心","vod_director":"土方政人"},{"vod_id":"34463430","vod_title":"真心的符号","vod_subtitle":"2019 / 日本 / 深田晃司 / 森崎温 土村芳","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579925427.webp","vod_score":"6.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"森崎温,土村芳,宇野祥平","vod_director":"深田晃司"},{"vod_id":"34446034","vod_title":"TOP LEAGUE","vod_subtitle":"2019 / 日本 / 剧情 / 星野和成 中前勇儿 / 玉山铁二 池内博之","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567350249.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"玉山铁二,池内博之,佐久间由衣","vod_director":"星野和成"},{"vod_id":"34822485","vod_title":"左撇子艾伦","vod_subtitle":"2019 / 日本 / 後藤庸介 / 神尾枫珠 池田依来沙","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570266915.webp","vod_score":"6.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"神尾枫珠,池田依来沙,石崎Huwie","vod_director":"後藤庸介"},{"vod_id":"33464535","vod_title":"令和元年版 怪谈牡丹灯笼","vod_subtitle":"2019 / 日本 / 奇幻 恐怖 / 源孝志 / 尾野真千子 柄本佑","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569234427.webp","vod_score":"7.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"尾野真千子,柄本佑,若叶龙也","vod_director":"源孝志"}]},{"alias":"tv_hot_enus","cn":"英美剧","lists":[{"vod_id":"24753762","vod_title":"敢不敢挑战我","vod_subtitle":"2019 / 美国 / 惊悚 / 斯戴芙·格林 / 薇拉·菲茨杰拉德 扎齐·罗伊瑞格","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575916154.webp","vod_score":"0.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"薇拉·菲茨杰拉德,扎齐·罗伊瑞格,坦米·布兰查德","vod_director":"斯戴芙·格林"},{"vod_id":"30237248","vod_title":"狄金森 第一季","vod_subtitle":"2019 / 美国 / 剧情 喜剧 传记 / 史黛西·帕松 大卫·戈登·格林 西拉斯·霍华德 琳·谢尔顿 帕特里克·R·诺里斯 / 海莉·斯坦菲尔德 安娜·巴瑞辛尼科夫","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569210674.webp","vod_score":"8.1","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"海莉·斯坦菲尔德,安娜·巴瑞辛尼科夫,艾拉·亨特","vod_director":"史黛西·帕松"},{"vod_id":"30280217","vod_title":"高堡奇人 第四季","vod_subtitle":"2019 / 美国 / 剧情 科幻 惊悚 / 丹尼尔·珀西瓦尔 约翰·福西特 / 艾莉克莎·黛瓦洛斯 乔尔·德拉富恩特","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2572140417.webp","vod_score":"7.4","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"艾莉克莎·黛瓦洛斯,乔尔·德拉富恩特,卢夫斯·塞维尔","vod_director":"丹尼尔·珀西瓦尔"},{"vod_id":"30428027","vod_title":"犯罪心理 第十五季","vod_subtitle":"2020 / 美国 / 剧情 犯罪 悬疑 / 乔·曼特纳 马修·格雷·古柏勒","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2560011498.webp","vod_score":"0.0","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"乔·曼特纳,马修·格雷·古柏勒,A·J·库克","vod_director":""},{"vod_id":"27163289","vod_title":"克莉丝汀·基勒的审判","vod_subtitle":"2019 / 英国 / 剧情 / 安德丽亚·哈金 / 索菲·库克森 詹姆斯·诺顿","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2580095727.webp","vod_score":"7.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"索菲·库克森,詹姆斯·诺顿,艾丽·巴姆博","vod_director":"安德丽亚·哈金"},{"vod_id":"30450247","vod_title":"邪恶力量 第十五季","vod_subtitle":"2019 / 美国 / 剧情 奇幻 惊悚 / 约翰·F·修华特 / 贾德·帕达里克 詹森·阿克斯","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2564856425.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"贾德·帕达里克,詹森·阿克斯,米沙·克林斯","vod_director":"约翰·F·修华特"}]}]
         * rank : [{"alias":"tv_real_time_hotest","title":"实时热门电视","lists":[{"vod_id":"33418361","vod_title":"但是还有书籍","vod_subtitle":"2019 / 中国大陆 / 纪录片 / 罗颖鸾 杨骊珠 王悦阳 郑苏杭 刘倩瑜 彭欣宇 林宸西 / 朱岳 俞国林","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2577256090.webp","vod_score":"9.4","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"34906701","vod_title":"下辈子我再好好过","vod_subtitle":"2020 / 日本 / 喜剧 爱情 / 三木康一郎 / 内田理央 小关裕太","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579743163.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"33438250","vod_title":"别对映像研出手！","vod_subtitle":"2020 / 日本 / 动画 / 汤浅政明 / 伊藤沙莉 田村睦心","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575204199.webp","vod_score":"9.7","vod_category":"tv","vod_year":"2020","vod_district":"日本","vod_star":"5.0","vod_tag":"动画","vod_actor":"","vod_director":""},{"vod_id":"27625457","vod_title":"去他*的世界 第二季","vod_subtitle":"2019 / 英国 / 剧情 喜剧 爱情 / 戴斯特尼·埃卡拉加 露西·福布斯 / 杰西卡·巴登 埃里克斯·劳瑟","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570938064.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""}]},{"alias":"tv_chinese_best_weekly","title":"华语口碑剧集","lists":[{"vod_id":"25853071","vod_title":"庆余年","vod_subtitle":"2019 / 中国大陆 / 剧情 古装 / 孙皓 / 张若昀 李沁","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575362797.webp","vod_score":"7.9","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"26903977","vod_title":"镇魂街 第二季","vod_subtitle":"2019 / 中国大陆 / 剧情 动作 动画 / 白海涛 / 图特哈蒙 黎筱濛","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2574775186.webp","vod_score":"8.1","vod_category":"tv","vod_year":"2019","vod_district":"中国大陆","vod_star":"4.0","vod_tag":"剧情,动作,动画","vod_actor":"","vod_director":""},{"vod_id":"27107123","vod_title":"锦衣之下","vod_subtitle":"2019 / 中国大陆 / 爱情 悬疑 古装 / 尹涛 / 任嘉伦 谭松韵","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579081159.webp","vod_score":"7.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"30168034","vod_title":"输不起","vod_subtitle":"2020 / 中国大陆 / 剧情 / 邢冬冬 / 邵庄 李逸男","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2578597672.webp","vod_score":"7.4","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"邵庄,李逸男,高煜霏","vod_director":"邢冬冬"}]},{"alias":"tv_global_best_weekly","title":"全球口碑剧集榜","lists":[{"vod_id":"30317887","vod_title":"魔法纪录：魔法少女小圆外传","vod_subtitle":"2020 / 日本 / 剧情 动作 动画 / 剧团狗咖喱（泥犬） 宫本幸裕 / 悠木碧 麻仓桃","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2568212091.webp","vod_score":"9.0","vod_category":"cartoon","vod_year":"2020","vod_district":"日本","vod_star":"4.5","vod_tag":"剧情,动作,动画","vod_actor":"悠木碧,麻仓桃,花泽香菜,加藤英美里,宫下早纪,陶山恵実里,鸡冠井美智子,夏川椎菜,雨宫天,佐仓绫音,小仓唯,小松未可子,大桥彩香,石原夏织","vod_director":"剧团狗咖喱（泥犬）,宫本幸裕"},{"vod_id":"30344167","vod_title":"曼达洛人 第一季","vod_subtitle":"2019 / 美国 / 科幻 奇幻 / 黛博拉·周 瑞克·法穆易瓦 戴夫·菲洛尼 布莱丝·达拉斯·霍华德 塔伊加·维迪提 / 佩德罗·帕斯卡 Kyle Pacek","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2566627804.webp","vod_score":"9.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"30458442","vod_title":"动物狂想曲","vod_subtitle":"2019 / 日本 / 剧情 动画 悬疑 / 松见真一 / 小林亲弘 千本木彩花","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2562089872.webp","vod_score":"9.2","vod_category":"cartoon","vod_year":"2019","vod_district":"日本","vod_star":"4.5","vod_tag":"剧情,动画,悬疑","vod_actor":"小林亲弘,千本木彩花,小野友树,种崎敦美,榎木淳弥,内田雄马,大冢刚央,小林直人,下妻由幸,冈本信彦","vod_director":"松见真一"},{"vod_id":"33379430","vod_title":"齐木楠雄的灾难 Ψ始动篇","vod_subtitle":"2019 / 日本 / 喜剧 动画 / 樱井弘明 / 神谷浩史 小野大辅","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2574157911.webp","vod_score":"9.2","vod_category":"cartoon","vod_year":"2019","vod_district":"日本","vod_star":"4.5","vod_tag":"喜剧,动画","vod_actor":"神谷浩史,小野大辅,岛崎信长,日野聪,花江夏树,茅野爱衣,鸟海浩辅,东山奈央","vod_director":"樱井弘明"}]}]
         * recommend : {"total":6,"per_page":15,"current_page":1,"last_page":1,"data":[{"vod_id":"30371751","vod_title":"伍六七之最强发型师","vod_subtitle":"2019 / 中国大陆 / 动画 / 何小疯 / 何小疯 姜广涛","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571634144.webp","vod_actor":"","vod_director":"","vod_tag":"动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-23","vod_district":"中国大陆","vod_hot_num":"17907.00","vod_score":"9.0","vod_star":"4.5"},{"vod_id":"34845689","vod_title":"剑网3·侠肝义胆沈剑心 第二季","vod_subtitle":"2019 / 中国大陆 / 剧情 喜剧 动画 / 周沬 王淼 / 宝木中阳 藤新","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570041713.webp","vod_actor":"宝木中阳,藤新,杨默","vod_director":"周沬","vod_tag":"剧情,喜剧,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-27","vod_district":"中国大陆","vod_hot_num":"497.00","vod_score":"8.9","vod_star":"4.5"},{"vod_id":"26903977","vod_title":"镇魂街 第二季","vod_subtitle":"2019 / 中国大陆 / 剧情 动作 动画 / 白海涛 / 图特哈蒙 黎筱濛","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2574775186.webp","vod_actor":"","vod_director":"","vod_tag":"剧情,动作,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-28","vod_district":"中国大陆","vod_hot_num":"2589.00","vod_score":"8.1","vod_star":"4.0"},{"vod_id":"34896134","vod_title":"今天开始做明星","vod_subtitle":"2019 / 中国大陆 / 喜剧 动画 / 于沺 / 吟良犬 马正阳","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575446558.webp","vod_actor":"吟良犬,马正阳,藤新","vod_director":"于沺","vod_tag":"喜剧,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-27","vod_district":"中国大陆","vod_hot_num":"253.00","vod_score":"7.4","vod_star":"3.5"},{"vod_id":"34845506","vod_title":"那年那兔那些事儿 第五季","vod_subtitle":"2019 / 中国大陆 / 历史 动画 / 王强 / 小连杀 叮当","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570226970.webp","vod_actor":"小连杀,叮当","vod_director":"王强","vod_tag":"历史,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-01","vod_district":"中国大陆","vod_hot_num":"511.00","vod_score":"6.8","vod_star":"3.5"},{"vod_id":"30453093","vod_title":"厨神小当家","vod_subtitle":"2019 / 日本 中国大陆 / 剧情 动画 / 川崎逸朗 / 藤原夏海 茅野爱衣","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2554618756.webp","vod_actor":"藤原夏海,茅野爱衣,藤井雪代","vod_director":"川崎逸朗","vod_tag":"剧情,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-12","vod_district":"中国大陆","vod_hot_num":"2016.00","vod_score":"4.7","vod_star":"2.5"}]}
         */

        private RecommendBean recommend;
        private List<HotPlayBean> hot_play;
        private List<RankBean> rank;

        public RecommendBean getRecommend() {
            return recommend;
        }

        public void setRecommend(RecommendBean recommend) {
            this.recommend = recommend;
        }

        public List<HotPlayBean> getHot_play() {
            return hot_play;
        }

        public void setHot_play(List<HotPlayBean> hot_play) {
            this.hot_play = hot_play;
        }

        public List<RankBean> getRank() {
            return rank;
        }

        public void setRank(List<RankBean> rank) {
            this.rank = rank;
        }

        public static class RecommendBean implements Serializable{
            /**
             * total : 6
             * per_page : 15
             * current_page : 1
             * last_page : 1
             * data : [{"vod_id":"30371751","vod_title":"伍六七之最强发型师","vod_subtitle":"2019 / 中国大陆 / 动画 / 何小疯 / 何小疯 姜广涛","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571634144.webp","vod_actor":"","vod_director":"","vod_tag":"动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-23","vod_district":"中国大陆","vod_hot_num":"17907.00","vod_score":"9.0","vod_star":"4.5"},{"vod_id":"34845689","vod_title":"剑网3·侠肝义胆沈剑心 第二季","vod_subtitle":"2019 / 中国大陆 / 剧情 喜剧 动画 / 周沬 王淼 / 宝木中阳 藤新","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570041713.webp","vod_actor":"宝木中阳,藤新,杨默","vod_director":"周沬","vod_tag":"剧情,喜剧,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-27","vod_district":"中国大陆","vod_hot_num":"497.00","vod_score":"8.9","vod_star":"4.5"},{"vod_id":"26903977","vod_title":"镇魂街 第二季","vod_subtitle":"2019 / 中国大陆 / 剧情 动作 动画 / 白海涛 / 图特哈蒙 黎筱濛","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2574775186.webp","vod_actor":"","vod_director":"","vod_tag":"剧情,动作,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-28","vod_district":"中国大陆","vod_hot_num":"2589.00","vod_score":"8.1","vod_star":"4.0"},{"vod_id":"34896134","vod_title":"今天开始做明星","vod_subtitle":"2019 / 中国大陆 / 喜剧 动画 / 于沺 / 吟良犬 马正阳","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575446558.webp","vod_actor":"吟良犬,马正阳,藤新","vod_director":"于沺","vod_tag":"喜剧,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-12-27","vod_district":"中国大陆","vod_hot_num":"253.00","vod_score":"7.4","vod_star":"3.5"},{"vod_id":"34845506","vod_title":"那年那兔那些事儿 第五季","vod_subtitle":"2019 / 中国大陆 / 历史 动画 / 王强 / 小连杀 叮当","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570226970.webp","vod_actor":"小连杀,叮当","vod_director":"王强","vod_tag":"历史,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-01","vod_district":"中国大陆","vod_hot_num":"511.00","vod_score":"6.8","vod_star":"3.5"},{"vod_id":"30453093","vod_title":"厨神小当家","vod_subtitle":"2019 / 日本 中国大陆 / 剧情 动画 / 川崎逸朗 / 藤原夏海 茅野爱衣","vod_category":"tv","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2554618756.webp","vod_actor":"藤原夏海,茅野爱衣,藤井雪代","vod_director":"川崎逸朗","vod_tag":"剧情,动画","vod_intro":"","vod_hot_comment":"","vod_year":"2019","vod_pub_date":"2019-10-12","vod_district":"中国大陆","vod_hot_num":"2016.00","vod_score":"4.7","vod_star":"2.5"}]
             */

            private int total;
            private int per_page;
            private int current_page;
            private int last_page;
            private List<DataBean> data;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPer_page() {
                return per_page;
            }

            public void setPer_page(int per_page) {
                this.per_page = per_page;
            }

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getLast_page() {
                return last_page;
            }

            public void setLast_page(int last_page) {
                this.last_page = last_page;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean extends EasyAdapter.TypeBean implements Serializable{
                /**
                 * vod_id : 30371751
                 * vod_title : 伍六七之最强发型师
                 * vod_subtitle : 2019 / 中国大陆 / 动画 / 何小疯 / 何小疯 姜广涛
                 * vod_category : tv
                 * vod_pic : https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571634144.webp
                 * vod_actor :
                 * vod_director :
                 * vod_tag : 动画
                 * vod_intro :
                 * vod_hot_comment :
                 * vod_year : 2019
                 * vod_pub_date : 2019-10-23
                 * vod_district : 中国大陆
                 * vod_hot_num : 17907.00
                 * vod_score : 9.0
                 * vod_star : 4.5
                 */

                private String vod_id;
                private String vod_title;
                private String vod_subtitle;
                private String vod_category;
                private String vod_pic;
                private String vod_actor;
                private String vod_director;
                private String vod_tag;
                private String vod_intro;
                private String vod_hot_comment;
                private String vod_year;
                private String vod_pub_date;
                private String vod_district;
                private String vod_hot_num;
                private String vod_score;
                private String vod_star;

                public String getVod_id() {
                    return vod_id;
                }

                public void setVod_id(String vod_id) {
                    this.vod_id = vod_id;
                }

                public String getVod_title() {
                    return vod_title;
                }

                public void setVod_title(String vod_title) {
                    this.vod_title = vod_title;
                }

                public String getVod_subtitle() {
                    return vod_subtitle;
                }

                public void setVod_subtitle(String vod_subtitle) {
                    this.vod_subtitle = vod_subtitle;
                }

                public String getVod_category() {
                    return vod_category;
                }

                public void setVod_category(String vod_category) {
                    this.vod_category = vod_category;
                }

                public String getVod_pic() {
                    return vod_pic;
                }

                public void setVod_pic(String vod_pic) {
                    this.vod_pic = vod_pic;
                }

                public String getVod_actor() {
                    return vod_actor;
                }

                public void setVod_actor(String vod_actor) {
                    this.vod_actor = vod_actor;
                }

                public String getVod_director() {
                    return vod_director;
                }

                public void setVod_director(String vod_director) {
                    this.vod_director = vod_director;
                }

                public String getVod_tag() {
                    return vod_tag;
                }

                public void setVod_tag(String vod_tag) {
                    this.vod_tag = vod_tag;
                }

                public String getVod_intro() {
                    return vod_intro;
                }

                public void setVod_intro(String vod_intro) {
                    this.vod_intro = vod_intro;
                }

                public String getVod_hot_comment() {
                    return vod_hot_comment;
                }

                public void setVod_hot_comment(String vod_hot_comment) {
                    this.vod_hot_comment = vod_hot_comment;
                }

                public String getVod_year() {
                    return vod_year;
                }

                public void setVod_year(String vod_year) {
                    this.vod_year = vod_year;
                }

                public String getVod_pub_date() {
                    return vod_pub_date;
                }

                public void setVod_pub_date(String vod_pub_date) {
                    this.vod_pub_date = vod_pub_date;
                }

                public String getVod_district() {
                    return vod_district;
                }

                public void setVod_district(String vod_district) {
                    this.vod_district = vod_district;
                }

                public String getVod_hot_num() {
                    return vod_hot_num;
                }

                public void setVod_hot_num(String vod_hot_num) {
                    this.vod_hot_num = vod_hot_num;
                }

                public String getVod_score() {
                    return vod_score;
                }

                public void setVod_score(String vod_score) {
                    this.vod_score = vod_score;
                }

                public String getVod_star() {
                    return vod_star;
                }

                public void setVod_star(String vod_star) {
                    this.vod_star = vod_star;
                }
            }
        }

        public static class HotPlayBean implements Serializable {
            /**
             * alias : tv_hot_all
             * cn : 综合
             * lists : [{"vod_id":"27174318","vod_title":"神探南茜","vod_subtitle":"2019 / 美国 / 犯罪 悬疑 惊悚 / 拉里·滕 / 肯尼迪·麦克曼 利·刘易斯","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567068632.webp","vod_score":"5.3","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"肯尼迪·麦克曼,利·刘易斯,滕吉·卡斯姆","vod_director":"拉里·滕"},{"vod_id":"30283263","vod_title":"职责/羞耻","vod_subtitle":"2019 / 英国 / 犯罪 / 朱利安·法里诺 / 平岳大 凯莉·麦克唐纳","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571927458.webp","vod_score":"7.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"平岳大,凯莉·麦克唐纳,洼冢洋介","vod_director":"朱利安·法里诺"},{"vod_id":"30400802","vod_title":"通灵少女 第二季","vod_subtitle":"2019 / 中国台湾 / 剧情 悬疑 奇幻 / 劉彥甫 / 郭书瑶 范少勋","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2569796002.webp","vod_score":"6.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"郭书瑶,范少勋,温贞菱","vod_director":"劉彥甫"},{"vod_id":"34446034","vod_title":"TOP LEAGUE","vod_subtitle":"2019 / 日本 / 剧情 / 星野和成 中前勇儿 / 玉山铁二 池内博之","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567350249.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"玉山铁二,池内博之,佐久间由衣","vod_director":"星野和成"},{"vod_id":"34462015","vod_title":"红白黑黄 第七季","vod_subtitle":"2019 / 美国 / 剧情 动画 / 林赛·琼斯 卡拉·埃伯利","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2568040446.webp","vod_score":"9.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"林赛·琼斯,卡拉·埃伯利,艾伦·泽克","vod_director":""},{"vod_id":"34806269","vod_title":"选拔屋：猎头的做派","vod_subtitle":"2019 / 日本 / 剧情 / 西浦正记 / 松下奈绪 内田有纪","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2571199317.webp","vod_score":"6.5","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"松下奈绪,内田有纪,小手伸也","vod_director":"西浦正记"}]
             */

            private String alias;
            private String cn;
            private List<ListsBean> lists;

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getCn() {
                return cn;
            }

            public void setCn(String cn) {
                this.cn = cn;
            }

            public List<ListsBean> getLists() {
                return lists;
            }

            public void setLists(List<ListsBean> lists) {
                this.lists = lists;
            }

            public static class ListsBean extends EasyAdapter.TypeBean implements Serializable{
                /**
                 * vod_id : 27174318
                 * vod_title : 神探南茜
                 * vod_subtitle : 2019 / 美国 / 犯罪 悬疑 惊悚 / 拉里·滕 / 肯尼迪·麦克曼 利·刘易斯
                 * vod_pic : https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2567068632.webp
                 * vod_score : 5.3
                 * vod_category : tv
                 * vod_year : 2019
                 * vod_district :
                 * vod_star : 0.0
                 * vod_tag :
                 * vod_actor : 肯尼迪·麦克曼,利·刘易斯,滕吉·卡斯姆
                 * vod_director : 拉里·滕
                 */

                private String vod_id;
                private String vod_title;
                private String vod_subtitle;
                private String vod_pic;
                private String vod_score;
                private String vod_category;
                private String vod_year;
                private String vod_district;
                private String vod_star;
                private String vod_tag;
                private String vod_actor;
                private String vod_director;

                public String getVod_id() {
                    return vod_id;
                }

                public void setVod_id(String vod_id) {
                    this.vod_id = vod_id;
                }

                public String getVod_title() {
                    return vod_title;
                }

                public void setVod_title(String vod_title) {
                    this.vod_title = vod_title;
                }

                public String getVod_subtitle() {
                    return vod_subtitle;
                }

                public void setVod_subtitle(String vod_subtitle) {
                    this.vod_subtitle = vod_subtitle;
                }

                public String getVod_pic() {
                    return vod_pic;
                }

                public void setVod_pic(String vod_pic) {
                    this.vod_pic = vod_pic;
                }

                public String getVod_score() {
                    return vod_score;
                }

                public void setVod_score(String vod_score) {
                    this.vod_score = vod_score;
                }

                public String getVod_category() {
                    return vod_category;
                }

                public void setVod_category(String vod_category) {
                    this.vod_category = vod_category;
                }

                public String getVod_year() {
                    return vod_year;
                }

                public void setVod_year(String vod_year) {
                    this.vod_year = vod_year;
                }

                public String getVod_district() {
                    return vod_district;
                }

                public void setVod_district(String vod_district) {
                    this.vod_district = vod_district;
                }

                public String getVod_star() {
                    return vod_star;
                }

                public void setVod_star(String vod_star) {
                    this.vod_star = vod_star;
                }

                public String getVod_tag() {
                    return vod_tag;
                }

                public void setVod_tag(String vod_tag) {
                    this.vod_tag = vod_tag;
                }

                public String getVod_actor() {
                    return vod_actor;
                }

                public void setVod_actor(String vod_actor) {
                    this.vod_actor = vod_actor;
                }

                public String getVod_director() {
                    return vod_director;
                }

                public void setVod_director(String vod_director) {
                    this.vod_director = vod_director;
                }
            }
        }

        public static class RankBean extends EasyAdapter.TypeBean implements Serializable{
            /**
             * alias : tv_real_time_hotest
             * title : 实时热门电视
             * lists : [{"vod_id":"33418361","vod_title":"但是还有书籍","vod_subtitle":"2019 / 中国大陆 / 纪录片 / 罗颖鸾 杨骊珠 王悦阳 郑苏杭 刘倩瑜 彭欣宇 林宸西 / 朱岳 俞国林","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2577256090.webp","vod_score":"9.4","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"34906701","vod_title":"下辈子我再好好过","vod_subtitle":"2020 / 日本 / 喜剧 爱情 / 三木康一郎 / 内田理央 小关裕太","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579743163.webp","vod_score":"7.7","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""},{"vod_id":"33438250","vod_title":"别对映像研出手！","vod_subtitle":"2020 / 日本 / 动画 / 汤浅政明 / 伊藤沙莉 田村睦心","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2575204199.webp","vod_score":"9.7","vod_category":"tv","vod_year":"2020","vod_district":"日本","vod_star":"5.0","vod_tag":"动画","vod_actor":"","vod_director":""},{"vod_id":"27625457","vod_title":"去他*的世界 第二季","vod_subtitle":"2019 / 英国 / 剧情 喜剧 爱情 / 戴斯特尼·埃卡拉加 露西·福布斯 / 杰西卡·巴登 埃里克斯·劳瑟","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2570938064.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":""}]
             */

            private String alias;
            private String title;
            private List<ListsBeanX> lists;

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ListsBeanX> getLists() {
                return lists;
            }

            public void setLists(List<ListsBeanX> lists) {
                this.lists = lists;
            }

            public static class ListsBeanX implements Serializable{
                /**
                 * vod_id : 33418361
                 * vod_title : 但是还有书籍
                 * vod_subtitle : 2019 / 中国大陆 / 纪录片 / 罗颖鸾 杨骊珠 王悦阳 郑苏杭 刘倩瑜 彭欣宇 林宸西 / 朱岳 俞国林
                 * vod_pic : https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2577256090.webp
                 * vod_score : 9.4
                 * vod_category : tv
                 * vod_year : 2019
                 * vod_district :
                 * vod_star : 0.0
                 * vod_tag :
                 * vod_actor :
                 * vod_director :
                 */

                private String vod_id;
                private String vod_title;
                private String vod_subtitle;
                private String vod_pic;
                private String vod_score;
                private String vod_category;
                private String vod_year;
                private String vod_district;
                private String vod_star;
                private String vod_tag;
                private String vod_actor;
                private String vod_director;

                public String getVod_id() {
                    return vod_id;
                }

                public void setVod_id(String vod_id) {
                    this.vod_id = vod_id;
                }

                public String getVod_title() {
                    return vod_title;
                }

                public void setVod_title(String vod_title) {
                    this.vod_title = vod_title;
                }

                public String getVod_subtitle() {
                    return vod_subtitle;
                }

                public void setVod_subtitle(String vod_subtitle) {
                    this.vod_subtitle = vod_subtitle;
                }

                public String getVod_pic() {
                    return vod_pic;
                }

                public void setVod_pic(String vod_pic) {
                    this.vod_pic = vod_pic;
                }

                public String getVod_score() {
                    return vod_score;
                }

                public void setVod_score(String vod_score) {
                    this.vod_score = vod_score;
                }

                public String getVod_category() {
                    return vod_category;
                }

                public void setVod_category(String vod_category) {
                    this.vod_category = vod_category;
                }

                public String getVod_year() {
                    return vod_year;
                }

                public void setVod_year(String vod_year) {
                    this.vod_year = vod_year;
                }

                public String getVod_district() {
                    return vod_district;
                }

                public void setVod_district(String vod_district) {
                    this.vod_district = vod_district;
                }

                public String getVod_star() {
                    return vod_star;
                }

                public void setVod_star(String vod_star) {
                    this.vod_star = vod_star;
                }

                public String getVod_tag() {
                    return vod_tag;
                }

                public void setVod_tag(String vod_tag) {
                    this.vod_tag = vod_tag;
                }

                public String getVod_actor() {
                    return vod_actor;
                }

                public void setVod_actor(String vod_actor) {
                    this.vod_actor = vod_actor;
                }

                public String getVod_director() {
                    return vod_director;
                }

                public void setVod_director(String vod_director) {
                    this.vod_director = vod_director;
                }
            }
        }
    }
}
