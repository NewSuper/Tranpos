package com.newsuper.t.markert.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.newsuper.t.markert.base.BaseApp;
import com.newsuper.t.markert.db.dao.LineSalesSettingDao;
import com.newsuper.t.markert.db.dao.LineSystemSetDao;
import com.newsuper.t.markert.db.dao.MemberLevelCategoryDiscountDao;
import com.newsuper.t.markert.db.dao.MemberLevelDao;
import com.newsuper.t.markert.db.dao.MemberPointRuleBrandDao;
import com.newsuper.t.markert.db.dao.MemberPointRuleCategoryDao;
import com.newsuper.t.markert.db.dao.MemberPointRuleDao;
import com.newsuper.t.markert.db.dao.OrderItemDao;
import com.newsuper.t.markert.db.dao.OrderItemPayDao;
import com.newsuper.t.markert.db.dao.OrderItemPromotionDao;
import com.newsuper.t.markert.db.dao.OrderObjectDao;
import com.newsuper.t.markert.db.dao.OrderPayDao;
import com.newsuper.t.markert.db.dao.PayModeDao;
import com.newsuper.t.markert.db.dao.PaymentParameterDao;
import com.newsuper.t.markert.db.dao.ProductBrandDao;
import com.newsuper.t.markert.db.dao.ProductCategroyDao;
import com.newsuper.t.markert.db.dao.ProductCodeDao;
import com.newsuper.t.markert.db.dao.ProductContactDao;
import com.newsuper.t.markert.db.dao.ProductDao;
import com.newsuper.t.markert.db.dao.ProductSpecDao;
import com.newsuper.t.markert.db.dao.ProductUnitDao;
import com.newsuper.t.markert.db.dao.PromotionOrderDao;
import com.newsuper.t.markert.db.dao.ShortCutsDao;
import com.newsuper.t.markert.db.dao.StoreInfoDao;
import com.newsuper.t.markert.db.dao.StoreProductDao;
import com.newsuper.t.markert.db.dao.SupplierDao;
import com.newsuper.t.markert.db.dao.WorkerDao;
import com.newsuper.t.markert.db.dao.WorkerDataDao;
import com.newsuper.t.markert.db.dao.WorkerModuleDao;
import com.newsuper.t.markert.db.dao.WorkerRoleDao;
import com.newsuper.t.markert.entity.LineSalesSetting;
import com.newsuper.t.markert.entity.LineSystemSet;
import com.newsuper.t.markert.entity.MemberLevel;
import com.newsuper.t.markert.entity.MemberLevelCategoryDiscount;
import com.newsuper.t.markert.entity.MemberPointRule;
import com.newsuper.t.markert.entity.MemberPointRuleBrand;
import com.newsuper.t.markert.entity.MemberPointRuleCategory;
import com.newsuper.t.markert.entity.OrderItem;
import com.newsuper.t.markert.entity.OrderItemPay;
import com.newsuper.t.markert.entity.OrderObject;
import com.newsuper.t.markert.entity.OrderPay;
import com.newsuper.t.markert.entity.PayMode;
import com.newsuper.t.markert.entity.PaymentParameter;
import com.newsuper.t.markert.entity.Product;
import com.newsuper.t.markert.entity.ProductBrand;
import com.newsuper.t.markert.entity.ProductCategory;
import com.newsuper.t.markert.entity.ProductCode;
import com.newsuper.t.markert.entity.ProductContact;
import com.newsuper.t.markert.entity.ProductSpec;
import com.newsuper.t.markert.entity.ProductUnit;
import com.newsuper.t.markert.entity.PromotionItem;
import com.newsuper.t.markert.entity.PromotionOrder;
import com.newsuper.t.markert.entity.ShortCuts;
import com.newsuper.t.markert.entity.StoreInfo;
import com.newsuper.t.markert.entity.StoreProduct;
import com.newsuper.t.markert.entity.Supplier;
import com.newsuper.t.markert.entity.WorkData;
import com.newsuper.t.markert.entity.WorkModule;
import com.newsuper.t.markert.entity.WorkRole;
import com.newsuper.t.markert.entity.Worker;


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

