package com.example.shirouq_paints.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shirouq_paints.models.SalePoint;
import com.example.shirouq_paints.models.Visit;
import com.example.shirouq_paints.models.VisitResult;

/**
 * Created by Carsiow on 11/14/16.
 */

public class DAO extends SQLiteOpenHelper {

    private SQLiteDatabase mDatabase;

    public DAO(Context context) {
        super(context, "roatable", null, 5);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sale_point " +
                "( sp_code text, " +
                "  sp_address text, " +
                "  sp_name text,  " +
                "  sp_owner text, " +
                "  sp_phone text, " +
                "  sp_type integer, " +
                "  street_type integer, " +
                "  block_type integer, " +
                "  route_desc  text, " +
                "  lat text, " +
                "  lng text, " +
                "  icon integer,  " +
                "  zim integer, " +
                "  evd integer, " +
                "  synced int )");

        db.execSQL(" CREATE TABLE visite " +
                "( agent_id text , " +
                "  sp_code text , " +
                "  visit_date text ,  " +
                "  visit_id text , " +
                "  synced int ) ");

        db.execSQL("CREATE TABLE visite_result " +
                "( visit_id text," +
                "  operater_id text, " +
                "  ad_type integer,  " +
                "  ad_state integer, " +
                "  bad_state_reason integer, " +
                "  ad_no integer , " +
                "  synced int )");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS sale_point");
//        db.execSQL("DROP TABLE IF EXISTS visite");
//        db.execSQL("DROP TABLE IF EXISTS visite_result");
//
//        onCreate(db);
    }

    public boolean insertSalePoint(SalePoint salePoint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("sp_code", salePoint.getSpCode());
        contentValues.put("sp_address", salePoint.getSpAddress());
        contentValues.put("sp_name", salePoint.getSpName());
        contentValues.put("sp_owner", salePoint.getSpOwnerName());
        contentValues.put("sp_phone", salePoint.getSpPhone());
        contentValues.put("sp_type", salePoint.getSpType());
        contentValues.put("street_type", salePoint.getStreet_type());
        contentValues.put("block_type", salePoint.getBlock_type());
        contentValues.put("route_desc", salePoint.getRoute_desc());
        contentValues.put("lat", salePoint.getLat());
        contentValues.put("lng", salePoint.getLng());
        contentValues.put("icon", salePoint.getICON());
        contentValues.put("zim", salePoint.getZIM());
        contentValues.put("evd", salePoint.getEVD());
        contentValues.put("synced", 0);

        Cursor res = db.rawQuery("select * from sale_point where sp_code  = '" +
                salePoint.getSpCode() + "' ", null);

        if (res.getCount() == 0) {
            db.insert("sale_point", null, contentValues);

            return true;
        } else {
            db.update("sale_point", contentValues, "sp_code = " + "'" + salePoint.getSpCode() + "'", null);
            return true;
        }
    }

    public void setSynced(String sp_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("synced", 1);
        db.update("sale_point", contentValues, "sp_code = " + "'" + sp_code + "'", null);
    }

    public Cursor getSalePoints() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from sale_point  ", null); //where synced = '0'
        return res;
    }


    public Cursor getSalePoint_(String sp_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from sale_point where sp_code = '"+ sp_code +"'", null);
        return res;
    }


    public boolean insertVisit(Visit visit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("agent_id", visit.getAgent().getA_id());
        contentValues.put("sp_code", visit.getSalePoint().getSpCode());
        contentValues.put("visit_date", "" + visit.getVisit_date());
        contentValues.put("visit_id", visit.getVisit_id());
        contentValues.put("synced", 0);


        db.insert("visite", null, contentValues);

        return true;

    }

    public void setSyncedV(String visit_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("synced", 1);
        db.update("visite", contentValues, "visit_id = " + "'" + visit_id + "'" , null);

    }


    public Cursor getVisits()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from visite ", null); //where synced = '0'
        return  res;
    }


    public boolean insertVisitResult(VisitResult visitResult) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("visit_id", visitResult.getVisit_id());
        contentValues.put("operater_id", visitResult.getOperater_id());
        contentValues.put("ad_type", "" + visitResult.getAd_type());
        contentValues.put("ad_state", visitResult.getAd_state());
        contentValues.put("bad_state_reason", visitResult.getBad_state_reason());
        contentValues.put("ad_no", visitResult.getAd_no());
        contentValues.put("synced", 0);

        db.insert("visite_result ", null, contentValues);

        return true;

    }

    public void setSyncedVR(String visit_id, String operater_id, String ad_type, String ad_state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("synced", 1);
        db.update("visite_result", contentValues, "visit_id = " + "'" + visit_id +
                "' and operater_id = '" + operater_id + "' and ad_type = '" + ad_type + "' and ad_state = '" + ad_state + "'", null);

    }

    public Cursor getVisitResults() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from visite_result ", null); //where synced = '0'
        return res;
    }


    public void insertDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into sale_point (sp_code, sp_address,  sp_name,  sp_owner, sp_phone, sp_type,  street_type, block_type, route_desc, lat, lng, synced) values ( '1', 'خرطوم', 'صيدلية المك نمر', 'هلال', '0987654321', '0', '1', '2', 'nn', '0', '0', '0');");
    }
}
