package com.yazan.roatableShops.services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.yazan.roatableShops.controllers.SalePointOrderSavedActivity;
import com.yazan.roatableShops.dao.DAO;
import com.yazan.roatableShops.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carsiow on 4/29/17.
 */


public class UpdateSalePointService extends Service {

    JSONParser SalePointJsonParser = new JSONParser();

    private static String url_sp_update = "http://consulsat.net/routableWSx/update_salepoint.aspx";
    private static String url_visit_update = "http://consulsat.net/routableWSx/shop_save_visite.aspx";
    private static String url_visit_result_update = "http://consulsat.net/routableWSx/shop_save_visite_reslut.aspx";

    public String android_id;

    boolean success = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        new UpdateSalePoint().execute();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    class UpdateSalePoint extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        private DAO DAO = new DAO(getApplicationContext());

        protected String doInBackground(String... args) {

            Cursor res = DAO.getSalePoints();
            res.moveToFirst();


            while (!res.isAfterLast()) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sp_code", res.getString(0)));
                params.add(new BasicNameValuePair("sp_address", res.getString(1)));
                params.add(new BasicNameValuePair("sp_name", "" + res.getString(2)));
                params.add(new BasicNameValuePair("sp_owner", res.getString(3)));
                params.add(new BasicNameValuePair("sp_phone", res.getString(4)));
                params.add(new BasicNameValuePair("sp_type", res.getString(5)));
                params.add(new BasicNameValuePair("street_type", res.getString(6)));
                params.add(new BasicNameValuePair("block_type", res.getString(7)));
                params.add(new BasicNameValuePair("route_desc", "" + res.getString(8)));
                params.add(new BasicNameValuePair("lat", "" + res.getString(9)));
                params.add(new BasicNameValuePair("lng", res.getString(10)));
                params.add(new BasicNameValuePair("icon", "" + res.getInt(11)));
                params.add(new BasicNameValuePair("zim", "" + res.getInt(12)));
                params.add(new BasicNameValuePair("evd", "" + res.getInt(13)));

                try {

                    JSONObject json = SalePointJsonParser.makeHttpRequest(url_sp_update, "GET", params);

                } catch (Exception e) {

                    Log.w("JSON Exception", e.getMessage());

                }

                res.moveToNext();
            }

            /** save shop_visit */
            Cursor resVisit = DAO.getVisits();

            resVisit.moveToFirst();
            while (!resVisit.isAfterLast()) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(DAO.COLUMN_SHOP_CODE, resVisit.getString(0)));
                params.add(new BasicNameValuePair(DAO.COLUMN_VISIT_DATE, resVisit.getString(1)));
                params.add(new BasicNameValuePair(DAO.COLUMN_VISIT_ID, "" + resVisit.getString(2)));
                params.add(new BasicNameValuePair(DAO.COLUMN_HOLDER_QTY, resVisit.getString(3)));
                params.add(new BasicNameValuePair(DAO.COLUMN_STAND_QTY, resVisit.getString(4)));
                params.add(new BasicNameValuePair(DAO.COLUMN_STAND_TYPES, resVisit.getString(5)));
                params.add(new BasicNameValuePair("android_id", android_id));

                try {

                    JSONObject json = SalePointJsonParser.makeHttpRequest(url_visit_update, "GET", params);


                } catch (Exception e) {

                    Log.w("JSON Exception", e.getMessage());

                }

                res.moveToNext();
            }


            /** Save shop_visit_result */
            Cursor resVisitResult = DAO.getVisitResults();
            resVisitResult.moveToFirst();


            while (!resVisitResult.isAfterLast()) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(DAO.COLUMN_VISIT_ID, resVisitResult.getString(0)));
                params.add(new BasicNameValuePair(DAO.COLUMN_AD_CODE, resVisitResult.getString(1)));
                params.add(new BasicNameValuePair(DAO.COLUMN_AD_QTY, "" + resVisitResult.getString(2)));
                params.add(new BasicNameValuePair(DAO.COLUMN_AD_WHEN, resVisitResult.getString(3)));

                try {
                    JSONObject json = SalePointJsonParser.makeHttpRequest(url_visit_result_update, "GET", params);

                } catch (Exception e) {

                    Log.w("JSON Exception", e.getMessage());

                }
                resVisitResult.moveToNext();
            }

            success = true;


            Intent i = new Intent(getApplicationContext(), SalePointOrderSavedActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

            return null;


        }


        protected void onPostExecute(String file_url) {
        }

    }


}
