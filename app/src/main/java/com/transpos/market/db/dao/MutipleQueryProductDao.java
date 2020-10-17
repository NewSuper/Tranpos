package com.transpos.market.db.dao;

import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.MultipleQueryProduct;


public interface MutipleQueryProductDao extends BaseDao<MultipleQueryProduct> {

    @Query("select p.`id`," +
            " p.`tenantId`, " +
            " p.`categoryId`, " +
            " p.`categoryPath`," +
            " p.`type`, " +
            " p.`no`, " +
            " p.`barCode`, " +
            " p.`subNo`," +
            " p.`otherNo`," +
            " p.`name`, " +
            " p.`english`, " +
            " p.`rem`," +
            " p.`shortName`, " +
            " p.`unitId`, " +
            " p.`brandId`, " +
            " p.`storageType`, " +
            " p.`storageAddress`, " +
            " sp.`supplierId`," +
            " sp.`managerType`, " +
            " p.`purchaseControl`," +
            " p.`purchaserCycle`, " +
            " p.`validDays`, " +
            " p.`productArea`, " +
            " p.`status`, " +
            " p.`spNum`, " +
            " sp.`stockFlag`, " +
            " p.`quickInventoryFlag`," +
            " p.`posSellFlag`," +
            " p .`batchStockFlag`," +
            " p.`weightFlag`," +
            " p.`weightWay`," +
            " p.`steelyardCode`," +
            " p.`labelPrintFlag`," +
            " sp.`foreDiscount`," +
            " sp.`foreGift`," +
            " p.`promotionFlag`," +
            " sp.`branchPrice`," +
            " sp.`foreBargain`," +
            " p.`returnType`," +
            " p.`returnRate`," +
            " sp.`pointFlag`," +
            " p.`pointValue`," +
            " p.`introduction`," +
            " p.`purchaseTax`," +
            " p.`saleTax`," +
            " p.`lyRate`," +
            " p.`allCode`," +
            " p.`deleteFlag`," +
            " p.`allowEditSup`," +
            " p.`ext1`," +
            " p.`ext2`," +
            " p.`ext3`," +
            " p.`createUser`," +
            " p.`createDate`," +
            " p.`modifyUser`," +
            " p.`modifyDate`," +
            " ps.specification as specName," +
            " ps.id as specId," +
            " pc.name as categoryName," +
            " pc.code as categoryNo," +
            " pu.name as unitName," +
            " pb.name as brandName," +
            " sp.batchPrice," +
            " sp.batchPrice2," +
            " sp.batchPrice3," +
            " sp.batchPrice4," +
            " sp.batchPrice5," +
            " sp.batchPrice6," +
            " sp.batchPrice7," +
            " sp.batchPrice8," +
            " sp.minPrice," +
            " sp.otherPrice," +
            " sp.postPrice," +
            " sp.purPrice," +
            " sp.salePrice," +
            " sp.vipPrice," +
            " sp.vipPrice2," +
            " sp.vipPrice3," +
            " sp.vipPrice4," +
            " sp.vipPrice5," +
            " ps.isDefault," +
            " ps.purchaseSpec," +
            " su.name as supplierName" +
            " from pos_product p " +
            " inner join pos_product_spec ps on p.id = ps.productId " +
            " inner join pos_store_product sp on ps.id = sp.specId " +
            " left join pos_product_unit pu on p.unitId = pu.id " +
            " left join pos_product_category pc on p.categoryId = pc.id " +
            " left join pos_product_brand pb on p.brandId = pb.id " +
            " left join pos_supplier su on sp.supplierId = su.id " +
            " where sp.status in (1, 2, 3) and ps.deleteFlag = 0 and p.barCode=:barCode order by p.categoryId, p.barCode;")
    MultipleQueryProduct queryOneByCode(String barCode);
}
