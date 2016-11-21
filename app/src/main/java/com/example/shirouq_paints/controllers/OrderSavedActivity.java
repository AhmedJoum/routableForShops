package com.example.shirouq_paints.controllers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shirouq_paints.dao.DAO;
import com.example.shirouq_paints.models.Agent;
import com.example.shirouq_paints.models.SalePoint;
import com.example.shirouq_paints.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OrderSavedActivity extends AppCompatActivity {

    Button newOrder;

    FloatingActionButton logoutBtn;

    private String Lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Agent agent = (Agent) getIntent().getSerializableExtra("Agent");
        final SalePoint salePoint = new SalePoint();


            setContentView(R.layout.activity_order_saved_note_ar);

        newOrder = (Button) findViewById(R.id.newOrder);



        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                i.putExtra("SalePoint", salePoint);
                i.putExtra("Agent", agent);
                startActivity(i);
            }
        });


        final Button syncBtn = (Button) findViewById(R.id.syncBtn);

        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "!! -- Please check your internet connection -- !!", Toast.LENGTH_LONG).show();
                    return;
                }

                newOrder.setEnabled(false);
                syncBtn.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Sync Started", Toast.LENGTH_LONG).show();

                new UpdateSalePointService().execute();

                new UpdateVisit().execute();

                new UpdateVisitResult().execute();


                newOrder.setEnabled(true);
                syncBtn.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Sync done", Toast.LENGTH_LONG).show();


            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
    }

    JSONParser SalePointjsonParser = new JSONParser();

    private static String url_sp_update = "http://consulsat-sd.com/roatable/update_salepoint.php";
    private static String url_visit_update = "http://consulsat-sd.com/roatable/save_visite.php";
    private static String url_visit_result_update = "http://consulsat-sd.com/roatable/save_visite_result.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    class UpdateSalePointService extends AsyncTask<String, String, String> {

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
                params.add(new BasicNameValuePair("block_type",res.getString(7)));
                params.add(new BasicNameValuePair("route_desc", "" + res.getString(8)));
                params.add(new BasicNameValuePair("lat", "" + res.getString(9)));
                params.add(new BasicNameValuePair("lng",  res.getString(10)));

                try {

                    JSONObject json = SalePointjsonParser.makeHttpRequest(url_sp_update, "GET", params);

                    if (json == null) {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        i.putExtra("crashed", true);
                        startActivity(i);
                    } else {

                        Log.d("Create Response", json.toString());


                        int success = json.getInt(TAG_SUCCESS);

                        String message = json.getString(TAG_MESSAGE);


                        if (success == 1) {
                            DAO.setSynced(res.getString(0));

                        }
                    }

                } catch (JSONException e) {

                    Log.w("JSON Exception", e.getMessage());

                }

                res.moveToNext();
            }

            return null;
        }


        protected void onPostExecute(String file_url) {
        }

    }

    class UpdateVisit extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        private DAO visitDAO = new DAO(getApplicationContext());

        protected String doInBackground(String... args) {

            Cursor res = visitDAO.getVisits();
            res.moveToFirst();


            while (!res.isAfterLast()) {



                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("agent_id", res.getString(0)));
                params.add(new BasicNameValuePair("sp_code", res.getString(1)));
                params.add(new BasicNameValuePair("visit_date", "" + res.getString(2)));
                params.add(new BasicNameValuePair("visit_id", res.getString(3)));

                try {

                    JSONObject json = SalePointjsonParser.makeHttpRequest(url_visit_update, "GET", params);

                    if (json == null) {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        i.putExtra("crashed", true);
                        startActivity(i);
                    } else {

                        Log.d("Create Response", json.toString());


                        int success = json.getInt(TAG_SUCCESS);

                        String message = json.getString(TAG_MESSAGE);


                        if (success == 1) {
                            visitDAO.setSyncedV(res.getString(3));

                        }
                    }

                } catch (JSONException e) {

                    Log.w("JSON Exception", e.getMessage());

                }

                res.moveToNext();
            }

            return null;
        }


        protected void onPostExecute(String file_url) {
        }

    }

    class UpdateVisitResult extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        private DAO visitResultDAO = new DAO(getApplicationContext());

        protected String doInBackground(String... args) {

            Cursor res = visitResultDAO.getVisitResults();
            res.moveToFirst();


            while (!res.isAfterLast()) {



                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("visit_id", res.getString(0)));
                params.add(new BasicNameValuePair("operater_id", res.getString(1)));
                params.add(new BasicNameValuePair("ad_type", "" + res.getString(2)));
                params.add(new BasicNameValuePair("ad_state", res.getString(3)));
                params.add(new BasicNameValuePair("bad_state_reason", res.getString(4)));

                try {

                    JSONObject json = SalePointjsonParser.makeHttpRequest(url_visit_result_update, "GET", params);

                    if (json == null) {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        i.putExtra("crashed", true);
                        startActivity(i);
                    } else {

                        Log.d("Create Response", json.toString());


                        int success = json.getInt(TAG_SUCCESS);

                        String message = json.getString(TAG_MESSAGE);


                        if (success == 1) {
                            visitResultDAO.setSyncedVR(res.getString(0), res.getString(1), res.getString(2), res.getString(3));

                        }
                    }

                } catch (JSONException e) {

                    Log.w("JSON Exception", e.getMessage());

                }
                res.moveToNext();

            }

            return null;
        }


        protected void onPostExecute(String file_url) {
        }

    }
}
