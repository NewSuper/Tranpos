package com.newsuper.t.consumer.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class GoodsBean {


    private int pageTotal;
    private List<FoodlistBean> foodlist;

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<FoodlistBean> getFoodlist() {
        return foodlist;
    }

    public void setFoodlist(List<FoodlistBean> foodlist) {
        this.foodlist = foodlist;
    }

    public static class FoodlistBean {
        /**
         * id : 5390480
         * tag : 0
         * name : 测123
         * img :
         * price : 100.00
         * buying_price : 123.32
         * type_id : 8617
         * second_type_id : 0
         * unit : 12
         * label :
         * ordered_count : 181
         * is_dabao : 0
         * dabao_money : 0.00
         * stockvalid : 1
         * stock : 998
         * open_autostock : 0
         * autostocknum : 20
         * member_price_used : 0
         * member_price : 0.00
         * memberlimit : 0
         * has_formerprice : 0
         * formerprice : 0.00
         * descript :
         * is_nature : 1
         * nature : [{"naturename":"倒萨大","maxchoose":"1","data":[{"naturevalue":"的撒打算","price":1},{"naturevalue":"湿哒哒","price":2}]}]
         * is_limitfood : 0
         * start_time : 2017-06-01
         * stop_time : 2017-09-01
         * limit_tags :
         * is_all_limit : 0
         * is_all_limit_num : 0
         * is_customerday_limit : 0
         * day_foodnum : 1
         * datetage : 0
         * timetage : 0
         * hasBuyNum : 0
         * hasBuyNumByDay : 0
         * limit_time : [{"start":"03:00","stop":"14:00"},{"start":"06:00","stop":"10:00"}]
         */

        private String id;
        private String tag;
        private String name;
        private String img;
        private String price;
        private String buying_price;
        private String type_id;
        private String second_type_id;
        private String unit;
        private String label;
        private String ordered_count;
        private String is_dabao;
        private String dabao_money;
        private String stockvalid;
        private String stock;
        private String open_autostock;
        private String autostocknum;
        private String member_price_used;
        private String member_price;
        private String memberlimit;
        private String has_formerprice;
        private String formerprice;
        private String descript;
        private String is_nature;
        private String is_limitfood;
        private String start_time;
        private String stop_time;
        private String limit_tags;
        private String is_all_limit;
        private String is_all_limit_num;
        private String is_customerday_limit;
        private String day_foodnum;
        private String datetage;
        private String timetage;
        private String hasBuyNum;
        private String hasBuyNumByDay;
        private List<NatureBean> nature;
        private List<LimitTimeBean> limit_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBuying_price() {
            return buying_price;
        }

        public void setBuying_price(String buying_price) {
            this.buying_price = buying_price;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getSecond_type_id() {
            return second_type_id;
        }

        public void setSecond_type_id(String second_type_id) {
            this.second_type_id = second_type_id;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getOrdered_count() {
            return ordered_count;
        }

        public void setOrdered_count(String ordered_count) {
            this.ordered_count = ordered_count;
        }

        public String getIs_dabao() {
            return is_dabao;
        }

        public void setIs_dabao(String is_dabao) {
            this.is_dabao = is_dabao;
        }

        public String getDabao_money() {
            return dabao_money;
        }

        public void setDabao_money(String dabao_money) {
            this.dabao_money = dabao_money;
        }

        public String getStockvalid() {
            return stockvalid;
        }

        public void setStockvalid(String stockvalid) {
            this.stockvalid = stockvalid;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getOpen_autostock() {
            return open_autostock;
        }

        public void setOpen_autostock(String open_autostock) {
            this.open_autostock = open_autostock;
        }

        public String getAutostocknum() {
            return autostocknum;
        }

        public void setAutostocknum(String autostocknum) {
            this.autostocknum = autostocknum;
        }

        public String getMember_price_used() {
            return member_price_used;
        }

        public void setMember_price_used(String member_price_used) {
            this.member_price_used = member_price_used;
        }

        public String getMember_price() {
            return member_price;
        }

        public void setMember_price(String member_price) {
            this.member_price = member_price;
        }

        public String getMemberlimit() {
            return memberlimit;
        }

        public void setMemberlimit(String memberlimit) {
            this.memberlimit = memberlimit;
        }

        public String getHas_formerprice() {
            return has_formerprice;
        }

        public void setHas_formerprice(String has_formerprice) {
            this.has_formerprice = has_formerprice;
        }

        public String getFormerprice() {
            return formerprice;
        }

        public void setFormerprice(String formerprice) {
            this.formerprice = formerprice;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }

        public String getIs_nature() {
            return is_nature;
        }

        public void setIs_nature(String is_nature) {
            this.is_nature = is_nature;
        }

        public String getIs_limitfood() {
            return is_limitfood;
        }

        public void setIs_limitfood(String is_limitfood) {
            this.is_limitfood = is_limitfood;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStop_time() {
            return stop_time;
        }

        public void setStop_time(String stop_time) {
            this.stop_time = stop_time;
        }

        public String getLimit_tags() {
            return limit_tags;
        }

        public void setLimit_tags(String limit_tags) {
            this.limit_tags = limit_tags;
        }

        public String getIs_all_limit() {
            return is_all_limit;
        }

        public void setIs_all_limit(String is_all_limit) {
            this.is_all_limit = is_all_limit;
        }

        public String getIs_all_limit_num() {
            return is_all_limit_num;
        }

        public void setIs_all_limit_num(String is_all_limit_num) {
            this.is_all_limit_num = is_all_limit_num;
        }

        public String getIs_customerday_limit() {
            return is_customerday_limit;
        }

        public void setIs_customerday_limit(String is_customerday_limit) {
            this.is_customerday_limit = is_customerday_limit;
        }

        public String getDay_foodnum() {
            return day_foodnum;
        }

        public void setDay_foodnum(String day_foodnum) {
            this.day_foodnum = day_foodnum;
        }

        public String getDatetage() {
            return datetage;
        }

        public void setDatetage(String datetage) {
            this.datetage = datetage;
        }

        public String getTimetage() {
            return timetage;
        }

        public void setTimetage(String timetage) {
            this.timetage = timetage;
        }

        public String getHasBuyNum() {
            return hasBuyNum;
        }

        public void setHasBuyNum(String hasBuyNum) {
            this.hasBuyNum = hasBuyNum;
        }

        public String getHasBuyNumByDay() {
            return hasBuyNumByDay;
        }

        public void setHasBuyNumByDay(String hasBuyNumByDay) {
            this.hasBuyNumByDay = hasBuyNumByDay;
        }

        public List<NatureBean> getNature() {
            return nature;
        }

        public void setNature(List<NatureBean> nature) {
            this.nature = nature;
        }

        public List<LimitTimeBean> getLimit_time() {
            return limit_time;
        }

        public void setLimit_time(List<LimitTimeBean> limit_time) {
            this.limit_time = limit_time;
        }

        public static class NatureBean {
            /**
             * naturename : 倒萨大
             * maxchoose : 1
             * data : [{"naturevalue":"的撒打算","price":1},{"naturevalue":"湿哒哒","price":2}]
             */

            private String naturename;
            private String maxchoose;
            private List<DataBean> data;

            public String getNaturename() {
                return naturename;
            }

            public void setNaturename(String naturename) {
                this.naturename = naturename;
            }

            public String getMaxchoose() {
                return maxchoose;
            }

            public void setMaxchoose(String maxchoose) {
                this.maxchoose = maxchoose;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * naturevalue : 的撒打算
                 * price : 1
                 */

                private String naturevalue;
                private int price;

                public String getNaturevalue() {
                    return naturevalue;
                }

                public void setNaturevalue(String naturevalue) {
                    this.naturevalue = naturevalue;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }
            }
        }

        public static class LimitTimeBean {
            /**
             * start : 03:00
             * stop : 14:00
             */

            private String start;
            private String stop;

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getStop() {
                return stop;
            }

            public void setStop(String stop) {
                this.stop = stop;
            }
        }
    }
}
