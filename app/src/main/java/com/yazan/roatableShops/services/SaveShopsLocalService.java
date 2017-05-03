package com.yazan.roatableShops.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.yazan.roatableShops.controllers.ShopOrderSavedActivity;
import com.yazan.roatableShops.controllers.ShopsInfoActivity;
import com.yazan.roatableShops.dao.DAO;
import com.yazan.roatableShops.models.Agent;
import com.yazan.roatableShops.models.Shop;
import com.yazan.roatableShops.models.ShopProductCode;
import com.yazan.roatableShops.util.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carsiow on 4/29/17.
 */

public class SaveShopsLocalService extends Service {

    JSONParser SalePointjsonParser = new JSONParser();

    private static String url_shops_info = "";
    private static String url_shop_product_code = "";
    private static final String TAG_SHOP_ID = "shop_id";
    private static final String TAG_SHOP_NAME = "shop_name";
    private static final String TAG_SHOP_TYPE = "shop_type";
    private static final String TAG_SHOP_CODE = "shop_code";
    private static final String TAG_CITY_CODE = "city_code";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LNG = "lng";
    private static final String TAG_SHOPS = "shops";

    private static final String TAG_AD_CODES = "ad_codes";
    private static final String TAG_PPRODUCT_NAME = "product_name";
    private static final String TAG_AD_CODE = "ad_code";
    private static final String TAG_AD_TYPE = "ad_type";
    private static final String TAG_POINT_TYPE = "point_type";


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
        String sp_code11;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //sp_code11 = ((TextView) tv.findViewById(R.id.textView)).getText().toString();
        }


        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            DAO dao = new DAO(getApplicationContext());

            try {


                JSONObject json = SalePointjsonParser.makeHttpRequest(url_shops_info, "GET", params);
                // System.out.println(json);
                if (json == null) {
                    Intent i = new Intent(getApplicationContext(), ShopOrderSavedActivity.class);
                    i.putExtra("crashed", true);
                    startActivity(i);
                } else {
                    int success = json.getInt(TAG_SUCCESS);

                    String message = json.getString(TAG_MESSAGE);

                    salepoints = json.getJSONArray(TAG_SHOPS);
                    if (success == 1) {

                        for (int i = 0; i < salepoints.length(); i++) {
                            JSONObject c = salepoints.getJSONObject(i);

                            Shop shop = new Shop();

                            shop.setSpID("" + c.getInt(TAG_SHOP_ID));
                            shop.setSpName(c.getString(TAG_SHOP_NAME));
                            shop.setSpCode(c.getString(TAG_SHOP_CODE));
                            shop.setSpType(c.getInt(TAG_SHOP_TYPE));
                            shop.setLat(c.getString(TAG_LAT));
                            shop.setLng(c.getString(TAG_LNG));
                            shop.setCityCode(c.getString(TAG_CITY_CODE));

                            dao.insertShop(shop);


                        }
                    } else if (success == 2) {
                        Intent in = new Intent(getApplicationContext(), ShopsInfoActivity.class);
                        in.putExtra("crashed", false);
                        in.putExtra("Agent", agent);
                        in.putExtra("Shop", shop);
                        in.putExtra("Lang", "Arabic");
                        in.putExtra("up", "");
                        in.putExtra("message", message);
                        startActivity(in);
                    }
                }


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

                            dao.insertShopProductCode(productCode);
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            success = true;

            Intent i = new Intent(getApplicationContext(), ShopOrderSavedActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

            return null;
        }

        protected void onPostExecute(String file_url) {
        }

    }

}

