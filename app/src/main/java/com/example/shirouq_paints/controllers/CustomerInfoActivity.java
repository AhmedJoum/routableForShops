package com.example.shirouq_paints.controllers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shirouq_paints.models.Customer;
import com.example.shirouq_paints.models.Salesman;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.shirouq_paints.util.JSONParser;
import com.example.shirouq_paints.util.LocListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class CustomerInfoActivity extends AppCompatActivity {

    //// TODO: 9/15/16 : use popup menu to save customer and clear controls

    EditText custName;
    EditText custCode;
    EditText custPhone;
    EditText custAddress;

    String nameError1;
    String nameError2;
    String phoneError1;
    String phoneError2;
    String emailError;
    String TBsError;
    String GPSError;

    int x = 3;

    FloatingActionButton nextBtn;
    FloatingActionButton cancelBtn;
    FloatingActionButton logoutBtn;


    Customer customer = new Customer();
    private String Lang;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean crashed = getIntent().getBooleanExtra("crashed", false);

        if (crashed == true) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        }

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocListener l = new LocListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, l);


        super.onCreate(savedInstanceState);

        Lang = getIntent().getStringExtra("Lang");

        if (Lang.equals("English")) {
            setContentView(R.layout.activity_customer_info);
            nameError1 = "Name contains number..";
            nameError2 = "Name Required";
            phoneError1 = "Should be numbers only..";
            phoneError2 = "Invalid phone number..";
            emailError = "Invalid Email address..";
            TBsError = "Fill all required...";
            GPSError = "GPS Not Activated.";

        } else {
            setContentView(R.layout.activity_customer_info_ar);
            nameError1 = "لا يمكن للاسم ان يحتوي ارقاماً!";
            nameError2 = "ادخل اسم العميل";
            phoneError1 = "رقام فقط";
            phoneError2 = "الرجاء ادخال عشر ارقام";
            emailError = "الريد الالكتروني غير صحيح";
            TBsError = "املأ جميع الخانات...";
            GPSError = "تحديد المواقع لا يعمل...";
        }

        custName = (EditText) findViewById(R.id.custName);
        custPhone = (EditText) findViewById(R.id.custPhone);
        custCode = (EditText) findViewById(R.id.custCode);
        custAddress = (EditText) findViewById(R.id.custAdress);

        nextBtn = (FloatingActionButton) findViewById(R.id.nextBtn);
        cancelBtn = (FloatingActionButton) findViewById(R.id.cancelBtn);
        logoutBtn = (FloatingActionButton) findViewById(R.id.LogoutBtn);

        //final Customer
        customer = (Customer) getIntent().getSerializableExtra("Customer");

        custName.setText(customer.getCustName());
        custPhone.setText(customer.getCustPhone());
        custCode.setText(customer.getCustCode());
        custAddress.setText(customer.getCustAddress());


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = custName.getText().toString();
                String Phone = custPhone.getText().toString();
                String Code = custCode.getText().toString();
                String Address = custAddress.getText().toString();



                boolean cancel = false;
                View focusView = null;

                if (name.matches(".*\\d.*")) {

                    custName.setError(nameError1);
                    cancel = true;
                    focusView = custName;
                }

                if (name.equals("")) {
                    custName.setError(nameError2);
                    cancel = true;
                    focusView = custName;
                }

                if (!Phone.matches("[0-9]+")) {
                    custPhone.setError(phoneError1);
                    cancel = true;
                    focusView = custPhone;
                }

                if (Phone.length() > 10 || Phone.length() < 10) {
                    custPhone.setError(phoneError2);
                    cancel = true;
                    focusView = custPhone;
                }

//                if (!Email.matches(".+@.+\\.[a-z]+")) {
//                    custEmail.setError(emailError);
//                    cancel = true;
//                    focusView = custEmail;
//                }

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                  Toast.makeText(getApplicationContext(), GPSError, Toast.LENGTH_LONG).show();
                    cancel = true;
                }

                if (name.equals("") || Phone.equals("") || Code.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            TBsError,
                            Toast.LENGTH_LONG).show();
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {

                    customer.setCustName(name);
                    customer.setCustPhone(Phone);
                    customer.setCustCode(Code);
                    customer.setCustAddress(Address);

                    customer.setLat("" + l.getLat());
                    customer.setLon("" + l.getLon());

                    Intent i = new Intent(getApplicationContext(), StepTowActivity.class);
                    i.putExtra("CustomerOrder", customer);
                    i.putExtra("Lang", Lang);
                    startActivity(i);
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custName.setText("");
                custCode.setText("");
                custAddress.setText("");
                custPhone.setText("");
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
    }


    /***/

    JSONParser jsonParser = new JSONParser();
    String url_cust_save = "http://yiserver.com/shirouq_sales_ws/save_customer.php";
    private static String TAG_SUCCESS = "success";
    private static String TAG_MESSAGE = "message";

    /**
     * CustomerService class.
     * <p/>
     * contact with the web service to save the current customer and return it's
     * code the order activity.
     * <p/>
     * it works on the background thread.
     */

    class CustomerService extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", customer.getCustName()));
            params.add(new BasicNameValuePair("code", customer.getCustCode()));
            params.add(new BasicNameValuePair("address", customer.getCustAddress()));
            params.add(new BasicNameValuePair("phone", customer.getCustPhone()));
            params.add(new BasicNameValuePair("lat", customer.getLat()));
            params.add(new BasicNameValuePair("lon", customer.getLon()));

            try {
                // getting JSON Object
                // Note that create product url accepts POST method
                JSONObject json = jsonParser.makeHttpRequest(url_cust_save, "GET", params);

                if (json == null) {
                    Intent i = new Intent(getApplicationContext(), CustomerInfoActivity.class);
                    i.putExtra("crashed", true);
                    i.putExtra("message", "Server not reached");
                    startActivity(i);
                } else {
                    // check log cat fro response
                    Log.d("Create Response", json.toString());

                    // check for success tag

                    int success = json.getInt(TAG_SUCCESS);
                    String message = json.getString(TAG_MESSAGE);


                    if (success == 1) {
                        Salesman salesman = new Salesman();
                        Customer customer = new Customer();

                        Intent i = new Intent(getApplicationContext(), StepTowActivity.class);
                        i.putExtra("Salesmen", salesman);
                        i.putExtra("Customer", customer);
                        i.putExtra("Lang", "Arabic");
                        startActivity(i);

                        // closing this screen
                        finish();
                    } else {
                        // failed to create product
                        Intent i = new Intent(getApplicationContext(), CustomerInfoActivity.class);
                        i.putExtra("crashed", true);
                        i.putExtra("message", message);
                        startActivity(i);
                    }
                }
            } catch (JSONException e) {

                Log.w("JSON Exception", e.getMessage());

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("crashed", true);
                startActivity(i);
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            //pDialog.dismiss();
        }

    }


}
