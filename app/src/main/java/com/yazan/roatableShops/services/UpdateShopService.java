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

import com.yazan.roatableShops.controllers.ShopOrderSavedActivity;
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


public class UpdateShopService extends Service {

    JSONParser SalePointjsonParser = new JSONParser();

    private static String url_sp_update = "http://consulsat.net/routableWSx/update_shop.aspx";
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
        new UpdateShop().execute();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    class UpdateShop extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        private DAO dao = new DAO(getApplicationContext());

        protected String doInBackground(String... args) {

            Cursor res = dao.getShops();
            res.moveToFirst();

            while (!res.isAfterLast()) {


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(DAO.COLUMN_SHOP_CODE, res.getString(0)));
                params.add(new BasicNameValuePair(DAO.COLUMN_SHOP_NAME, "" + res.getString(1)));
                params.add(new BasicNameValuePair(DAO.COLUMN_SHOP_TYPE, "" + res.getString(2)));
                params.add(new BasicNameValuePair(DAO.COLUMN_LAT, res.getString(3)));
                params.add(new BasicNameValuePair(DAO.COLUMN_LNG, res.getString(4)));
                params.add(new BasicNameValuePair(DAO.COLUMN_LAST_CHECK_DATE, res.getString(5)));
                params.add(new BasicNameValuePair(DAO.COLUMN_CITY_CODE, res.getString(6)));


                try {
                    SalePointjsonParser.makeHttpRequest(url_sp_update, "GET", params);
                } catch (Exception e) {

                    Log.w("JSON Exception", e.getMessage());

                }

                res.moveToNext();
            }

            /** save shop_visit */
            Cursor resVisit = dao.getVisits();

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

                    SalePointjsonParser.makeHttpRequest(url_visit_update, "GET", params);

                } catch (Exception e) {

                    Log.w("JSON Exception", e.getMessage());

                }

                res.moveToNext();
            }


            /** Save shop_visit_result */
            Cursor resVisitResult = dao.getVisitResults();
            resVisitResult.moveToFirst();


            while (!resVisitResult.isAfterLast()) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(DAO.COLUMN_VISIT_ID, resVisitResult.getString(0)));
                params.add(new BasicNameValuePair(DAO.COLUMN_AD_CODE, resVisitResult.getString(1)));
                params.add(new BasicNameValuePair(DAO.COLUMN_AD_QTY, "" + resVisitResult.getString(2)));
                params.add(new BasicNameValuePair(DAO.COLUMN_AD_WHEN, resVisitResult.getString(3)));

                try {
                    SalePointjsonParser.makeHttpRequest(url_visit_result_update, "GET", params);

                } catch (Exception e) {

                    Log.w("JSON Exception", e.getMessage());

                }
                resVisitResult.moveToNext();
            }


            success = true;

            Intent i = new Intent(getApplicationContext(), ShopOrderSavedActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

            return null;
        }


        protected void onPostExecute(String file_url) {
        }

    }


}
