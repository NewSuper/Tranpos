package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.util.List;

public class MovieNewTabRankEntity {

    /**
     * code : 0
     * msg : success
     * data : {"total":23,"per_page":"10","current_page":1,"last_page":3,"data":[{"vod_id":"3754946","vod_title":"锦衣卫","vod_subtitle":"2010 / 中国大陆 中国香港 新加坡 / 剧情 动作 惊悚 / 李仁港 / 甄子丹 赵薇","vod_pub_date":"2010-02-03","vod_pic":"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p776030044.webp","vod_score":"5.9","vod_category":"movie","vod_year":"2010","vod_district":"中国大陆","vod_star":"3.0","vod_tag":"剧情,动作,惊悚","vod_actor":"甄子丹,赵薇,吴尊,徐子珊,戚玉武,陈观泰,午马,洪金宝,罗家英,刘松仁","vod_director":"李仁港","is_notice":1},{"vod_id":"6538866","vod_title":"极速车王","vod_subtitle":"2019 / 美国 法国 / 剧情 传记 运动 / 詹姆斯·曼高德 / 马特·达蒙 克里斯蒂安·贝尔","vod_pub_date":"2019-08-30","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2568792942.webp","vod_score":"8.5","vod_category":"movie","vod_year":"2019","vod_district":"特柳赖德电影节","vod_star":"4.5","vod_tag":"剧情,传记,运动","vod_actor":"马特·达蒙,克里斯蒂安·贝尔,乔·博恩瑟,诺亚·尤佩,玛丽萨·佩特罗,凯特瑞娜·巴尔夫,乔什·卢卡斯,雷·迈克金农,约翰·约瑟夫·菲尔德,崔西·莱茨,华莱士·朗翰,乔纳森·拉帕格里拉,斯特凡,鲁道夫·马丁,拉奇兰·布坎南,瓦德·霍尔顿,肖恩·卡里甘,克里斯托弗·达尔加,亚当·梅菲尔德,乔瓦尼索罗菲瓦,乔·威廉森,本杰明·里格比,杰克·麦克马伦,保罗·福克斯,雷莫·吉罗内,德鲁·劳施,朱利安·米勒,艾拉姆·奥里安,埃姆利·贝赫什蒂,蒂芙妮·伊冯娜·考克斯,达林·库珀,科拉多·因韦尔尼齐,特洛伊·迪林格,彼得·阿佩塞拉,斯凯勒·马歇尔","vod_director":"詹姆斯·曼高德","is_notice":1},{"vod_id":"1292052","vod_title":"肖申克的救赎","vod_subtitle":"1994 / 美国 / 犯罪 剧情 / 弗兰克·德拉邦特 / 蒂姆·罗宾斯 摩根·弗里曼","vod_pub_date":"1994-09-10","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","vod_score":"9.7","vod_category":"movie","vod_year":"1994","vod_district":"多伦多电影节","vod_star":"5.0","vod_tag":"犯罪,剧情","vod_actor":"蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿,威廉姆·赛德勒,克兰西·布朗,吉尔·贝罗斯,马克·罗斯顿,詹姆斯·惠特摩,杰弗里·德曼,拉里·布兰登伯格,尼尔·吉恩托利,布赖恩·利比,大卫·普罗瓦尔,约瑟夫·劳格诺,祖德·塞克利拉,保罗·麦克兰尼,芮妮·布莱恩,阿方索·弗里曼,V·J·福斯特,弗兰克·梅德拉诺,马克·迈尔斯,尼尔·萨默斯,耐德·巴拉米,布赖恩·戴拉特,唐·麦克马纳斯","vod_director":"弗兰克·德拉邦特","is_notice":1},{"vod_id":"30269016","vod_title":"半个喜剧","vod_subtitle":"2019 / 中国大陆 / 喜剧 爱情 / 周申 刘露 / 任素汐 吴昱翰","vod_pub_date":"2019-12-20","vod_pic":"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2576482356.webp","vod_score":"7.6","vod_category":"movie","vod_year":"2019","vod_district":"中国大陆","vod_star":"4.0","vod_tag":"喜剧,爱情","vod_actor":"任素汐,吴昱翰,刘迅,汤敏,赵海燕,刘宸翎,裴魁山,梁瀛,于奥,梁翘柏,常远","vod_director":"周申,刘露","is_notice":0},{"vod_id":"6981153","vod_title":"爱尔兰人","vod_subtitle":"2019 / 美国 / 剧情 传记 犯罪 / 马丁·斯科塞斯 / 罗伯特·德尼罗 阿尔·帕西诺","vod_pub_date":"2019-09-27","vod_pic":"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2568902055.webp","vod_score":"9.0","vod_category":"movie","vod_year":"2019","vod_district":"纽约电影节","vod_star":"4.5","vod_tag":"剧情,传记,犯罪","vod_actor":"罗伯特·德尼罗,阿尔·帕西诺,乔·佩西,安娜·帕奎因,杰西·普莱蒙,哈威·凯特尔,斯蒂芬·格拉汉姆,鲍比·坎纳瓦尔,杰克·休斯顿,阿莱卡萨·帕拉迪诺,凯瑟琳·纳杜奇,多米尼克·隆巴多兹,雷·罗马诺,塞巴斯蒂安·马尼斯科,杰克·霍夫曼,保罗·本-维克托,斯蒂芬妮·库尔特祖巴,莎伦·菲弗,路易斯·坎瑟米,茵迪亚·恩能加,J.C.麦肯泽,巴里·普赖默斯,吉姆·诺顿,盖里·巴萨拉巴,玛格丽特·安妮·佛罗伦斯,约翰·斯库蒂,约翰·塞纳迭姆博,大卫·阿隆·贝克,桃乐丝·麦卡锡,凯文·凯恩,加里·帕斯托雷,约瑟夫·罗素,凯文·奥罗克,娜塔莎·罗曼诺娃,杰里米·卢克,凯莉·P·威廉姆斯,詹妮弗·马奇,保罗·鲍格才,蒂姆·内夫,斯蒂芬·梅尔勒,克雷格·文森特,比利·史密斯,布莱斯·科里根,斯泰西·爱丽丝·科恩,吉诺·卡法雷利,拉里·罗曼诺,罗伯特·富纳罗,维罗妮卡·阿利奇诺,克劳黛特·拉利,詹姆斯·洛林兹,阿什莉·诺斯,肯·斯拉迪克,帕特里克·加洛","vod_director":"马丁·斯科塞斯","is_notice":0},{"vod_id":"34906611","vod_title":"辛弃疾1162","vod_subtitle":"2020 / 中国大陆 / 动作 古装 / 张哲 张明国 / 谢苗 南笙","vod_pub_date":"2020-01-02","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2579789452.webp","vod_score":"6.4","vod_category":"movie","vod_year":"2020","vod_district":"中国大陆","vod_star":"3.0","vod_tag":"动作,古装","vod_actor":"谢苗,南笙,郑文森,高名扬","vod_director":"张哲,张明国","is_notice":0},{"vod_id":"34893827","vod_title":"野猪","vod_subtitle":"2020 / 中国大陆 / 剧情 喜剧 / 野猪 泓 / 王轩堃 侯桐江","vod_pub_date":"2020-01-10","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2577466852.webp","vod_score":"0.0","vod_category":"movie","vod_year":"2020","vod_district":"中国大陆","vod_star":"0.0","vod_tag":"剧情,喜剧","vod_actor":"王轩堃,侯桐江,刘东明,闫昌,胡刚,王一霏,王馨雨,吕睿男,阿扎,冷雪儿,夏磊,李文元,董硕,李子超","vod_director":"野猪 泓","is_notice":0},{"vod_id":"33424345","vod_title":"紫罗兰永恒花园外传：永远与自动手记人偶","vod_subtitle":"2019 / 日本 / 剧情 动画 / 藤田春香 / 石川由依 茅原实里","vod_pub_date":"2020-01-10","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2578719670.webp","vod_score":"8.2","vod_category":"movie","vod_year":"2019","vod_district":"中国大陆","vod_star":"4.0","vod_tag":"剧情,动画","vod_actor":"石川由依,茅原实里,远藤绫,寿美菜子,子安武人,户松遥,内山昂辉,悠木碧","vod_director":"藤田春香","is_notice":1},{"vod_id":"33417046","vod_title":"宠爱","vod_subtitle":"2019 / 中国大陆 / 剧情 喜剧 爱情 / 杨子 / 于和伟 吴磊","vod_pub_date":"2019-12-31","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2577327922.webp","vod_score":"6.2","vod_category":"movie","vod_year":"2019","vod_district":"中国大陆","vod_star":"3.0","vod_tag":"剧情,喜剧,爱情","vod_actor":"于和伟,吴磊,张子枫,钟汉良,杨子姗,陈伟霆,钟楚曦,檀健次,阚清子,郭麒麟,李兰迪,郎月婷,李倩,龚蓓苾,余皑磊,王紫逸","vod_director":"杨子","is_notice":0},{"vod_id":"30394535","vod_title":"被光抓走的人","vod_subtitle":"2019 / 中国大陆 / 剧情 爱情 科幻 / 董润年 / 黄渤 王珞丹","vod_pub_date":"2019-12-13","vod_pic":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2575887979.webp","vod_score":"7.0","vod_category":"movie","vod_year":"2019","vod_district":"中国大陆","vod_star":"3.5","vod_tag":"剧情,爱情,科幻","vod_actor":"黄渤,王珞丹,谭卓,白客,黄璐,文淇,焦俊艳,宋春丽,李嘉琪,丁溪鹤,吕星辰,黄觉,李倩,王菊,李诞,金靖承,曹炳琨,张腾岳,田壮壮,刘頔","vod_director":"董润年","is_notice":0}],"section":{"section_id":"movie_real_time_hotest","category":"movie","name":"实时执门电影","title":"实时执门电影","sub_title":"","description":"根据电影实时热度与关注度得出的综合排名","header_bg":"https://img3.doubanio.com/view/photo/photo/public/p2533776779.webp","header_fg":"","header_icon":"https://img3.doubanio.com/dae/screenshot/image/html/053498e584ea0591.jpg?window_size=300,300&format=png","status":1,"create_time":"2020-01-08 17:41:49","update_time":"2020-01-08 17:41:49"}}
     * time : 1578619730
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
         * total : 23
         * per_page : 10
         * current_page : 1
         * last_page : 3
         * data : [{"vod_id":"3754946","vod_title":"锦衣卫","vod_subtitle":"2010 / 中国大陆 中国香港 新加坡 / 剧情 动作 惊悚 / 李仁港 / 甄子丹 赵薇","vod_pub_date":"2010-02-03","vod_pic":"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p776030044.webp","vod_score":"5.9","vod_category":"movie","vod_year":"2010","vod_district":"中国大陆","vod_star":"3.0","vod_tag":"剧情,动作,惊悚","vod_actor":"甄子丹,赵薇,吴尊,徐子珊,戚玉武,陈观泰,午马,洪金宝,罗家英,刘松仁","vod_director":"李仁港","is_notice":1},{"vod_id":"6538866","vod_title":"极速车王","vod_subtitle":"2019 / 美国 法国 / 剧情 传记 运动 / 詹姆斯·曼高德 / 马特·达蒙 克里斯蒂安·贝尔","vod_pub_date":"2019-08-30","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2568792942.webp","vod_score":"8.5","vod_category":"movie","vod_year":"2019","vod_district":"特柳赖德电影节","vod_star":"4.5","vod_tag":"剧情,传记,运动","vod_actor":"马特·达蒙,克里斯蒂安·贝尔,乔·博恩瑟,诺亚·尤佩,玛丽萨·佩特罗,凯特瑞娜·巴尔夫,乔什·卢卡斯,雷·迈克金农,约翰·约瑟夫·菲尔德,崔西·莱茨,华莱士·朗翰,乔纳森·拉帕格里拉,斯特凡,鲁道夫·马丁,拉奇兰·布坎南,瓦德·霍尔顿,肖恩·卡里甘,克里斯托弗·达尔加,亚当·梅菲尔德,乔瓦尼索罗菲瓦,乔·威廉森,本杰明·里格比,杰克·麦克马伦,保罗·福克斯,雷莫·吉罗内,德鲁·劳施,朱利安·米勒,艾拉姆·奥里安,埃姆利·贝赫什蒂,蒂芙妮·伊冯娜·考克斯,达林·库珀,科拉多·因韦尔尼齐,特洛伊·迪林格,彼得·阿佩塞拉,斯凯勒·马歇尔","vod_director":"詹姆斯·曼高德","is_notice":1},{"vod_id":"1292052","vod_title":"肖申克的救赎","vod_subtitle":"1994 / 美国 / 犯罪 剧情 / 弗兰克·德拉邦特 / 蒂姆·罗宾斯 摩根·弗里曼","vod_pub_date":"1994-09-10","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","vod_score":"9.7","vod_category":"movie","vod_year":"1994","vod_district":"多伦多电影节","vod_star":"5.0","vod_tag":"犯罪,剧情","vod_actor":"蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿,威廉姆·赛德勒,克兰西·布朗,吉尔·贝罗斯,马克·罗斯顿,詹姆斯·惠特摩,杰弗里·德曼,拉里·布兰登伯格,尼尔·吉恩托利,布赖恩·利比,大卫·普罗瓦尔,约瑟夫·劳格诺,祖德·塞克利拉,保罗·麦克兰尼,芮妮·布莱恩,阿方索·弗里曼,V·J·福斯特,弗兰克·梅德拉诺,马克·迈尔斯,尼尔·萨默斯,耐德·巴拉米,布赖恩·戴拉特,唐·麦克马纳斯","vod_director":"弗兰克·德拉邦特","is_notice":1},{"vod_id":"30269016","vod_title":"半个喜剧","vod_subtitle":"2019 / 中国大陆 / 喜剧 爱情 / 周申 刘露 / 任素汐 吴昱翰","vod_pub_date":"2019-12-20","vod_pic":"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2576482356.webp","vod_score":"7.6","vod_category":"movie","vod_year":"2019","vod_district":"中国大陆","vod_star":"4.0","vod_tag":"喜剧,爱情","vod_actor":"任素汐,吴昱翰,刘迅,汤敏,赵海燕,刘宸翎,裴魁山,梁瀛,于奥,梁翘柏,常远","vod_director":"周申,刘露","is_notice":0},{"vod_id":"6981153","vod_title":"爱尔兰人","vod_subtitle":"2019 / 美国 / 剧情 传记 犯罪 / 马丁·斯科塞斯 / 罗伯特·德尼罗 阿尔·帕西诺","vod_pub_date":"2019-09-27","vod_pic":"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2568902055.webp","vod_score":"9.0","vod_category":"movie","vod_year":"2019","vod_district":"纽约电影节","vod_star":"4.5","vod_tag":"剧情,传记,犯罪","vod_actor":"罗伯特·德尼罗,阿尔·帕西诺,乔·佩西,安娜·帕奎因,杰西·普莱蒙,哈威·凯特尔,斯蒂芬·格拉汉姆,鲍比·坎纳瓦尔,杰克·休斯顿,阿莱卡萨·帕拉迪诺,凯瑟琳·纳杜奇,多米尼克·隆巴多兹,雷·罗马诺,塞巴斯蒂安·马尼斯科,杰克·霍夫曼,保罗·本-维克托,斯蒂芬妮·库尔特祖巴,莎伦·菲弗,路易斯·坎瑟米,茵迪亚·恩能加,J.C.麦肯泽,巴里·普赖默斯,吉姆·诺顿,盖里·巴萨拉巴,玛格丽特·安妮·佛罗伦斯,约翰·斯库蒂,约翰·塞纳迭姆博,大卫·阿隆·贝克,桃乐丝·麦卡锡,凯文·凯恩,加里·帕斯托雷,约瑟夫·罗素,凯文·奥罗克,娜塔莎·罗曼诺娃,杰里米·卢克,凯莉·P·威廉姆斯,詹妮弗·马奇,保罗·鲍格才,蒂姆·内夫,斯蒂芬·梅尔勒,克雷格·文森特,比利·史密斯,布莱斯·科里根,斯泰西·爱丽丝·科恩,吉诺·卡法雷利,拉里·罗曼诺,罗伯特·富纳罗,维罗妮卡·阿利奇诺,克劳黛特·拉利,詹姆斯·洛林兹,阿什莉·诺斯,肯·斯拉迪克,帕特里克·加洛","vod_director":"马丁·斯科塞斯","is_notice":0},{"vod_id":"34906611","vod_title":"辛弃疾1162","vod_subtitle":"2020 / 中国大陆 / 动作 古装 / 张哲 张明国 / 谢苗 南笙","vod_pub_date":"2020-01-02","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2579789452.webp","vod_score":"6.4","vod_category":"movie","vod_year":"2020","vod_district":"中国大陆","vod_star":"3.0","vod_tag":"动作,古装","vod_actor":"谢苗,南笙,郑文森,高名扬","vod_director":"张哲,张明国","is_notice":0},{"vod_id":"34893827","vod_title":"野猪","vod_subtitle":"2020 / 中国大陆 / 剧情 喜剧 / 野猪 泓 / 王轩堃 侯桐江","vod_pub_date":"2020-01-10","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2577466852.webp","vod_score":"0.0","vod_category":"movie","vod_year":"2020","vod_district":"中国大陆","vod_star":"0.0","vod_tag":"剧情,喜剧","vod_actor":"王轩堃,侯桐江,刘东明,闫昌,胡刚,王一霏,王馨雨,吕睿男,阿扎,冷雪儿,夏磊,李文元,董硕,李子超","vod_director":"野猪 泓","is_notice":0},{"vod_id":"33424345","vod_title":"紫罗兰永恒花园外传：永远与自动手记人偶","vod_subtitle":"2019 / 日本 / 剧情 动画 / 藤田春香 / 石川由依 茅原实里","vod_pub_date":"2020-01-10","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2578719670.webp","vod_score":"8.2","vod_category":"movie","vod_year":"2019","vod_district":"中国大陆","vod_star":"4.0","vod_tag":"剧情,动画","vod_actor":"石川由依,茅原实里,远藤绫,寿美菜子,子安武人,户松遥,内山昂辉,悠木碧","vod_director":"藤田春香","is_notice":1},{"vod_id":"33417046","vod_title":"宠爱","vod_subtitle":"2019 / 中国大陆 / 剧情 喜剧 爱情 / 杨子 / 于和伟 吴磊","vod_pub_date":"2019-12-31","vod_pic":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2577327922.webp","vod_score":"6.2","vod_category":"movie","vod_year":"2019","vod_district":"中国大陆","vod_star":"3.0","vod_tag":"剧情,喜剧,爱情","vod_actor":"于和伟,吴磊,张子枫,钟汉良,杨子姗,陈伟霆,钟楚曦,檀健次,阚清子,郭麒麟,李兰迪,郎月婷,李倩,龚蓓苾,余皑磊,王紫逸","vod_director":"杨子","is_notice":0},{"vod_id":"30394535","vod_title":"被光抓走的人","vod_subtitle":"2019 / 中国大陆 / 剧情 爱情 科幻 / 董润年 / 黄渤 王珞丹","vod_pub_date":"2019-12-13","vod_pic":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2575887979.webp","vod_score":"7.0","vod_category":"movie","vod_year":"2019","vod_district":"中国大陆","vod_star":"3.5","vod_tag":"剧情,爱情,科幻","vod_actor":"黄渤,王珞丹,谭卓,白客,黄璐,文淇,焦俊艳,宋春丽,李嘉琪,丁溪鹤,吕星辰,黄觉,李倩,王菊,李诞,金靖承,曹炳琨,张腾岳,田壮壮,刘頔","vod_director":"董润年","is_notice":0}]
         * section : {"section_id":"movie_real_time_hotest","category":"movie","name":"实时执门电影","title":"实时执门电影","sub_title":"","description":"根据电影实时热度与关注度得出的综合排名","header_bg":"https://img3.doubanio.com/view/photo/photo/public/p2533776779.webp","header_fg":"","header_icon":"https://img3.doubanio.com/dae/screenshot/image/html/053498e584ea0591.jpg?window_size=300,300&format=png","status":1,"create_time":"2020-01-08 17:41:49","update_time":"2020-01-08 17:41:49"}
         */

        private int total;
        private String per_page;
        private int current_page;
        private int last_page;
        private SectionBean section;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
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

        public SectionBean getSection() {
            return section;
        }

        public void setSection(SectionBean section) {
            this.section = section;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class SectionBean {
            /**
             * section_id : movie_real_time_hotest
             * category : movie
             * name : 实时执门电影
             * title : 实时执门电影
             * sub_title :
             * description : 根据电影实时热度与关注度得出的综合排名
             * header_bg : https://img3.doubanio.com/view/photo/photo/public/p2533776779.webp
             * header_fg :
             * header_icon : https://img3.doubanio.com/dae/screenshot/image/html/053498e584ea0591.jpg?window_size=300,300&format=png
             * status : 1
             * create_time : 2020-01-08 17:41:49
             * update_time : 2020-01-08 17:41:49
             */

            private String section_id;
            private String category;
            private String name;
            private String title;
            private String sub_title;
            private String description;
            private String header_bg;
            private String header_fg;
            private String header_icon;
            private int status;
            private String create_time;
            private String update_time;

            public String getSection_id() {
                return section_id;
            }

            public void setSection_id(String section_id) {
                this.section_id = section_id;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSub_title() {
                return sub_title;
            }

            public void setSub_title(String sub_title) {
                this.sub_title = sub_title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getHeader_bg() {
                return header_bg;
            }

            public void setHeader_bg(String header_bg) {
                this.header_bg = header_bg;
            }

            public String getHeader_fg() {
                return header_fg;
            }

            public void setHeader_fg(String header_fg) {
                this.header_fg = header_fg;
            }

            public String getHeader_icon() {
                return header_icon;
            }

            public void setHeader_icon(String header_icon) {
                this.header_icon = header_icon;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }
        }

        public static class DataBean extends EasyAdapter.TypeBean {
            /**
             * vod_id : 3754946
             * vod_title : 锦衣卫
             * vod_subtitle : 2010 / 中国大陆 中国香港 新加坡 / 剧情 动作 惊悚 / 李仁港 / 甄子丹 赵薇
             * vod_pub_date : 2010-02-03
             * vod_pic : https://img9.doubanio.com/view/photo/s_ratio_poster/public/p776030044.webp
             * vod_score : 5.9
             * vod_category : movie
             * vod_year : 2010
             * vod_district : 中国大陆
             * vod_star : 3.0
             * vod_tag : 剧情,动作,惊悚
             * vod_actor : 甄子丹,赵薇,吴尊,徐子珊,戚玉武,陈观泰,午马,洪金宝,罗家英,刘松仁
             * vod_director : 李仁港
             * is_notice : 1
             */

            private String vod_id;
            private String vod_title;
            private String vod_subtitle;
            private String vod_pub_date;
            private String vod_pic;
            private String vod_score;
            private String vod_category;
            private String vod_year;
            private String vod_district;
            private String vod_star;
            private String vod_tag;
            private String vod_actor;
            private String vod_director;
            private int is_notice;

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

            public String getVod_pub_date() {
                return vod_pub_date;
            }

            public void setVod_pub_date(String vod_pub_date) {
                this.vod_pub_date = vod_pub_date;
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

            public int getIs_notice() {
                return is_notice;
            }

            public void setIs_notice(int is_notice) {
                this.is_notice = is_notice;
            }
        }
    }
}
