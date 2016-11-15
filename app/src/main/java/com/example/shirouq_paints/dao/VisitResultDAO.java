package com.example.shirouq_paints.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shirouq_paints.models.Visit;
import com.example.shirouq_paints.models.VisitResult;

/**
 * Created by Carsiow on 11/14/16.
 */

public class VisitResultDAO extends SQLiteOpenHelper {


    public VisitResultDAO(Context context) {
        super(context, "roatable", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE visite_result " +
                "( visit_id text," +
                "  operater_id text, " +
                "  ad_type integer,  " +
                "  ad_state integer, " +
                "  bad_state_reason integer, " +
                "  synced int )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS visite_result");
        onCreate(db);
    }

    public boolean insertVisitResult(VisitResult visitResult) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("visit_id", visitResult.getVisit_id());
        contentValues.put("operater_id", visitResult.getOperater_id());
        contentValues.put("ad_type", "" + visitResult.getAd_type());
        contentValues.put("ad_state", visitResult.getAd_state());
        contentValues.put("bad_state_reason", visitResult.getBad_state_reason());
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
        Cursor res = db.rawQuery("select * from visite_result where status = '0'", null);
        return res;
    }

}