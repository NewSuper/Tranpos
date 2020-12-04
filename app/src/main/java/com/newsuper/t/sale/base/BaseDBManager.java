package com.newsuper.t.sale.base;

import java.util.List;

public abstract class BaseDBManager<T> {

    protected BaseDao<T> baseDao;

    public BaseDBManager() {
        this.baseDao = getDataBaseDao();
    }

    protected abstract BaseDao<T> getDataBaseDao();


    public long insert(T bean){
        return baseDao.insert(bean);
    }

    public List<Long> insert(T... beans){
        return baseDao.insert(beans);
    }

    public List<Long> insert(List<T> beans){
        return baseDao.insert(beans);
    }

    public int update(T bean){
        return baseDao.update(bean);
    }

    public int update(T... beans){
        return baseDao.update(beans);
    }

    public int update(List<T> beans){
        return baseDao.update(beans);
    }

    public List<T> loadAll(){
//        return baseDao.loadAll();
        return null;
    }

    public int delete(T bean){
        return baseDao.delete(bean);
    }

    public int delete(T ... beans){
        return baseDao.delete(beans);
    }

    public int deleteAll(){
//        return baseDao.deleteAll();
        return 0;
    }


}
