package com.example.shirouq_paints.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shirouq_paints.models.Agent;
import com.example.shirouq_paints.models.SalePoint;
import com.example.shirouq_paints.models.Visit;

/**
 * Created by Carsiow on 11/14/16.
 */

public class VisitDAO extends SQLiteOpenHelper {


    public VisitDAO(Context context) {
        super(context, "roatable", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE visite " +
                "( agent_id text , " +
                "  sp_code text , " +
                "  visit_date text ,  " +
                "  visit_id text , " +
                "  synced int ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS visite");
        onCreate(db);
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

    public void setSynced(String visit_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("synced", 1);
        db.update("visite", contentValues, "visit_id = " + "'" + visit_id + "'" , null);

    }


    public Cursor getVisits()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from visite where status = '0'", null);
        return  res;
    }

}
