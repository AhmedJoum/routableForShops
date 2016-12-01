


package com.example.shirouq_paints.controllers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shirouq_paints.dao.DAO;
import com.example.shirouq_paints.models.Agent;
import com.example.shirouq_paints.models.Route;
import com.example.shirouq_paints.models.SalePoint;
import com.example.shirouq_paints.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.shirouq_paints.controllers.R.id.syncBtn;


public class OrderSavedActivity extends AppCompatActivity {
    // private String Lang;

    private String lat;
    private String lng;
    private String sp_code1;
    private String sp_code2;
    private String sp_code;

    private String str, str2;

    TextView tv;
    Button newOrder, syncPullBtn;
    Button syncBtn;


    EditText fromTB;
    EditText toTB;

    private String Lang;
    SalePoint salePoint = new SalePoint();
    Agent agent = new Agent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Agent agent = new Agent();
        agent.setA_id(1);
        final SalePoint salePoint = new SalePoint();


        setContentView(R.layout.activity_order_saved_note_ar);

        newOrder = (Button) findViewById(R.id.newOrder);
        syncPullBtn = (Button) findViewById(R.id.syncPullBtn);
        syncBtn = (Button) findViewById(R.id.syncBtn);
        fromTB = (EditText) findViewById(R.id.FromTB);
        toTB = (EditText) findViewById(R.id.ToTB);

        /****************************************************************/
        //tv = (TextView)findViewById(R.id.textView);

        File dir = Environment.getExternalStorageDirectory();
        File yourFile = new File(dir, "/sdcard/");

        //Get the text file
        File file = new File(dir, "note2.txt");
        if (file.exists())   // check if file exist
        {
            //Read text from file
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
            } catch (IOException e) {
                //You'll need to add proper error handling here
            }
            //Set the text
            //  tv.setText(text);
        } else {
            // tv.setText("Sorry file doesn't exist!!");
        }

        /*************************************************************/
        syncPullBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "!! -- Please check your internet connection -- !!", Toast.LENGTH_LONG).show();
                    return;
                }

//                success = false;
//                //Visit visit = new Visit();
//                newOrder.setEnabled(false);
//                syncPullBtn.setEnabled(false);
//                syncBtn.setEnabled(false);

                if(fromTB.getText().toString().isEmpty() || toTB.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "رجاد حدد من و الى للSync", Toast.LENGTH_LONG).show();
                    return;
                }

                 str = fromTB.getText().toString();


                 str2 = toTB.getText().toString();


                Toast.makeText(getApplicationContext(), "Sync Started", Toast.LENGTH_LONG).show();


                new SalePointSaveLocal().execute();

                Toast.makeText(getApplicationContext(), "Sync done", Toast.LENGTH_LONG).show();


                succed();


            }
        });


        /*************************************************/

        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                i.putExtra("SalePoint", salePoint);
                i.putExtra("Agent", agent);
                startActivity(i);
            }
        });





        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "!! -- Please check your internet connection -- !!", Toast.LENGTH_LONG).show();
                    return;
                }

//                success = false;
//
//                newOrder.setEnabled(false);
//                syncPullBtn.setEnabled(false);
//                syncBtn.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Sync Started", Toast.LENGTH_LONG).show();

                new UpdateSalePointService().execute();

                new UpdateVisit().execute();

                new UpdateVisitResult().execute();


                succed();


                Toast.makeText(getApplicationContext(), "Sync done", Toast.LENGTH_LONG).show();


            }
        });

    }

    private void succed() {
        if(success){
            newOrder.setEnabled(true);
            syncPullBtn.setEnabled(true);
            syncBtn.setEnabled(true);
        }
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
    private static String url_sps_info = "http://consulsat-sd.com/roatable/sps_get_info.php";
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

    private static final String TAG_SALEPOINTS = "salepoints";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    JSONArray salepoints = null;

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
                params.add(new BasicNameValuePair("block_type", res.getString(7)));
                params.add(new BasicNameValuePair("route_desc", "" + res.getString(8)));
                params.add(new BasicNameValuePair("lat", "" + res.getString(9)));
                params.add(new BasicNameValuePair("lng", res.getString(10)));

                try {

                    JSONObject json = SalePointjsonParser.makeHttpRequest(url_sp_update, "GET", params);

                    if (json == null) {
                        Intent i = new Intent(getApplicationContext(), OrderSavedActivity.class);
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
            success= true;
            return null;
        }


        protected void onPostExecute(String file_url) {
        }

    }

    boolean success = false;

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
                        Intent i = new Intent(getApplicationContext(), OrderSavedActivity.class);
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

            success = true;

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
                params.add(new BasicNameValuePair("ad_no", res.getString(5)));

                try {

                    JSONObject json = SalePointjsonParser.makeHttpRequest(url_visit_result_update, "GET", params);

                    if (json == null) {
                        Intent i = new Intent(getApplicationContext(), OrderSavedActivity.class);
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

            success = true;

            return null;
        }


        protected void onPostExecute(String file_url) {
        }

    }


    /********************************************************/
    class SalePointSaveLocal extends AsyncTask<String, String, String> {
        String sp_code11;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //sp_code11 = ((TextView) tv.findViewById(R.id.textView)).getText().toString();
        }


        protected String doInBackground(String... args) {


            sp_code1 = String.valueOf(str);


            sp_code2 = String.valueOf(str2);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sp_code1", sp_code1));
            params.add(new BasicNameValuePair("sp_code2", sp_code2));

            try {


                JSONObject json = SalePointjsonParser.makeHttpRequest(url_sps_info, "GET", params);
                // System.out.println(json);
                if (json == null) {
                    Intent i = new Intent(getApplicationContext(), OrderSavedActivity.class);
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
                    } else if (success == 2) {
                        Intent in = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                        in.putExtra("crashed", false);
                        in.putExtra("Agent", agent);
                        in.putExtra("SalePoint", salePoint);
                        in.putExtra("Lang", "Arabic");
                        in.putExtra("up", "");
                        in.putExtra("message", message);
                        startActivity(in);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            success = true;
            return null;
        }


        protected void onPostExecute(String file_url) {
        }

    }
}
