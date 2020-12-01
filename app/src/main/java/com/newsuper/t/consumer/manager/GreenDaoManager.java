package com.newsuper.t.consumer.manager;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.bean.GoodsDbBean;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.GoodsListBean.GoodsInfo;
import com.newsuper.t.consumer.greendao.DaoMaster;
import com.newsuper.t.consumer.greendao.DaoSession;
import com.newsuper.t.consumer.greendao.GoodsDbBeanDao;
import com.newsuper.t.consumer.greendao.GoodsDbBeanDao.Properties;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance; //单例
    private Gson gson;

    private GreenDaoManager() {
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new
                    DaoMaster.DevOpenHelper(BaseApplication.getContext(), "cartDb", null);//此处为自己需要处理的表
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
            gson = new Gson();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {//保证异步处理安全操作
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    //添加商品
    public void addGoods(GoodsInfo goods) {
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        GoodsDbBean existedGoods = null;
        if(null!=goods.nature&&goods.nature.size()>0){
            //有属性商品直接添加
            GoodsDbBean insertGoods = new GoodsDbBean();
            insertGoods.setShopId(goods.shop_id);
            insertGoods.setGoodsId(goods.id);
            goods.count=1;
            insertGoods.setGoodsInfo(gson.toJson(goods));
            insertGoods.setType("0");
            insertGoods.setIndex(goods.index);
            insertGoods.setNature(gson.toJson(goods.nature));
            getSession().getGoodsDbBeanDao().insert(insertGoods);
        }else{
            existedGoods = (GoodsDbBean) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id)
                    , Properties.Nature.eq(gson.toJson(goods.nature)), Properties.Type.eq("0"))
                    .build().unique();
            if (null != existedGoods) {
                GoodsInfo goodsInfo=gson.fromJson(existedGoods.getGoodsInfo(),GoodsInfo.class);
                goodsInfo.count++;
                existedGoods.setGoodsInfo(gson.toJson(goodsInfo));
                getSession().getGoodsDbBeanDao().update(existedGoods);
            } else {
                //如果商品不存在就插入该数据
                GoodsDbBean insertGoods = new GoodsDbBean();
                insertGoods.setShopId(goods.shop_id);
                insertGoods.setGoodsId(goods.id);
                goods.count=1;
                insertGoods.setGoodsInfo(gson.toJson(goods));
                insertGoods.setType("0");
                insertGoods.setNature(gson.toJson(goods.nature));
                getSession().getGoodsDbBeanDao().insert(insertGoods);
            }
        }
    }

    //添加商品
    public void addGoodsFromServer(GoodsInfo goods) {
        if( null != goods.nature && goods.nature.size() > 0 ){
            //有属性商品直接添加
            GoodsDbBean insertGoods = new GoodsDbBean();
            insertGoods.setShopId(goods.shop_id);
            insertGoods.setGoodsId(goods.id);
            insertGoods.setGoodsInfo(gson.toJson(goods));
            insertGoods.setType("0");
            insertGoods.setIndex(goods.index);
            insertGoods.setNature(gson.toJson(goods.nature));
            getSession().getGoodsDbBeanDao().insert(insertGoods);
        }else{
            //如果商品不存在就插入该数据
            GoodsDbBean insertGoods = new GoodsDbBean();
            insertGoods.setShopId(goods.shop_id);
            insertGoods.setGoodsId(goods.id);
            insertGoods.setGoodsInfo(gson.toJson(goods));
            insertGoods.setType("0");
            insertGoods.setNature(gson.toJson(goods.nature));
            getSession().getGoodsDbBeanDao().insert(insertGoods);
        }
    }


    //更新有属性商品或套餐
    public void updateNatureGoods(GoodsInfo goods){
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        GoodsDbBean existedGoods = (GoodsDbBean) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id)
                , Properties.Index.eq(goods.index)).build().unique();
        if (null != existedGoods) {
            existedGoods.setGoodsInfo(gson.toJson(goods));
            getSession().getGoodsDbBeanDao().update(existedGoods);
        }
    }

    //删除商品(包含套餐)
    public void deleteGoods(GoodsListBean.GoodsInfo goods) {
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        GoodsDbBean existedGoods = null;
        if(null!=goods.nature&&goods.nature.size()>0){
            existedGoods = (GoodsDbBean) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id),Properties.Index.eq(goods.index))
                    .build().unique();
            if(null!=existedGoods){
                getSession().getGoodsDbBeanDao().delete(existedGoods);
            }
        }else{
            existedGoods = (GoodsDbBean) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id)
                    , Properties.Nature.eq(gson.toJson(goods.nature)))
                    .build().unique();
            if(null!=existedGoods){
                GoodsInfo dbGoods=gson.fromJson(existedGoods.getGoodsInfo(),GoodsInfo.class);
                dbGoods.count--;
                if(dbGoods.count==0){
                    getSession().getGoodsDbBeanDao().delete(existedGoods);
                }else{
                    Log.e("deleteGoods的数量", ""+ dbGoods.count);
                    existedGoods.setGoodsInfo(gson.toJson(dbGoods));
                    getSession().getGoodsDbBeanDao().update(existedGoods);
                }
            }
        }
    }
    //直接删除商品(包含套餐)
    public void deleteGoodsById(String id) {
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        ArrayList<GoodsDbBean> existedGoods = ( ArrayList<GoodsDbBean>) qb.where(Properties.GoodsId.eq(id)).build().list();
        if(existedGoods.size()>0){
            for(GoodsDbBean goodsDbBean:existedGoods){
                getSession().getGoodsDbBeanDao().delete(goodsDbBean);
            }
        }
    }

    //直接删除商品(包含套餐)
    public void deleteGoodsAll(GoodsListBean.GoodsInfo goods) {
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        ArrayList<GoodsDbBean> existedGoods = ( ArrayList<GoodsDbBean>) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id)).build().list();
        if(existedGoods.size()>0){
            for(GoodsDbBean goodsDbBean:existedGoods){
                getSession().getGoodsDbBeanDao().delete(goodsDbBean);
            }
        }
    }
    public void updateGoods(GoodsListBean.GoodsInfo goods){
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        GoodsDbBean existedGoods = null;
        if(goods.nature.size()>0){
            existedGoods = (GoodsDbBean) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id)
                    , Properties.Nature.eq(gson.toJson(goods.nature)), Properties.Index.eq(goods.index))
                    .build().unique();
        }else{
            existedGoods = (GoodsDbBean) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id)
                    , Properties.Nature.eq(gson.toJson(goods.nature)))
                    .build().unique();
        }
        if (null != existedGoods) {
            existedGoods.setGoodsInfo(gson.toJson(goods));
            getSession().getGoodsDbBeanDao().update(existedGoods);
        }
    }

    //获取购物车所有商品数量
    public int getAllGoodsNum() {
        List<GoodsDbBean> goodsDbBeanList = getSession().getGoodsDbBeanDao().loadAll();
        int num=0;
        for (GoodsDbBean goodsDbBean : goodsDbBeanList) {
            GoodsListBean.GoodsInfo goodsInfo=gson.fromJson(goodsDbBean.getGoodsInfo(), GoodsListBean.GoodsInfo.class);
            num+=goodsInfo.count;
        }
        return num;
    }


    //根据good_id查询店铺某商品
    public ArrayList<GoodsListBean.GoodsInfo> getGoodsFromDb(String shop_id, String good_id) {
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        ArrayList<GoodsDbBean> existedGoods = (ArrayList<GoodsDbBean>) qb.where(Properties.ShopId.eq(shop_id), Properties.GoodsId.eq(good_id), Properties.Type.eq("0"))
                .build().list();
        ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
        if (null != existedGoods && existedGoods.size() > 0) {
            for (GoodsDbBean bean : existedGoods) {
                goodsList.add(new Gson().fromJson(bean.getGoodsInfo(), GoodsListBean.GoodsInfo.class));
            }
        }
        return goodsList;
    }


    //根据good_id查询店铺某套餐
    public ArrayList<GoodsListBean.GoodsInfo> getPackageFromDb(String shop_id, String good_id) {
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        ArrayList<GoodsDbBean> existedGoods = (ArrayList<GoodsDbBean>) qb.where(Properties.ShopId.eq(shop_id), Properties.GoodsId.eq(good_id), Properties.Type.eq("1"))
                .build().list();
        ArrayList<GoodsListBean.GoodsInfo> packages = new ArrayList<>();
        if (null != existedGoods && existedGoods.size() > 0) {
            for (GoodsDbBean bean : existedGoods) {
                packages.add(new Gson().fromJson(bean.getGoodsInfo(), GoodsListBean.GoodsInfo.class));
            }
        }
        return packages;
    }



    //删除商品(包含套餐)
    public void deleteGoodsById(GoodsListBean.GoodsInfo goods) {
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        ArrayList<GoodsDbBean> existedGoodsList = null;
        if ("taocan".equals(goods.type_id)) {
            existedGoodsList = (ArrayList<GoodsDbBean>) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id)
                    , Properties.Type.eq("1")).build().list();
        } else {
            existedGoodsList = (ArrayList<GoodsDbBean>) qb.where(Properties.ShopId.eq(goods.shop_id), Properties.GoodsId.eq(goods.id)
                    , Properties.Type.eq("0")).build().list();
        }
        if (null != existedGoodsList && existedGoodsList.size() > 0) {
            for (GoodsDbBean bean : existedGoodsList) {
                getSession().getGoodsDbBeanDao().delete(bean);
            }
        }
    }

    //删除商品
    public void deleteGoodsByShopId(String shop_id) {
        QueryBuilder qb = getSession().getGoodsDbBeanDao().queryBuilder();
        ArrayList<GoodsDbBean> list = (ArrayList<GoodsDbBean>) qb.where(Properties.ShopId.eq(shop_id))
                .build().list();
        if (null != list && list.size() > 0) {
            for (GoodsDbBean bean : list) {
                getSession().getGoodsDbBeanDao().delete(bean);
            }
        }
    }


    //获取所有goods
    public ArrayList<String> getSelectShop() {
        List<GoodsDbBean> goodsDbBeanList = getSession().getGoodsDbBeanDao().loadAll();
        ArrayList<String> shopList = new ArrayList<>();
        for (GoodsDbBean goodsDbBean : goodsDbBeanList) {
            shopList.add(goodsDbBean.getShopId());

        }
        return shopList;
    }

    //获取所有商品(用来显示购物车数量)
    public ArrayList<GoodsInfo> getAllGoodsInfo(){
        List<GoodsDbBean> goodsDbBeanList = getSession().getGoodsDbBeanDao().loadAll();
        ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
        for (GoodsDbBean goodsDbBean : goodsDbBeanList) {
            goodsList.add(gson.fromJson(goodsDbBean.getGoodsInfo(), GoodsListBean.GoodsInfo.class));
        }
        return goodsList;
    }

    //获取所有商品
    public ArrayList<GoodsListBean.GoodsInfo> getAllGoods() {
        List<GoodsDbBean> goodsDbBeanList = getSession().getGoodsDbBeanDao().queryBuilder().list();
        ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
        for (GoodsDbBean goodsDbBean : goodsDbBeanList) {
            goodsList.add(gson.fromJson(goodsDbBean.getGoodsInfo(), GoodsListBean.GoodsInfo.class));
        }
        return goodsList;
    }

    public ArrayList<GoodsListBean.GoodsInfo> getAllGoods(String shopid) {
        List<GoodsDbBean> goodsDbBeanList = getSession().getGoodsDbBeanDao().queryBuilder().where(Properties.ShopId.eq(shopid)).list();
        ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
        for (GoodsDbBean goodsDbBean : goodsDbBeanList) {
            goodsList.add(gson.fromJson(goodsDbBean.getGoodsInfo(), GoodsListBean.GoodsInfo.class));
        }
        return goodsList;
    }
    public ArrayList<GoodsListBean.GoodsInfo> getGoodsInfoById(String id) {
        List<GoodsDbBean> goodsDbBeanList = getSession().getGoodsDbBeanDao().queryBuilder().where(Properties.GoodsId.eq(id)).list();
        ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
        for (GoodsDbBean goodsDbBean : goodsDbBeanList) {
            GoodsListBean.GoodsInfo info = gson.fromJson(goodsDbBean.getGoodsInfo(), GoodsListBean.GoodsInfo.class);
            goodsList.add(info);
        }
        return goodsList;
    }
    //获取所有商品
    public ArrayList<GoodsListBean.GoodsInfo> getGoodsListByShopId(String shop_id) {
        List<GoodsDbBean> goodsDbBeanList = getSession().getGoodsDbBeanDao().queryBuilder().where(Properties.ShopId.eq(shop_id)).list();
        ArrayList<GoodsListBean.GoodsInfo> goodsList = new ArrayList<>();
        for (GoodsDbBean goodsDbBean : goodsDbBeanList) {
            GoodsListBean.GoodsInfo info = gson.fromJson(goodsDbBean.getGoodsInfo(), GoodsListBean.GoodsInfo.class);
            if (info != null && info.shop_id.equals(shop_id)) {
                goodsList.add(info);
            }
        }
        return goodsList;
    }
}
