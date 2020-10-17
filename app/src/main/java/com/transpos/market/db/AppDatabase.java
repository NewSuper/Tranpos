package com.transpos.market.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.transpos.market.base.BaseApp;
import com.transpos.market.db.dao.LineSalesSettingDao;
import com.transpos.market.db.dao.LineSystemSetDao;
import com.transpos.market.db.dao.MemberLevelCategoryDiscountDao;
import com.transpos.market.db.dao.MemberLevelDao;
import com.transpos.market.db.dao.MemberPointRuleBrandDao;
import com.transpos.market.db.dao.MemberPointRuleCategoryDao;
import com.transpos.market.db.dao.MemberPointRuleDao;
import com.transpos.market.db.dao.OrderItemDao;
import com.transpos.market.db.dao.OrderItemPayDao;
import com.transpos.market.db.dao.OrderItemPromotionDao;
import com.transpos.market.db.dao.OrderObjectDao;
import com.transpos.market.db.dao.OrderPayDao;
import com.transpos.market.db.dao.PayModeDao;
import com.transpos.market.db.dao.PaymentParameterDao;
import com.transpos.market.db.dao.ProductBrandDao;
import com.transpos.market.db.dao.ProductCategroyDao;
import com.transpos.market.db.dao.ProductCodeDao;
import com.transpos.market.db.dao.ProductContactDao;
import com.transpos.market.db.dao.ProductDao;
import com.transpos.market.db.dao.ProductSpecDao;
import com.transpos.market.db.dao.ProductUnitDao;
import com.transpos.market.db.dao.PromotionOrderDao;
import com.transpos.market.db.dao.ShortCutsDao;
import com.transpos.market.db.dao.StoreInfoDao;
import com.transpos.market.db.dao.StoreProductDao;
import com.transpos.market.db.dao.SupplierDao;
import com.transpos.market.db.dao.WorkerDao;
import com.transpos.market.db.dao.WorkerDataDao;
import com.transpos.market.db.dao.WorkerModuleDao;
import com.transpos.market.db.dao.WorkerRoleDao;
import com.transpos.market.entity.LineSalesSetting;
import com.transpos.market.entity.LineSystemSet;
import com.transpos.market.entity.MemberLevel;
import com.transpos.market.entity.MemberLevelCategoryDiscount;
import com.transpos.market.entity.MemberPointRule;
import com.transpos.market.entity.MemberPointRuleBrand;
import com.transpos.market.entity.MemberPointRuleCategory;
import com.transpos.market.entity.OrderItem;
import com.transpos.market.entity.OrderItemPay;
import com.transpos.market.entity.OrderObject;
import com.transpos.market.entity.OrderPay;
import com.transpos.market.entity.PayMode;
import com.transpos.market.entity.PaymentParameter;
import com.transpos.market.entity.Product;
import com.transpos.market.entity.ProductBrand;
import com.transpos.market.entity.ProductCategory;
import com.transpos.market.entity.ProductCode;
import com.transpos.market.entity.ProductContact;
import com.transpos.market.entity.ProductSpec;
import com.transpos.market.entity.ProductUnit;
import com.transpos.market.entity.PromotionItem;
import com.transpos.market.entity.PromotionOrder;
import com.transpos.market.entity.ShortCuts;
import com.transpos.market.entity.StoreInfo;
import com.transpos.market.entity.StoreProduct;
import com.transpos.market.entity.Supplier;
import com.transpos.market.entity.WorkData;
import com.transpos.market.entity.WorkModule;
import com.transpos.market.entity.WorkRole;
import com.transpos.market.entity.Worker;


import java.io.File;

//entities表示要包含哪些表；version为数据库的版本，数据库升级时更改；exportSchema是否导出数据库结构，默认为true
@Database(entities = {ProductBrand.class, ProductCategory.class, ProductUnit.class,
        Product.class, ProductCode.class, ProductContact.class, ProductSpec.class, StoreProduct.class,
        Worker.class, Supplier.class, PayMode.class, StoreInfo.class, LineSystemSet.class,
        MemberLevel.class, MemberLevelCategoryDiscount.class, MemberPointRule.class,
        MemberPointRuleCategory.class, MemberPointRuleBrand.class, PaymentParameter.class,
        LineSalesSetting.class, OrderObject.class, OrderItem.class, PromotionItem.class,
        OrderItemPay.class, OrderPay.class, PromotionOrder.class, ShortCuts.class, WorkData.class,
        WorkModule.class, WorkRole.class
},
        version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    //    外部存储 应用卸载数据也丢弃
    private static final String db_path = BaseApp.getApplication().getExternalFilesDir(null).getPath()+ File.separator+"db_store/pos.db";

    //单例
    public static AppDatabase getDatabase(){
        return Holder.instance;
    }

    private static class Holder{
        private static final AppDatabase instance = Room.databaseBuilder(BaseApp.getApplication(), AppDatabase.class, db_path)
                .allowMainThreadQueries()   //设置允许在主线程进行数据库操作，默认不允许，建议都设置为默认
                // .fallbackToDestructiveMigration()  //设置数据库升级的时候清除之前的所有数据
//                .addMigrations(MIGRATION_1_2)
                .build();
    }

    public abstract ProductBrandDao getProductBrandDao();

    public abstract ProductCategroyDao getProductCategroyDao();

    public abstract ProductUnitDao getProductUnitDao();

    public abstract ProductDao getProductDao();

    public abstract ProductCodeDao getProductCodeDao();

    public abstract ProductContactDao getProcuctContractDao();

    public abstract ProductSpecDao getProductSpecDao();

    public abstract StoreProductDao getStoreProductDao();

    public abstract WorkerDao getWorkerDao();

    public abstract SupplierDao getSupplierDao();

    public abstract PayModeDao getPayModeDao();

    public abstract StoreInfoDao getStoreInfoDao();

    public abstract LineSystemSetDao getLineSystemSetDao();

    public abstract MemberLevelDao getMemberLevelDao();

    public abstract MemberLevelCategoryDiscountDao getMemberLevelCategoryDiscountDao();

    public abstract MemberPointRuleDao getMemberPointRuleDao();

    public abstract MemberPointRuleCategoryDao getMemberPointRuleCategoryDao();

    public abstract MemberPointRuleBrandDao getMemberPointRuleBrandDao();

    public abstract PaymentParameterDao getPaymentParamterDao();

    public abstract LineSalesSettingDao getLineSalesSettingDao();

    public abstract OrderObjectDao getOrderObjectDao();

    public abstract OrderItemDao getOrderItemDao();

    public abstract OrderItemPromotionDao getOrderItemPromotionDao();

    public abstract OrderItemPayDao getOrderItemPayDao();

    public abstract OrderPayDao getOrderPayDao();

    public abstract PromotionOrderDao getPromotionOrderDao();

    public abstract ShortCutsDao getShortCutsDao();

    public abstract WorkerDataDao getWorkerDataDao();

    public abstract WorkerModuleDao getWorkerModuleDao();

    public abstract WorkerRoleDao getWorkerRoleDao();


    /**
     * 数据库升级  version1 -> version2
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //向student表中添加一个sport字段
            //database.execSQL("ALTER TABLE student ADD COLUMN sport INTEGER"); //这种方式不支持一次添加多个字段，可以多写几条sql语句分别执行
//            String sql = "CREATE TABLE IF NOT EXISTS  pos_order(id VARCHAR (24) NOT NULL UNIQUE, tenantId VARCHAR (16) NOT NULL, objectId VARCHAR (32), orderNo VARCHAR (16), tradeNo VARCHAR (32), storeId VARCHAR (24), storeNo VARCHAR (24), storeName VARCHAR (512), workerNo VARCHAR (24), workerName VARCHAR (24), saleDate VARCHAR (32), finishDate VARCHAR (32), tableNo VARCHAR (32), tableName VARCHAR (64), salesCode VARCHAR (128), salesName VARCHAR (128), posNo VARCHAR (24), deviceName VARCHAR (64), macAddress VARCHAR (128), ipAddress VARCHAR (24), itemCount INT, payCount INT, totalQuantity DECIMAL (24, 4), amount DECIMAL (24, 4), discountAmount DECIMAL (24, 4), receivableAmount DECIMAL (24, 4), paidAmount DECIMAL (24, 4), receivedAmount DECIMAL (24, 4), malingAmount DECIMAL (24, 4), changeAmount DECIMAL (24, 4), invoicedAmount DECIMAL (24, 4), overAmount DECIMAL (24, 4), orderStatus INT, paymentStatus INT, printStatus INT, printTimes INT, postWay INT, orderSource INT (32), people INT, shiftId VARCHAR (24), shiftNo VARCHAR (32), shiftName VARCHAR (32), discountRate DECIMAL (24, 4), orgTradeNo VARCHAR (32), refundCause VARCHAR (512), isMember INTEGER, memberNo VARCHAR (32), memberName VARCHAR (32), memberMobileNo VARCHAR (32), cardFaceNo VARCHAR (64), prePoint DECIMAL (24, 4) DEFAULT 0, addPoint DECIMAL (24, 4), refundPoint DECIMAL (24, 4) DEFAULT (0), aftPoint DECIMAL (24, 4), aftAmount DECIMAL (24, 4) DEFAULT (0), uploadStatus INT, uploadErrors INT, uploadCode VARCHAR (32), uploadMessage VARCHAR (128), serverId VARCHAR (32), uploadTime VARCHAR (32), weather VARCHAR (24), weeker VARCHAR (24), remark VARCHAR (256), ext1 VARCHAR (32), ext2 VARCHAR (32), ext3 VARCHAR (32), createUser VARCHAR (16), createDate VARCHAR (32), modifyUser VARCHAR (16), modifyDate VARCHAR (32), memberId VARCHAR (32), receivableRemoveCouponAmount decimal(24,4), isPlus int, plusDiscountAmount decimal(24, 4))";
//            database.execSQL(sql);

        }
    };

    /**
     * 数据库升级  version2 -> version3
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //向student表中添加一个physical字段
            //database.execSQL("ALTER TABLE student ADD COLUMN physical INTEGER");
        }
    };
}

