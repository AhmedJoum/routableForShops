package com.yazan.roatableShops.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yazan.roatableShops.models.SalePoint;
import com.yazan.roatableShops.models.Shop;
import com.yazan.roatableShops.models.ShopProductCode;
import com.yazan.roatableShops.models.Visit;
import com.yazan.roatableShops.models.VisitResult;

/**
 * Created by Carsiow on 11/14/16.
 */

public class DAO extends SQLiteOpenHelper {
    public static final String TABLE_SALE_POINT = "sale_point";
    public static final String COLUMN_SP_CODE = "sp_code";
    public static final String COLUMN_SP_NAME = "shop_name";
    public static final String COLUMN_SP_ADDRESS = "sp_address";
    public static final String COLUMN_SP_OWNER = "sp_owner";
    public static final String COLUMN_SP_PHONE = "sp_phone";
    public static final String COLUMN_SP_TYPE = "sp_type";
    public static final String COLUMN_STREET_TYPE = "street_type";
    public static final String COLUMN_BLOCK_TYPE = "block_type";
    public static final String COLUMN_ROUTE_DESC = "route_desc";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_ZIM = "zim";
    public static final String COLUMN_EVD = "evd";


    public static final String TABLE_SHOP = "shop";
    public static final String COLUMN_SHOP_CODE = "shop_code";
    public static final String COLUMN_SHOP_NAME = "shop_name";
    public static final String COLUMN_SHOP_TYPE = "shop_type";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_LAST_CHECK_DATE = "last_check_date";
    public static final String COLUMN_CITY_CODE = "city_code";
    public static final String COLUMN_SYNCED = "synced";

    public static final String TABLE_VISITE = "visite";
    public static final String COLUMN_VISIT_DATE = "visit_date";
    public static final String COLUMN_VISIT_ID = "visit_id";
    public static final String COLUMN_HOLDER_QTY = "holder_qty";
    public static final String COLUMN_STAND_QTY = "stand_qty";
    public static final String COLUMN_STAND_TYPES = "stand_types";

    public static final String TABLE_VISITE_RESULT = "visite_result";
    public static final String COLUMN_AD_CODE = "ad_code";
    public static final String COLUMN_AD_QTY = "ad_qty";
    public static final String COLUMN_AD_WHEN = "ad_when";

    public static final String TABLE_SHOP_PRODUCT_CODE = "shop_product_code";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_AD_TYPE = "ad_type";
    public static final String COLUMN_POINT_TYPE = "point_type";

    private SQLiteDatabase mDatabase;

    public DAO(Context context) {
        super(context, "roatableShops", null, 11);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_SALE_POINT + " " +
                "( " + COLUMN_SP_CODE + " text, " +
                "  " + COLUMN_SP_ADDRESS + " text, " +
                "  " + COLUMN_SP_NAME + " text,  " +
                "  " + COLUMN_SP_OWNER + " text, " +
                "  " + COLUMN_SP_PHONE + " text, " +
                "  " + COLUMN_SP_TYPE + " integer, " +
                "  " + COLUMN_STREET_TYPE + " integer, " +
                "  " + COLUMN_BLOCK_TYPE + " integer, " +
                "  " + COLUMN_ROUTE_DESC + "  text, " +
                "  " + COLUMN_LAT + " text, " +
                "  " + COLUMN_LNG + " text, " +
                "  " + COLUMN_ICON + " integer,  " +
                "  " + COLUMN_ZIM + " integer, " +
                "  " + COLUMN_EVD + " integer, " +
                "  " + COLUMN_SYNCED + " int )");


        db.execSQL("CREATE TABLE " +
                " " + TABLE_SHOP + " " +
                "( " + COLUMN_SHOP_CODE + " text, " +
                "  " + COLUMN_SHOP_NAME + " text,  " +
                "  " + COLUMN_SHOP_TYPE + " integer, " +
                "  " + COLUMN_LAT + " text, " +
                "  " + COLUMN_LNG + " text, " +
                "  " + COLUMN_LAST_CHECK_DATE + " text, " +
                "  " + COLUMN_CITY_CODE + ", " +
                "  " + COLUMN_SYNCED + " int )");

        db.execSQL(" CREATE TABLE " + TABLE_VISITE + " " +
                "(  " +
                "  " + COLUMN_SHOP_CODE + " text , " +
                "  " + COLUMN_VISIT_DATE + " text ,  " +
                "  " + COLUMN_VISIT_ID + " text , " +
                "  " + COLUMN_HOLDER_QTY + " text , " +
                "  " + COLUMN_STAND_QTY + " text, " +
                "  " + COLUMN_STAND_TYPES + " text , " +
                "  " + COLUMN_SYNCED + " int ) ");

        db.execSQL("CREATE TABLE " + TABLE_VISITE_RESULT + " " +
                "( " + COLUMN_VISIT_ID + " text," +
                "  " + COLUMN_AD_CODE + " text, " +
                "  " + COLUMN_AD_QTY + " text,  " +
                "  " + COLUMN_AD_WHEN + " text, " +
                "  " + COLUMN_SYNCED + " int )");

        db.execSQL("CREATE TABLE " + TABLE_SHOP_PRODUCT_CODE + "  " +
                " (" + COLUMN_PRODUCT_NAME + " text,  " +
                " " + COLUMN_AD_CODE + " text, " +
                " " + COLUMN_AD_TYPE + " text, " +
                " " + COLUMN_POINT_TYPE + " text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP_PRODUCT_CODE + " ");

        db.execSQL("CREATE TABLE " + TABLE_SHOP_PRODUCT_CODE + "  " +
                " (" + COLUMN_PRODUCT_NAME + " text,  " +
                " " + COLUMN_AD_CODE + " text, " +
                " " + COLUMN_AD_TYPE + " text, " +
                " " + COLUMN_POINT_TYPE + " text)");
    }


    public boolean insertSalePoint(SalePoint salePoint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SP_CODE, salePoint.getSpCode());
        contentValues.put(COLUMN_SP_ADDRESS, salePoint.getSpAddress());
        contentValues.put(COLUMN_SP_NAME, salePoint.getSpName());
        contentValues.put(COLUMN_SP_OWNER, salePoint.getSpOwnerName());
        contentValues.put(COLUMN_SP_PHONE, salePoint.getSpPhone());
        contentValues.put(COLUMN_SP_TYPE, salePoint.getSpType());
        contentValues.put(COLUMN_STREET_TYPE, salePoint.getStreet_type());
        contentValues.put(COLUMN_BLOCK_TYPE, salePoint.getBlock_type());
        contentValues.put(COLUMN_ROUTE_DESC, salePoint.getRoute_desc());
        contentValues.put(COLUMN_LAT, salePoint.getLat());
        contentValues.put(COLUMN_LNG, salePoint.getLng());
        contentValues.put(COLUMN_ICON, salePoint.getICON());
        contentValues.put(COLUMN_ZIM, salePoint.getZIM());
        contentValues.put(COLUMN_EVD, salePoint.getEVD());
        contentValues.put(COLUMN_SYNCED, 0);

        Cursor res = db.rawQuery("select * from " + TABLE_SALE_POINT + "  " +
                " where " + COLUMN_SP_CODE + "  = '" +
                salePoint.getSpCode() + "' ", null);

        if (res.getCount() == 0) {
            db.insert(TABLE_SALE_POINT, null, contentValues);

            return true;
        } else {
            db.update(TABLE_SALE_POINT,
                    contentValues, "sp_code = " + "'" + salePoint.getSpCode() + "'", null);
            return true;
        }
    }

    public boolean insertShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SHOP_CODE, shop.getSpCode());
        contentValues.put(COLUMN_SHOP_NAME, shop.getSpName());
        contentValues.put(COLUMN_LAT, shop.getLat());
        contentValues.put(COLUMN_LNG, shop.getLng());
        contentValues.put(COLUMN_SYNCED, 0);
        contentValues.put(COLUMN_SHOP_TYPE, shop.getSpType());
        contentValues.put(COLUMN_LAST_CHECK_DATE, shop.getLastCheckDate());
        contentValues.put(COLUMN_CITY_CODE, shop.getCityCode());

        Cursor res = db.rawQuery("select " + COLUMN_SHOP_CODE + " from " + TABLE_SHOP +
                " where " + COLUMN_SHOP_CODE + "  = '" +
                shop.getSpCode() + "' ", null);

        if (res.getCount() == 0) {
            db.insert(TABLE_SHOP, null, contentValues);

            return true;
        } else {
            db.update(TABLE_SHOP, contentValues, COLUMN_SHOP_CODE + " = " + "'" + shop.getSpCode() + "'", null);
            return true;
        }
    }

    public boolean insertShopProductCode(ShopProductCode spc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, spc.getProductName());
        contentValues.put(COLUMN_AD_CODE, spc.getAdCode());
        contentValues.put(COLUMN_AD_TYPE, spc.getAdType());
        contentValues.put(COLUMN_POINT_TYPE, spc.getPointType());

        Cursor res = db.rawQuery("select * from " + TABLE_SHOP_PRODUCT_CODE + " where ad_code  = '"
                + spc.getAdCode() + "' ", null);

        if (res.getCount() == 0) {
            db.insert(TABLE_SHOP_PRODUCT_CODE, null, contentValues);
            return true;
        } else {
            db.update(TABLE_SHOP_PRODUCT_CODE, contentValues, " " + COLUMN_AD_CODE + " = '"
                    + spc.getAdCode() + "' ", null);
            return true;
        }
    }

    public void setSynced(String sp_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("synced", 1);
        db.update("sale_point", contentValues, "sp_code = " + "'" + sp_code + "'", null);
    }

    /**
     * I'm Sorry for write it wrongly
     */
    public Cursor getSalePoints() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from sale_point ", null); //where synced = '0'
        return res;
    }


    public Cursor getSalePoint_(String sp_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from sale_point where sp_code = '" + sp_code + "'", null);
        return res;
    }


    public Cursor getShops() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + COLUMN_SHOP_CODE + " " +
                " , " + COLUMN_SHOP_NAME + "" +
                " , " + COLUMN_SHOP_TYPE + "" +
                " , " + COLUMN_LAT + "" +
                " , " + COLUMN_LNG + "" +
                " , " + COLUMN_LAST_CHECK_DATE + "" +
                " , " + COLUMN_CITY_CODE + "" +
                " from " + TABLE_SHOP + "  ", null); //where synced = '0'
        return res;
    }


    public boolean insertVisit(Visit visit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COLUMN_SHOP_CODE, visit.getShop() != null ?
                visit.getShop().getSpCode() : visit.getSalePoint().getSpCode());
        contentValues.put(COLUMN_VISIT_DATE, visit.getVisit_date());
        contentValues.put(COLUMN_VISIT_ID, visit.getVisit_id());
        contentValues.put(COLUMN_HOLDER_QTY, visit.getHolderQty());
        contentValues.put(COLUMN_STAND_QTY, visit.getStandQty());
        contentValues.put(COLUMN_STAND_TYPES, visit.getStandTypes());
        contentValues.put(COLUMN_SYNCED, 0);


        db.insert(TABLE_VISITE, null, contentValues);

        return true;

    }

    public void setSyncedV(String visit_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("synced", 1);
        db.update(TABLE_VISITE, contentValues, COLUMN_VISIT_ID + " = " + "'" + visit_id + "'", null);

    }


    public Cursor getVisits() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + COLUMN_SHOP_CODE + " " +
                " , " + COLUMN_VISIT_DATE + " " +
                " , " + COLUMN_VISIT_ID + " " +
                " , " + COLUMN_HOLDER_QTY + "  " +
                " , " + COLUMN_STAND_QTY + " " +
                " , " + COLUMN_STAND_TYPES +
                " from " + TABLE_VISITE + " ", null); //where synced = '0'
        return res;
    }


    public boolean insertVisitResult(VisitResult visitResult) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_VISIT_ID, visitResult.getVisit_id());
        contentValues.put(COLUMN_AD_CODE, visitResult.getAd_Code());
        contentValues.put(COLUMN_AD_QTY, visitResult.getAd_QTY());
        contentValues.put(COLUMN_AD_WHEN, visitResult.getWhen());
        contentValues.put(COLUMN_SYNCED, 0);

        db.insert(TABLE_VISITE_RESULT, null, contentValues);

        return true;

    }

    public void setSyncedVR(String visit_id, String operater_id, String ad_type, String ad_state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("synced", 1);
        db.update("visite_result", contentValues, "visit_id = " + "'" + visit_id + "' ", null);

    }

    public Cursor getVisitResults() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + COLUMN_VISIT_ID + " " +
                " , " + COLUMN_AD_CODE + " " +
                " , " + COLUMN_AD_QTY + " " +
                " , " + COLUMN_AD_WHEN + " from " + TABLE_VISITE_RESULT + " ", null); //where synced = '0'
        return res;
    }


    public Cursor getAdCodes(String ad_type, String point_type) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(" select " + COLUMN_AD_CODE + " " +
                "  from " + TABLE_SHOP_PRODUCT_CODE + "  " +
                "  where " + COLUMN_AD_TYPE + " = '" + ad_type + "'  " +
                "  and  " + COLUMN_POINT_TYPE + " = '" + point_type + "'", null);
        return res;
    }


    public Cursor getCityCode() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(" select DISTINCT city_code from shop ", null);
        return res;
    }

    public Cursor getShopByCityCode(String CityCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(" select shop_name from shop where city_code = '" + CityCode + "' ", null);
        return res;
    }

    public Cursor getShopDetailsByName(String shopName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(" select shop_code, shop_name,  shop_type ,city_code " +
                "  from shop where shop_name = '" + shopName + "' ", null);
        return res;
    }

}


