package com.example.shirouq_paints.controllers;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shirouq_paints.models.Customer;
import com.example.shirouq_paints.util.JSONParser;
import com.example.shirouq_paints.util.LocListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StepTowActivity extends AppCompatActivity {

    private CheckBox mCB1, mCB2, mCB3, mCB4, mCB5, mCB6, mCB7, mCB8, mCB9, mCB10,
            mCB11, mCB12, mCB13, mCB14, mCB15, mCB16, mCB17,
            mCB18, mCB19, mCB20, mCBX;

    private EditText mET1, mET2, mET3, mET4, mET5, mET6, mET7, mET8, mET9, mET10, mET11, mET12, mET13, mET14, mET15,
            mET16, mET17, mET18, mET19, mET20;


    private View mProgressView;
    private View mStepTwoFormView;
    private View mEngMaintenance;

    private String Lang, notSelectError, GPSError, NetworkError, StartingUploadingInfo, ServerError, DescriptionServiceError;

    JSONParser jsonParser = new JSONParser();
    private static String url_save_order = "http://yiserver.com/rms-ws/save_order.php";
    private static String url_save_order_service = "http://yiserver.com/rms-ws/save_order_service.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ORDER_ID = "order_id";


    FloatingActionButton nextBtn;
    FloatingActionButton backBtn, cancelBtn;

    List<Integer> SelectedServices;
    HashMap<Integer, String> SelectedServiceDescription;

    Customer co = new Customer();//(Customer) getIntent().getSerializableExtra("CustomerOrder");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Lang = getIntent().getStringExtra("Lang");

        if (Lang.equals("English")) {
            setContentView(R.layout.activity_step_tow);
            notSelectError = "Please at least select one service...";
            GPSError = "GPS not Enabled..";
            NetworkError = "Network Connection problem..";
            StartingUploadingInfo = "Start uploading information..";
            ServerError = "Server Down ..!";
            DescriptionServiceError = "Fill Checked Services Description..";
        } else {
            setContentView(R.layout.activity_step_tow_ar);
            notSelectError = "اختر على الأقل خدمة واحدة..";
            GPSError = "خاصية تحديد المواقع غير مفعلة";
            NetworkError = "مشكلة بالاتصال بالانترنت";
            StartingUploadingInfo = "بدأ تحميل البيانات";
            ServerError = "غير قادر بالإتصال مع حافظ البيانات..!";
            DescriptionServiceError = "املاء ملاحظات الخدمات المختارة";
        }

        if (getIntent().getBooleanExtra("crashed", false) == true) {
            Toast.makeText(getApplicationContext(), ServerError, Toast.LENGTH_LONG).show();
        }

        mCB1 = (CheckBox) findViewById(R.id.checkBox);
        mCB2 = (CheckBox) findViewById(R.id.checkBox2);
        mCB3 = (CheckBox) findViewById(R.id.checkBox3);
        mCB4 = (CheckBox) findViewById(R.id.checkBox4);
        mCB5 = (CheckBox) findViewById(R.id.checkBox5);
        mCB6 = (CheckBox) findViewById(R.id.checkBox6);
        mCB7 = (CheckBox) findViewById(R.id.checkBox7);
        mCB8 = (CheckBox) findViewById(R.id.checkBox8);
        mCB9 = (CheckBox) findViewById(R.id.checkBox9);
        mCB10 = (CheckBox) findViewById(R.id.checkBox10);
        mCB11 = (CheckBox) findViewById(R.id.checkBox11);
        mCB12 = (CheckBox) findViewById(R.id.checkBox12);
        mCB13 = (CheckBox) findViewById(R.id.checkBox13);
        mCB14 = (CheckBox) findViewById(R.id.checkBox14);
        mCB15 = (CheckBox) findViewById(R.id.checkBox15);
        mCB16 = (CheckBox) findViewById(R.id.checkBox16);
        mCB17 = (CheckBox) findViewById(R.id.checkBox17);
        mCB18 = (CheckBox) findViewById(R.id.checkBox18);
        mCB19 = (CheckBox) findViewById(R.id.checkBox19);
        mCB20 = (CheckBox) findViewById(R.id.checkBox20);
        mCBX = (CheckBox) findViewById(R.id.checkBoxX);

        mET1 = (EditText) findViewById(R.id.editText);
        mET2 = (EditText) findViewById(R.id.editText2);
        mET3 = (EditText) findViewById(R.id.editText3);
        mET4 = (EditText) findViewById(R.id.editText4);
        mET5 = (EditText) findViewById(R.id.editText5);
        mET6 = (EditText) findViewById(R.id.editText6);
        mET7 = (EditText) findViewById(R.id.editText7);
        mET8 = (EditText) findViewById(R.id.editText8);
        mET9 = (EditText) findViewById(R.id.editText9);
        mET10 = (EditText) findViewById(R.id.editText10);
        mET11 = (EditText) findViewById(R.id.editText11);
        mET12 = (EditText) findViewById(R.id.editText12);
        mET13 = (EditText) findViewById(R.id.editText13);
        mET14 = (EditText) findViewById(R.id.editText14);
        mET15 = (EditText) findViewById(R.id.editText15);
        mET16 = (EditText) findViewById(R.id.editText16);
        mET17 = (EditText) findViewById(R.id.editText17);
        mET18 = (EditText) findViewById(R.id.editText18);
        mET19 = (EditText) findViewById(R.id.editText19);
        mET20 = (EditText) findViewById(R.id.editText20);


        nextBtn = (FloatingActionButton) findViewById(R.id.nextBtn);
        backBtn = (FloatingActionButton) findViewById(R.id.backBtn);
        cancelBtn = (FloatingActionButton) findViewById(R.id.cancelBtn);

        mStepTwoFormView = findViewById(R.id.stepTwo_form);
        mProgressView = findViewById(R.id.StepTwo_progress);
        mEngMaintenance = findViewById(R.id.EngMaintenance);

        co = (Customer) getIntent().getSerializableExtra("CustomerOrder");

//        SelectedServices = customer.getServices();
//        SelectedServiceDescription = customer.getServicesDescription();

        //Toast.makeText(getApplicationContext(), customer.getCustEmail() + "  " + customer.getSmId() , Toast.LENGTH_LONG).show();
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocListener l = new LocListener();

        //
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, l);

//        setCustServicesAndDescription();
        CBonCkeck();


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getCustServicesAndDescription()) {
                    Toast.makeText(getApplicationContext(),
                            notSelectError,
                            Toast.LENGTH_LONG).show();
                } else {
                    getCustServicesAndDescription();

//                    customer.setServices(SelectedServices);
//                    customer.setServicesDescription(SelectedServiceDescription);

                    //if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //  Toast.makeText(getApplicationContext(), GPSError, Toast.LENGTH_LONG).show();
                    //} else
                    if (!CheckServiceDescription()) {
                        Toast.makeText(getApplicationContext(), DescriptionServiceError, Toast.LENGTH_LONG).show();
                    } else {
                        //customer.setLon("" + l.getLon());
                        //customer.setLat("" + l.getLat());


                        if (isNetworkAvailable()) {
                            Toast.makeText(getApplicationContext(), StartingUploadingInfo, Toast.LENGTH_LONG).show();
                            nextBtn.setVisibility(View.GONE);
                            backBtn.setVisibility(View.GONE);
                            cancelBtn.setVisibility(View.GONE);
                            showProgress(true);
                            new save_order().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), NetworkError, Toast.LENGTH_LONG).show();
                        }

                    }
                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBtnClick();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer cust = new Customer();
//                custOrder.setSmId(customer.getSmId());

                Intent i = new Intent(getApplicationContext(), CustomerInfoActivity.class);
                i.putExtra("CustomerOrder", cust);
                i.putExtra("Lang", Lang);
                startActivity(i);
            }
        });

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
    }

//    private void setCustServicesAndDescription() {
//        if (SelectedServices.size() > 0) {
//            for (int i = 0; i < SelectedServices.size(); i++) {
//                switch (customer.getServices().get(i)) {
//                    case 1:
//                        mCB1.setChecked(true);
//                        mET1.setText(SelectedServiceDescription.get(1).toString());
//                        break;
//                    case 2:
//                        mCB2.setChecked(true);
//                        mET2.setText(SelectedServiceDescription.get(2).toString());
//                        break;
//                    case 3:
//                        mCB3.setChecked(true);
//                        mET3.setText(SelectedServiceDescription.get(3).toString());
//                        break;
//                    case 4:
//                        mCB4.setChecked(true);
//                        mET4.setText(SelectedServiceDescription.get(4).toString());
//                        break;
//                    case 5:
//                        mCB5.setChecked(true);
//                        mET5.setText(SelectedServiceDescription.get(5).toString());
//                        break;
//                    case 6:
//                        mCB6.setChecked(true);
//                        mET6.setText(SelectedServiceDescription.get(6).toString());
//                        break;
//                    case 7:
//                        mCB7.setChecked(true);
//                        mET7.setText(SelectedServiceDescription.get(7).toString());
//                        break;
//                    case 8:
//                        mCB8.setChecked(true);
//                        mET8.setText(SelectedServiceDescription.get(8).toString());
//                        break;
//                    case 9:
//                        mCB9.setChecked(true);
//                        mET9.setText(SelectedServiceDescription.get(9).toString());
//                        break;
//                    case 10:
//                        mCB10.setChecked(true);
//                        mET10.setText(SelectedServiceDescription.get(10).toString());
//                        break;
//                    case 11:
//                        mCB11.setChecked(true);
//                        mET11.setText(SelectedServiceDescription.get(11).toString());
//                        break;
//                    case 12:
//                        mCBX.isChecked();
//                        mCB12.setChecked(true);
//                        mET12.setText(SelectedServiceDescription.get(12).toString());
//                        break;
//                    case 13:
//                        mCBX.isChecked();
//                        mCB13.setChecked(true);
//                        mET13.setText(SelectedServiceDescription.get(13).toString());
//                        break;
//                    case 14:
//                        mCBX.isChecked();
//                        mCB14.setChecked(true);
//                        mET14.setText(SelectedServiceDescription.get(14).toString());
//                        break;
//                    case 15:
//                        mCBX.isChecked();
//                        mCB15.setChecked(true);
//                        mET15.setText(SelectedServiceDescription.get(15).toString());
//                        break;
//                    case 16:
//                        mCBX.isChecked();
//                        mCB16.setChecked(true);
//                        mET16.setText(SelectedServiceDescription.get(16).toString());
//                        break;
//                    case 17:
//                        mCBX.isChecked();
//                        mCB17.setChecked(true);
//                        mET17.setText(SelectedServiceDescription.get(17).toString());
//                        break;
//                    case 18:
//                        mCBX.isChecked();
//                        mCB18.setChecked(true);
//                        mET18.setText(SelectedServiceDescription.get(18).toString());
//                        break;
//                    case 19:
//                        mCBX.isChecked();
//                        mCB19.setChecked(true);
//                        mET19.setText(SelectedServiceDescription.get(19).toString());
//                        break;
//                    case 20:
//                        mCBX.isChecked();
//                        mCB20.setChecked(true);
//                        mET20.setText(SelectedServiceDescription.get(12).toString());
//                        break;
//
//                }
//            }
//        }
//    }

    private void CBonCkeck() {
        mCB1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET1.setVisibility(View.VISIBLE);
                else mET1.setVisibility(View.GONE);
            }
        });
        mCB2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET2.setVisibility(View.VISIBLE);
                else mET2.setVisibility(View.GONE);
            }
        });
        mCB3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET3.setVisibility(View.VISIBLE);
                else mET3.setVisibility(View.GONE);
            }
        });
        mCB4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET4.setVisibility(View.VISIBLE);
                else mET4.setVisibility(View.GONE);
            }
        });
        mCB5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET5.setVisibility(View.VISIBLE);
                else mET5.setVisibility(View.GONE);
            }
        });
        mCB6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET6.setVisibility(View.VISIBLE);
                else mET6.setVisibility(View.GONE);
            }
        });
        mCB7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET7.setVisibility(View.VISIBLE);
                else mET7.setVisibility(View.GONE);
            }
        });
        mCB8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET8.setVisibility(View.VISIBLE);
                else mET8.setVisibility(View.GONE);
            }
        });
        mCB9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET9.setVisibility(View.VISIBLE);
                else mET9.setVisibility(View.GONE);
            }
        });
        mCB10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET10.setVisibility(View.VISIBLE);
                else mET2.setVisibility(View.GONE);
            }
        });
        mCB11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET11.setVisibility(View.VISIBLE);
                else mET11.setVisibility(View.GONE);
            }
        });
        mCB12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET12.setVisibility(View.VISIBLE);
                else mET12.setVisibility(View.GONE);
            }
        });
        mCB13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET13.setVisibility(View.VISIBLE);
                else mET13.setVisibility(View.GONE);
            }
        });
        mCB14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET14.setVisibility(View.VISIBLE);
                else mET14.setVisibility(View.GONE);
            }
        });
        mCB15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET15.setVisibility(View.VISIBLE);
                else mET15.setVisibility(View.GONE);
            }
        });
        mCB16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET16.setVisibility(View.VISIBLE);
                else mET16.setVisibility(View.GONE);
            }
        });
        mCB17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET17.setVisibility(View.VISIBLE);
                else mET17.setVisibility(View.GONE);
            }
        });
        mCB18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET18.setVisibility(View.VISIBLE);
                else mET18.setVisibility(View.GONE);
            }
        });
        mCB19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET19.setVisibility(View.VISIBLE);
                else mET19.setVisibility(View.GONE);
            }
        });
        mCB20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mET20.setVisibility(View.VISIBLE);
                else mET20.setVisibility(View.GONE);
            }
        });
        mCBX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEngMaintenance.setVisibility(View.VISIBLE);
                } else mEngMaintenance.setVisibility(View.GONE);
            }
        });
    }

    private void nextBtnClick() {
    }

    private void backBtnClick() {
        if (getCustServicesAndDescription()) {
//            customer.setServices(SelectedServices);
        }

        Intent i = new Intent(getApplicationContext(), CustomerInfoActivity.class);
        i.putExtra("CustomerOrder", co);
        i.putExtra("Lang", Lang);
        startActivity(i);
    }

    private boolean getCustServicesAndDescription() {
        SelectedServices = new ArrayList<Integer>();
        if (mCB1.isChecked())
            SelectedServices.add(1);
        SelectedServiceDescription.put(1, mET1.getText().toString());
        if (mCB2.isChecked())
            SelectedServices.add(2);
        SelectedServiceDescription.put(2, mET2.getText().toString());
        if (mCB3.isChecked())
            SelectedServices.add(3);
        SelectedServiceDescription.put(3, mET3.getText().toString());
        if (mCB4.isChecked())
            SelectedServices.add(4);
        SelectedServiceDescription.put(4, mET4.getText().toString());
        if (mCB5.isChecked())
            SelectedServices.add(5);
        SelectedServiceDescription.put(5, mET5.getText().toString());
        if (mCB6.isChecked())
            SelectedServices.add(6);
        SelectedServiceDescription.put(6, mET6.getText().toString());
        if (mCB7.isChecked())
            SelectedServices.add(7);
        SelectedServiceDescription.put(7, mET7.getText().toString());
        if (mCB8.isChecked())
            SelectedServices.add(8);
        SelectedServiceDescription.put(8, mET8.getText().toString());
        if (mCB9.isChecked())
            SelectedServices.add(9);
        SelectedServiceDescription.put(9, mET9.getText().toString());
        if (mCB10.isChecked())
            SelectedServices.add(10);
        SelectedServiceDescription.put(10, mET10.getText().toString());
        if (mCB11.isChecked())
            SelectedServices.add(11);
        SelectedServiceDescription.put(11, mET11.getText().toString());
        if (mCB12.isChecked())
            SelectedServices.add(12);
        SelectedServiceDescription.put(12, mET12.getText().toString());
        if (mCB13.isChecked())
            SelectedServices.add(13);
        SelectedServiceDescription.put(13, mET13.getText().toString());
        if (mCB14.isChecked())
            SelectedServices.add(14);
        SelectedServiceDescription.put(14, mET14.getText().toString());
        if (mCB15.isChecked())
            SelectedServices.add(15);
        SelectedServiceDescription.put(15, mET15.getText().toString());
        if (mCB16.isChecked())
            SelectedServices.add(16);
        SelectedServiceDescription.put(16, mET16.getText().toString());
        if (mCB17.isChecked())
            SelectedServices.add(17);
        SelectedServiceDescription.put(17, mET17.getText().toString());
        if (mCB18.isChecked())
            SelectedServices.add(18);
        SelectedServiceDescription.put(18, mET18.getText().toString());
        if (mCB19.isChecked())
            SelectedServices.add(19);
        SelectedServiceDescription.put(19, mET19.getText().toString());
        if (mCB20.isChecked())
            SelectedServices.add(20);
        SelectedServiceDescription.put(20, mET20.getText().toString());

        if (SelectedServices.size() > 0)
            return true;
        else
            return false;

    }

    private boolean CheckServiceDescription() {
//        for (int i = 0; i < customer.getServices().size(); i++)
//        {
//            if(customer.getServicesDescription().get(customer.getServices().get(i)).equals(""))
//            {
//                return false;
//            } else  {
//                return true;
//            }
//        }

        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mStepTwoFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mStepTwoFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mStepTwoFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mStepTwoFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class save_order extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
//            String sm_id = "" + customer.getSmId();
            String cust_name = co.getCustName();
            String cust_phone = co.getCustPhone();
            String Cust_email = co.getCustEmail();
            String CustAddress = co.getCustAddress();
            String lon = co.getLon();
            String lat = co.getLat();


            //String description = inputDesc.getText().toString();
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("sm_id", sm_id));
            params.add(new BasicNameValuePair("cust_name", cust_name));
            params.add(new BasicNameValuePair("cust_phone", cust_phone));
            params.add(new BasicNameValuePair("cust_email", Cust_email));
            params.add(new BasicNameValuePair("lon", lon));
            params.add(new BasicNameValuePair("lat", lat));
            params.add(new BasicNameValuePair("cust_address", CustAddress));
            JSONObject Services = new JSONObject();
            //adding services and its descriptions
            // try
            // {
            //     for(int i = 0; i < customer.getServices().size(); i++)
            //     {
            //         Services.put("service_id", "" + customer.getServices().get(i));
            //         Services.put("service_description", "" + customer.getServicesDescription().get(customer.getServices().get(i)));
            //     }
//
            // }
            // catch (JSONException e){e.printStackTrace();}

            // params.add(new BasicNameValuePair("services", Services.toString()));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_save_order, "GET", params);

            if (json == null) {
                Intent i = new Intent(getApplicationContext(), StepTowActivity.class);
                i.putExtra("crashed", true);
                i.putExtra("CustomerOrder", co);
                startActivity(i);
            } else {

                // check log cat fro response
                Log.d("Create Response", json.toString());

                // check for success tag
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    int order_id = json.getInt(TAG_ORDER_ID);

                    if (success == 1) {
                        //jrba mn '3iir al for
//                         for (int i = 0; i < customer.getServices().size(); i++) {
//                             List<NameValuePair> paramsOS = new ArrayList<NameValuePair>();
//                             paramsOS.add(new BasicNameValuePair("order_id", "" + order_id));
//                             paramsOS.add(new BasicNameValuePair("service_id", "" + customer.getServices().get(i)));
//                             paramsOS.add(new BasicNameValuePair("service_description", "" + customer.getServicesDescription().get(customer.getServices().get(i))));
//                             JSONObject jsonSO = jsonParser.makeHttpRequest(url_save_order_service, "GET", paramsOS);
//                         }
                        // successfully created product
                        Customer co = new Customer();
//                        customer.setSmId(((Customer) getIntent().getSerializableExtra("CustomerOrder")).getSmId());
                        Intent i = new Intent(getApplicationContext(), OrderSavedActivity.class);
                        i.putExtra("CustomerOrder", co);
                        i.putExtra("Lang", Lang);
                        startActivity(i);

                        // closing this screen
                        //finish();
                    } else {
                        // failed to create product
                        Intent i = new Intent(getApplicationContext(), StepTowActivity.class);
                        i.putExtra("crashed", true);
                        i.putExtra("CustomerOrder", co);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Network Connection problem..", Toast.LENGTH_LONG).show();
                }
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
