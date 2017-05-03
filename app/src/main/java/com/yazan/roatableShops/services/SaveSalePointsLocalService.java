package com.yazan.roatableShops.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.yazan.roatableShops.controllers.ShopOrderSavedActivity;
import com.yazan.roatableShops.controllers.SalePointOrderSavedActivity;
import com.yazan.roatableShops.dao.DAO;
import com.yazan.roatableShops.models.Agent;
import com.yazan.roatableShops.models.Route;
import com.yazan.roatableShops.models.SalePoint;
import com.yazan.roatableShops.models.Shop;
import com.yazan.roatableShops.models.ShopProductCode;
import com.yazan.roatableShops.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carsiow on 4/29/17.
 */

public class SaveSalePointsLocalService extends Service {

    JSONParser SalePointjsonParser = new JSONParser();

    String sp_code1, sp_code2;

    private static String url_sps_info = "http://consulsat.net/routableWSx/sps_get_info.aspx";
    private static String url_shop_product_code = "http://consulsat.net/routableWSx/shop_product_code.aspx";
    private static final String TAG_SP_ID = "sp_id";
    private static final String TAG_SP_NAME = "sp_name";
    private static final String TAG_SP_TYPE = "sp_type";
    private static final String TAG_SP_OWNER = "sp_owner";
    private static final String TAG_SP_PHONE = "sp_phone";
    private static final String TAG_SP_ADDRESS = "sp_address";
    private static final String TAG_SP_BLOCK_TYPE = "block_type";
    private static final String TAG_SP_STREET_TYPE = "street_type";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LNG = "lng";
    private static final String TAG_ROAT_ID = "roat_id";
    private static final String TAG_ROAT_DES = "route_desc";

    private static final String TAG_AD_CODES = "ad_codes";
    private static final String TAG_PPRODUCT_NAME = "product_name";
    private static final String TAG_AD_CODE = "ad_code";
    private static final String TAG_AD_TYPE = "ad_type";
    private static final String TAG_POINT_TYPE = "point_type";


    private static final String TAG_SALEPOINTS = "salepoints";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    Agent agent = new Agent();
    Shop shop = new Shop();


    boolean success = false;

    JSONArray salepoints = null;
    JSONArray adCodes = null;


    @Override
    public void onCreate() {
        //
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sp_code1 = intent.getStringExtra("sp_code1");
        sp_code2 = intent.getStringExtra("sp_code2");

        new SalePointSaveLocal().execute();
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "sync done", Toast.LENGTH_SHORT).show();
    }


    class SalePointSaveLocal extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //sp_code11 = ((TextView) tv.findViewById(R.id.textView)).getText().toString();
        }


        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sp_code1", sp_code1));
            params.add(new BasicNameValuePair("sp_code2", sp_code2));

            try {


                JSONObject json = SalePointjsonParser.makeHttpRequest(url_sps_info, "GET", params);
                // System.out.println(json);
                if (json == null) {
                    Intent i = new Intent(getApplicationContext(), SalePointOrderSavedActivity.class);
                    i.putExtra("crashed", true);
                    startActivity(i);
                } else {

                    Log.d("Create Response", json.toString());


                    int success = json.getInt(TAG_SUCCESS);

                    String message = json.getString(TAG_MESSAGE);

                    salepoints = json.getJSONArray(TAG_SALEPOINTS);
                    if (success == 1) {


                        for (int i = 0; i < salepoints.length(); i++) {
                            JSONObject c = salepoints.getJSONObject(i);

                            int sp_id = c.getInt(TAG_SP_ID);
                            String sp_name = c.getString(TAG_SP_NAME);
                            int sp_type = c.getInt(TAG_SP_TYPE);
                            String sp_owner = c.getString(TAG_SP_OWNER);
                            String sp_phone = c.getString(TAG_SP_PHONE);
                            String sp_address = c.getString(TAG_SP_ADDRESS);
                            String lat = c.getString(TAG_LAT);
                            String lng = c.getString(TAG_LNG);
                            String roat_id = c.getString(TAG_ROAT_ID);
                            String route_desc = c.getString(TAG_ROAT_DES);
                            int block_type = c.getInt(TAG_SP_BLOCK_TYPE);
                            int street_type = c.getInt(TAG_SP_STREET_TYPE);
                            String sp_code = c.getString("sp_code");
                            int icon = c.getInt("icon");
                            int zim = c.getInt("zim");
                            int evd = c.getInt("evd");


                            Agent agent = new Agent();
                            SalePoint salePoint = new SalePoint();
                            Route route = new Route();
                            salePoint.setSpID("" + sp_id);
                            salePoint.setSpName(sp_name);
                            salePoint.setSpCode(sp_code);
                            salePoint.setSpType(sp_type);
                            salePoint.setRout_id(roat_id);
                            salePoint.setSpOwnerName(sp_owner);
                            salePoint.setSpPhone(sp_phone);
                            salePoint.setSpAddress(sp_address);
                            salePoint.setBlock_type(block_type);
                            salePoint.setStreet_type(street_type);
                            salePoint.setLat(lat);
                            salePoint.setLng(lng);
                            salePoint.setRoute_desc(route_desc);
                            salePoint.setICON(icon);
                            salePoint.setEVD(evd);
                            salePoint.setZIM(zim);

                            DAO dao = new DAO(getApplicationContext());
                            dao.insertSalePoint(salePoint);


                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonAdCodes = SalePointjsonParser.makeHttpRequest(url_shop_product_code,
                        "GET", params);

                if (jsonAdCodes == null) {
                    Intent i = new Intent(getApplicationContext(), ShopOrderSavedActivity.class);
                    i.putExtra("crashed", true);
                    startActivity(i);
                } else {
                    int success = jsonAdCodes.getInt(TAG_SUCCESS);
                    String message = jsonAdCodes.getString(TAG_MESSAGE);

                    adCodes = jsonAdCodes.getJSONArray(TAG_AD_CODES);
                    if (success == 1) {

                        for (int i = 0; i < adCodes.length(); i++) {
                            JSONObject c = adCodes.getJSONObject(i);

                            ShopProductCode productCode = new ShopProductCode();
                            productCode.setProductName(c.getString(TAG_PPRODUCT_NAME));
                            productCode.setAdCode(c.getString(TAG_AD_CODE));
                            productCode.setAdType(c.getString(TAG_AD_TYPE));
                            productCode.setPointType(c.getString(TAG_POINT_TYPE));

                            DAO dao = new DAO(getApplicationContext());
                            dao.insertShopProductCode(productCode);
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
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

