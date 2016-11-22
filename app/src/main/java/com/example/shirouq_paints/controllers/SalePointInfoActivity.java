package com.example.shirouq_paints.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shirouq_paints.dao.DAO;
import com.example.shirouq_paints.models.Agent;
import com.example.shirouq_paints.models.Route;
import com.example.shirouq_paints.models.SalePoint;
import com.example.shirouq_paints.models.Visit;
import com.example.shirouq_paints.models.VisitResult;
import com.example.shirouq_paints.util.JSONParser;
import com.example.shirouq_paints.util.LocListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalePointInfoActivity extends AppCompatActivity {

    private String Lang;

    private String lat;
    private String lng;

    private String sp_code;

    int orderTableCount;

    View mProgressView;
    View searchLayout;

    View salePointInfo;
    View stepTwo_form;


    View zainInfo;
    View mtnInfo;
    View sudaniInfo;


    View zainInc;
    View mtnInc;
    View sudaniInc;

    EditText spCodeTB;
    Button searchBtn;
    FloatingActionButton locationBtn;

    EditText spNameTB;
    EditText spOwnerTB;
    RadioGroup spTypeRG;
    RadioButton typeRB;
    //
    RadioButton type1;
    RadioButton type2;
    RadioButton type3;
    RadioButton type4;
    RadioButton type5;
    RadioButton type6;
    RadioButton type7;
    //
    EditText spPhoneTB;
    EditText spAddressTB;
    EditText spRoatTB;

    RadioGroup spBlockTypeRG;

    RadioButton blockType1;
    RadioButton blockType2;
    RadioButton blockType3;

    RadioGroup spStreetTypeRG;

    RadioButton streetType1;
    RadioButton streetType2;
    RadioButton streetType3;

    TextView zain;
    TextView mtn;
    TextView sudani;

    CheckBox zainStickCB;
    CheckBox zainDangCB;
    CheckBox zainWashCB;

    View zainStick_details;
    View zainDang_details;
    View zainWash_details;

    RadioGroup zainStickStatuse_RG;
    RadioGroup zainStickBadReason_RG;
    RadioGroup zainDangStatuse_RG;
    RadioGroup zainDangBadReason_RG;
    RadioGroup zainWashStatuse_RG;
    RadioGroup zainWashBadReason_RG;

    CheckBox mtnStickCB;
    CheckBox mtnDangCB;
    CheckBox mtnWashCB;

    View mtnStick_details;
    View mtnDang_details;
    View mtnWash_details;

    RadioGroup mtnStickStatuse_RG;
    RadioGroup mtnStickBadReason_RG;
    RadioGroup mtnDangStatuse_RG;
    RadioGroup mtnDangBadReason_RG;
    RadioGroup mtnWashStatuse_RG;
    RadioGroup mtnWashBadReason_RG;

    CheckBox sudaniStickCB;
    CheckBox sudaniDangCB;
    CheckBox sudaniWashCB;

    View sudaniStick_details;
    View sudaniDang_details;
    View sudaniWash_details;

    RadioGroup sudaniStickStatuse_RG;
    RadioGroup sudaniStickBadReason_RG;
    RadioGroup sudaniDangStatuse_RG;
    RadioGroup sudaniDangBadReason_RG;
    RadioGroup sudaniWashStatuse_RG;
    RadioGroup sudaniWashBadReason_RG;

    Button saveBtn;

    SalePoint salePoint = new SalePoint();
    Agent agent = new Agent();
    Route route = new Route();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_sale_point_info_ar);

        init();
        setZainListeners();
        setMtnListeners();
        setSudaniListener();


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spCodeTB.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "enter a code ..", Toast.LENGTH_LONG).show();
                    return;
                }

                sp_code = spCodeTB.getText().toString();
                //new SalePointService().execute();

                DAO DAO = new DAO(getApplicationContext());
                Cursor cr = DAO.getSalePoint_(sp_code);


                //if (!(cr.moveToFirst()) || cr.getCount() ==0)
                if (cr.getCount() ==0){
                    salePoint.setSpCode(sp_code);
                    return;
                }

                cr.moveToFirst();

                salePoint.setSpCode(cr.getString(0));
                salePoint.setSpAddress(cr.getString(1));
                salePoint.setSpName(cr.getString(2));
                salePoint.setSpOwnerName(cr.getString(3));
                salePoint.setSpPhone(cr.getString(4));
                salePoint.setSpType(Integer.parseInt(cr.getString(5)));
                salePoint.setStreet_type(Integer.parseInt(cr.getString(6)));
                salePoint.setBlock_type(Integer.parseInt(cr.getString(7)));

                salePoint.setLat(cr.getString(9));
                salePoint.setLng(cr.getString(10));
                salePoint.setRoute_desc(cr.getString(8));


                setSalePointInfo();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sp_name = spNameTB.getText().toString();
                String sp_owner = spOwnerTB.getText().toString();
                String sp_address = spAddressTB.getText().toString();
                String sp_phone = spPhoneTB.getText().toString();
                String sp_route_desc = spRoatTB.getText().toString();

                int sp_type = 0;
                if (type1.isChecked()) {
                    sp_type = 0;
                }

                if (type2.isChecked()) {
                    sp_type = 1;
                }

                if (type3.isChecked()) {
                    sp_type = 2;
                }
                if (type4.isChecked()) {
                    sp_type = 3;
                }

                if (type5.isChecked()) {
                    sp_type = 4;
                }
                if (type6.isChecked()) {
                    sp_type = 5;
                }
                if (type7.isChecked()) {
                    sp_type = 6;
                }

                /**
                 *
                 */

                int spStreet_type = 0;
                if (streetType1.isChecked()) {
                    spStreet_type = 0;
                }

                if (streetType2.isChecked()) {
                    spStreet_type = 1;
                }

                if (streetType3.isChecked()) {
                    spStreet_type = 2;
                }


                /**
                 *
                 */

                int spBlock_type = 0;
                if (blockType1.isChecked()) {
                    spBlock_type = 0;
                }

                if (blockType2.isChecked()) {
                    spBlock_type = 1;
                }

                if (blockType3.isChecked()) {
                    spBlock_type = 2;
                }

                if (sp_name.isEmpty() || sp_owner.isEmpty() ||
                        sp_address.isEmpty() || sp_phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "إملاء جميع الفراغات...", Toast.LENGTH_LONG).show();
                    return;
                }

                if (salePoint.getLat() == null || salePoint.getLng() == null) {
                    Toast.makeText(getApplicationContext(), "Please Set A Location .. !", Toast.LENGTH_LONG).show();
                    return;
                }

                System.out.println(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));

                salePoint.setSpCode(spCodeTB.getText().toString());
                salePoint.setSpName(sp_name);
                salePoint.setSpOwnerName(sp_owner);
                salePoint.setSpAddress(sp_address);
                salePoint.setSpPhone(sp_phone);
                salePoint.setSpType(sp_type);
                salePoint.setBlock_type(spBlock_type);
                salePoint.setStreet_type(spStreet_type);
                salePoint.setRoute_desc(sp_route_desc);


                Visit visit = new Visit();
                visit.setAgent(agent);
                visit.setSalePoint(salePoint);
                visit.setVisit_date(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
                visit.setVisit_id();

                List<VisitResult> visitResults = getAllSellected(visit.getVisit_id());

                System.out.println(salePoint.getLng());


                DAO dao = new DAO(getApplicationContext());


                dao.insertSalePoint(salePoint);
                dao.insertVisit(visit);

                for(final VisitResult visitResult : visitResults) {
                    dao.insertVisitResult(visitResult);
                }


                Intent i = new Intent(getApplicationContext(), OrderSavedActivity.class);
                i.putExtra("Agent", agent);
                startActivity(i);

                //new UpdateSalePointService().execute();

            }
        });


        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocListener l = new LocListener();
        //
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, l);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spCodeTB.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Code assigned.. !!", Toast.LENGTH_LONG).show();
                }

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(getApplicationContext(), "GPS not Enabled.. !!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (l.getLat() == 0.0 || l.getLon() == 0.0) {
                    Toast.makeText(getApplicationContext(), "Wait for GPS till get a location.. !!", Toast.LENGTH_LONG).show();
                    return;
                }

                salePoint.setLat("" + l.getLat());
                salePoint.setLng("" + l.getLon());

                System.out.println(salePoint.getLng());

                Toast.makeText(getApplicationContext(), "تم تسجيل النقطه..", Toast.LENGTH_LONG).show();
                //setSalePointInfo();


            }
        });

        spAddressTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        spCodeTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        spNameTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        spOwnerTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        spPhoneTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    private void setSudaniListener() {
        /**
         * sudani check..
         */

        sudani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sudaniInfo.getVisibility() == View.VISIBLE) {
                    sudaniInfo.setVisibility(View.GONE);
                } else {
                    sudaniInfo.setVisibility(View.VISIBLE);
                }
            }
        });

        sudaniStickCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (sudaniStickCB.isChecked()) {
                    setRBEnable(sudaniStickStatuse_RG, true);
                } else {
                    setRBEnable(sudaniStickStatuse_RG, false);
                }
            }
        });

        sudaniDangCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sudaniDangCB.isChecked()) {
                    setRBEnable(sudaniDangStatuse_RG, true);
                } else {
                    setRBEnable(sudaniDangStatuse_RG, false);
                }
            }
        });

        sudaniWashCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sudaniWashCB.isChecked()) {
                    setRBEnable(sudaniWashStatuse_RG, true);
                } else {
                    setRBEnable(sudaniWashStatuse_RG, false);
                }
            }
        });

        sudaniStickStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(sudaniStickStatuse_RG, sudaniStick_details);
            }
        });


        sudaniDangStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(sudaniDangStatuse_RG, sudaniDang_details);
            }
        });

        sudaniWashStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(sudaniWashStatuse_RG, sudaniWash_details);
            }
        });

        /**
         *
         */}

    private void setMtnListeners() {
        /**
         * mtn check..
         */

        mtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mtnInfo.getVisibility() == View.VISIBLE) {
                    mtnInfo.setVisibility(View.GONE);
                } else {
                    mtnInfo.setVisibility(View.VISIBLE);
                }
            }
        });

        mtnStickCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mtnStickCB.isChecked()) {
                    setRBEnable(mtnStickStatuse_RG, true);
                    //
                } else {
                    setRBEnable(mtnStickStatuse_RG, false);
                }
            }
        });

        mtnDangCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mtnDangCB.isChecked()) {
                    setRBEnable(mtnDangStatuse_RG, true);
                } else {
                    setRBEnable(mtnDangStatuse_RG, false);
                }
            }
        });

        mtnWashCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mtnWashCB.isChecked()) {
                    setRBEnable(mtnWashStatuse_RG, true);
                } else {
                    setRBEnable(mtnWashStatuse_RG, false);
                }
            }
        });


        mtnStickStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(mtnStickStatuse_RG, mtnStick_details);
            }
        });


        mtnDangStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(mtnDangStatuse_RG, mtnDang_details);
            }
        });

        mtnWashStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(mtnWashStatuse_RG, mtnWash_details);
            }
        });
    }

    private void setZainListeners() {
        /**
         * zain check
         */

        zain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zainInfo.getVisibility() == View.VISIBLE) {
                    zainInfo.setVisibility(View.GONE);
                } else {
                    zainInfo.setVisibility(View.VISIBLE);
                }
            }
        });

        zainStickCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (zainStickCB.isChecked()) {
                    setRBEnable(zainStickStatuse_RG, true);

                } else {
                    setRBEnable(zainStickStatuse_RG, false);
                }
            }
        });

        zainDangCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (zainDangCB.isChecked()) {
                    setRBEnable(zainDangStatuse_RG, true);
                } else {
                    setRBEnable(zainDangStatuse_RG, false);
                }
            }
        });

        zainWashCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (zainWashCB.isChecked()) {
                    setRBEnable(zainWashStatuse_RG, true);
                } else {
                    setRBEnable(zainWashStatuse_RG, false);
                }
            }
        });


        zainStickStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(zainStickStatuse_RG, zainStick_details);
            }
        });


        zainDangStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(zainDangStatuse_RG, zainDang_details);
            }
        });

        zainWashStatuse_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setAdStatus_RG_OnCheck(zainWashStatuse_RG, zainWash_details);
            }
        });
    }

    private void init() {
        if (getIntent().getBooleanExtra("crashed", false) == true) {
            Toast.makeText(getApplicationContext(), "server down :/", Toast.LENGTH_LONG).show();
        }

        if ((SalePoint) getIntent().getSerializableExtra("SalePoint") != null) {

            salePoint = (SalePoint) getIntent().getSerializableExtra("SalePoint");
            agent = (Agent) getIntent().getSerializableExtra("Agent");
            agent.setA_id(1);
            agent.setR_id("1");

            route = (Route) getIntent().getSerializableExtra("Route");
        } else {
            salePoint = new SalePoint();
            agent = new Agent();
            agent.setA_id(1);
            agent.setR_id("1");

            route = new Route();
        }


        mProgressView = findViewById(R.id.pbar);
        searchLayout = findViewById(R.id.searchLayout);
        salePointInfo = findViewById(R.id.salePointInfo);
        stepTwo_form = findViewById(R.id.stepTwo_form);
        zainInfo = findViewById(R.id.zainInfo);
        mtnInfo = findViewById(R.id.mtnInfo);
        sudaniInfo = findViewById(R.id.sudaniInfo);

        spCodeTB = (EditText) findViewById(R.id.spCodeTB);

        searchBtn = (Button) findViewById(R.id.searchBtn);

        locationBtn = (FloatingActionButton) findViewById(R.id.LocationBtn);

        spNameTB = (EditText) findViewById(R.id.spNameTB);
        spOwnerTB = (EditText) findViewById(R.id.spOwnerNameTB);
        spTypeRG = (RadioGroup) findViewById(R.id.spTypeRG);
        //
        type1 = (RadioButton) findViewById(R.id.type1);
        type2 = (RadioButton) findViewById(R.id.type2);
        type3 = (RadioButton) findViewById(R.id.type3);
        type4 = (RadioButton) findViewById(R.id.type4);
        type5 = (RadioButton) findViewById(R.id.type5);
        type6 = (RadioButton) findViewById(R.id.type6);
        type7 = (RadioButton) findViewById(R.id.type7);
        //
        spPhoneTB = (EditText) findViewById(R.id.spPhoneTB);
        spAddressTB = (EditText) findViewById(R.id.spAddressTB);
        spRoatTB = (EditText) findViewById(R.id.spRoatTB);

        spBlockTypeRG = (RadioGroup) findViewById(R.id.spBlockTypeRG);
        spStreetTypeRG = (RadioGroup) findViewById(R.id.spStreetypeRG);

        blockType1 = (RadioButton) findViewById(R.id.blockType1);
        blockType2 = (RadioButton) findViewById(R.id.blockType2);
        blockType3 = (RadioButton) findViewById(R.id.blockType3);
        streetType1 = (RadioButton) findViewById(R.id.streetType1);
        streetType2 = (RadioButton) findViewById(R.id.streetType2);
        streetType3 = (RadioButton) findViewById(R.id.streetType3);

        zain = (TextView) findViewById(R.id.zainCB);
        mtn = (TextView) findViewById(R.id.mtnCB);
        sudani = (TextView) findViewById(R.id.sudaniCB);

        zainInc = (View) findViewById(R.id.zain_details);
        mtnInc = (View) findViewById(R.id.mtn_details);
        sudaniInc = (View) findViewById(R.id.sudani_details);

        saveBtn = (Button) findViewById(R.id.saveBtn);

        zainStickCB = (CheckBox) zainInc.findViewById(R.id.StickCkB);
        zainDangCB = (CheckBox) zainInfo.findViewById(R.id.DangCB);
        zainWashCB = (CheckBox) zainInfo.findViewById(R.id.WashCB);

        zainStick_details = zainInfo.findViewById(R.id.adType1);
        zainStickStatuse_RG = (RadioGroup) zainStick_details.findViewById(R.id.AdStatusGroup);
        zainStickBadReason_RG = (RadioGroup) zainStick_details.findViewById(R.id.BadAdStatusGroup);

        zainDang_details = zainInfo.findViewById(R.id.adType2);
        zainDangStatuse_RG = (RadioGroup) zainDang_details.findViewById(R.id.AdStatusGroup);
        zainDangBadReason_RG = (RadioGroup) zainDang_details.findViewById(R.id.BadAdStatusGroup);

        zainWash_details = zainInfo.findViewById(R.id.adType3);
        zainWashStatuse_RG = (RadioGroup) zainWash_details.findViewById(R.id.AdStatusGroup);
        zainWashBadReason_RG = (RadioGroup) zainWash_details.findViewById(R.id.BadAdStatusGroup);

        mtnStickCB = (CheckBox) mtnInfo.findViewById(R.id.StickCkB);
        mtnDangCB = (CheckBox) mtnInfo.findViewById(R.id.DangCB);
        mtnWashCB = (CheckBox) mtnInfo.findViewById(R.id.WashCB);

        mtnStick_details = mtnInfo.findViewById(R.id.adType1);
        mtnStickStatuse_RG = (RadioGroup) mtnStick_details.findViewById(R.id.AdStatusGroup);
        mtnStickBadReason_RG = (RadioGroup) mtnStick_details.findViewById(R.id.BadAdStatusGroup);

        mtnDang_details = mtnInfo.findViewById(R.id.adType2);
        mtnDangStatuse_RG = (RadioGroup) mtnDang_details.findViewById(R.id.AdStatusGroup);
        mtnDangBadReason_RG = (RadioGroup) mtnDang_details.findViewById(R.id.BadAdStatusGroup);

        mtnWash_details = mtnInfo.findViewById(R.id.adType3);
        mtnWashStatuse_RG = (RadioGroup) mtnWash_details.findViewById(R.id.AdStatusGroup);
        mtnWashBadReason_RG = (RadioGroup) mtnWash_details.findViewById(R.id.BadAdStatusGroup);

        sudaniStickCB = (CheckBox) sudaniInfo.findViewById(R.id.StickCkB);
        sudaniDangCB = (CheckBox) sudaniInfo.findViewById(R.id.DangCB);
        sudaniWashCB = (CheckBox) sudaniInfo.findViewById(R.id.WashCB);

        sudaniStick_details = sudaniInfo.findViewById(R.id.adType1);
        sudaniStickStatuse_RG = (RadioGroup) sudaniStick_details.findViewById(R.id.AdStatusGroup);
        sudaniStickBadReason_RG = (RadioGroup) sudaniStick_details.findViewById(R.id.BadAdStatusGroup);

        sudaniDang_details = sudaniInfo.findViewById(R.id.adType2);
        sudaniDangStatuse_RG = (RadioGroup) sudaniDang_details.findViewById(R.id.AdStatusGroup);
        sudaniDangBadReason_RG = (RadioGroup) sudaniDang_details.findViewById(R.id.BadAdStatusGroup);

        sudaniWash_details = sudaniInfo.findViewById(R.id.adType3);
        sudaniWashStatuse_RG = (RadioGroup) sudaniWash_details.findViewById(R.id.AdStatusGroup);
        sudaniWashBadReason_RG = (RadioGroup) sudaniWash_details.findViewById(R.id.BadAdStatusGroup);

        setRBEnable(zainStickStatuse_RG, false);
        setRBEnable(zainDangStatuse_RG, false);
        setRBEnable(zainWashStatuse_RG, false);
        setRBEnable(mtnStickStatuse_RG, false);
        setRBEnable(mtnDangStatuse_RG, false);
        setRBEnable(mtnWashStatuse_RG, false);
        setRBEnable(sudaniStickStatuse_RG, false);
        setRBEnable(sudaniDangStatuse_RG, false);
        setRBEnable(sudaniWashStatuse_RG, false);


        if (salePoint != null && route != null) {

            spCodeTB.setText(salePoint.getSpCode());
            spNameTB.setText(salePoint.getSpName());
            spOwnerTB.setText(salePoint.getSpOwnerName());
            ((RadioButton) spTypeRG.getChildAt(salePoint.getSpType())).setChecked(true);
            spPhoneTB.setText(salePoint.getSpPhone());
            spRoatTB.setText(route.getRoute_description());
            spAddressTB.setText(salePoint.getSpAddress());

            ((RadioButton) spBlockTypeRG.getChildAt(salePoint.getBlock_type())).setChecked(true);
            ((RadioButton) spStreetTypeRG.getChildAt(salePoint.getStreet_type())).setChecked(true);
            // locationBtn.setVisibility(View.VISIBLE);


        } else {
            if (getIntent().getStringExtra("message") != null) {
                Toast.makeText(getApplicationContext(),
                        getIntent().getStringExtra("message"),
                        Toast.LENGTH_LONG).show();
            }

            spCodeTB.setEnabled(true);
            searchBtn.setEnabled(true);
        }

        if (getIntent().getStringExtra("up") != null) {
            spOwnerTB.setText("");
            spNameTB.setText("");
            spCodeTB.setText("");
            spAddressTB.setText("");
            spPhoneTB.setText("");
            spRoatTB.setText("");
        }
    }

    private void setRBEnable(RadioGroup rg, boolean checked) {
        for (int i = 0; i < rg.getChildCount(); i++) {
            ((RadioButton) rg.getChildAt(i)).setClickable(checked);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.
                INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setSalePointInfo() {
        salePointInfo.setVisibility(View.VISIBLE);

        spCodeTB.setText(salePoint.getSpCode());
        spNameTB.setText(salePoint.getSpName());
        spOwnerTB.setText(salePoint.getSpOwnerName());


        int index = salePoint.getSpType();

        typeRB = (RadioButton) spTypeRG.getChildAt(index);
        typeRB.setChecked(true);

        int indexStreet = salePoint.getStreet_type();
        RadioButton streetType = (RadioButton) spStreetTypeRG.getChildAt(indexStreet);
        streetType.setChecked(true);

        int indexBlock = salePoint.getBlock_type();
        RadioButton streetBlock = (RadioButton) spBlockTypeRG.getChildAt(indexBlock);
        streetBlock.setChecked(true);

        spPhoneTB.setText(salePoint.getSpPhone());
        spAddressTB.setText(salePoint.getSpAddress());
        spRoatTB.setText(salePoint.getRoute_desc());

        lat = salePoint.getLat();
        lng = salePoint.getLng();
    }

    private static void setAdStatus_RG_OnCheck(RadioGroup Statuse_RG, View Ad_details) {
        RadioButton excellantRB = (RadioButton) Statuse_RG.findViewById(R.id.AdStatus1);
        RadioButton goodRB = (RadioButton) Statuse_RG.findViewById(R.id.AdStatus2);
        RadioButton badRB = (RadioButton) Statuse_RG.findViewById(R.id.AdStatus3);

        if (badRB.isChecked()) {
            ((View) Ad_details.findViewById(R.id.BadAdInfoDetails)).setVisibility(View.VISIBLE);
        } else {
            ((View) Ad_details.findViewById(R.id.BadAdInfoDetails)).setVisibility(View.GONE);
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


    JSONParser SalePointjsonParser = new JSONParser();
    private static String url_sp_info = "http://consulsat-sd.com/roatable/sp_get_info.php";
    private static String url_sp_update = "http://consulsat-sd.com/roatable/update_salepoint.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
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
    private static final String TAG_ROAT_DES = "roat_description";


    /**
     *
     */


    class SalePointService extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected String doInBackground(String... args) {

            String sp_r_id = agent.getR_id();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sp_code", sp_code));
            params.add(new BasicNameValuePair("r_id", sp_r_id));

            try {

                JSONObject json = jsonParser.makeHttpRequest(url_sp_info, "GET", params);

                if (json == null) {
                    Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                    i.putExtra("crashed", true);
                    startActivity(i);
                } else {

                    Log.d("Create Response", json.toString());


                    int success = json.getInt(TAG_SUCCESS);
                    int sp_id = json.getInt(TAG_SP_ID);
                    String sp_name = json.getString(TAG_SP_NAME);
                    int sp_type = json.getInt(TAG_SP_TYPE);
                    String sp_owner = json.getString(TAG_SP_OWNER);
                    String sp_phone = json.getString(TAG_SP_PHONE);
                    String sp_address = json.getString(TAG_SP_ADDRESS);
                    String lat = json.getString(TAG_LAT);
                    String lng = json.getString(TAG_LNG);
                    String roat_id = json.getString(TAG_ROAT_ID);
                    String message = json.getString(TAG_MESSAGE);
                    String roat_description = json.getString(TAG_ROAT_DES);
                    int block_type = json.getInt(TAG_SP_BLOCK_TYPE);
                    int street_type = json.getInt(TAG_SP_STREET_TYPE);

                    if (success == 1) {
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
                        route.setRoute_description(roat_description);

                        Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                        i.putExtra("Agent", agent);
                        i.putExtra("SalePoint", salePoint);
                        i.putExtra("Route", route);
                        i.putExtra("Lang", "Arabic");
                        i.putExtra("message", message);
                        startActivity(i);

                        finish();
                    } else if (success == 2) {
                        Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                        i.putExtra("crashed", false);
                        i.putExtra("Agent", agent);
                        i.putExtra("SalePoint", salePoint);
                        i.putExtra("Lang", "Arabic");
                        i.putExtra("up", "");
                        i.putExtra("message", message);
                        startActivity(i);
                    }
                }
            } catch (JSONException e) {

                Log.w("JSON Exception", e.getMessage());

                Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                i.putExtra("crashed", true);
                i.putExtra("Agent", agent);
                i.putExtra("SalePoint", salePoint);
                i.putExtra("Lang", "Arabic");
                i.putExtra("message", "not connected");
                i.putExtra("up", "");
                startActivity(i);
            }

            return null;
        }


        protected void onPostExecute(String file_url) {
        }

    }



    JSONParser jsonParser = new JSONParser();
    private static String url_save_trn_header = "http://yiserver.com/shirouq_sales_ws/save_trn_header.php"; // TRN Header.
    private static String url_save_trn_details = "http://yiserver.com/shirouq_sales_ws/save_trn_details.php"; // TRN Details.
    private static final String TAG_TRN_ID = "trn_id";


    class SaveOrderService extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected boolean insertIntoTRNDetails(TableLayout table, String TRNCode, int i) {

            Log.w("Create Response", "starting saving in trn details..");

            TableRow mRow = (TableRow) table.getChildAt(i);
            String product = ((TextView) mRow.getChildAt(3)).getText().toString();
            String description = ((TextView) mRow.getChildAt(2)).getText().toString();
            String qty = ((TextView) mRow.getChildAt(1)).getText().toString();
            //
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("trn_id", TRNCode));
            //params.add(new BasicNameValuePair("product", product));
            params.add(new BasicNameValuePair("item_dis", product + " " + description));
            params.add(new BasicNameValuePair("qty", qty));
            params.add(new BasicNameValuePair("sm_id", "" + agent.getA_id()));

            JSONObject json = jsonParser.makeHttpRequest(url_save_trn_details, "GET", params);
            i--;
            return (i < 0) ? true : insertIntoTRNDetails(table, TRNCode, i);

        }

        protected String doInBackground(String... args) {
//            String sm_id = "" + salePoint.getSmId();
            String cust_name = salePoint.getSpName();
            String cust_phone = salePoint.getSpPhone();
            String Cust_code = salePoint.getSpCode();
            String CustAddress = salePoint.getSpAddress();
            String lon = salePoint.getLng();
            String lat = salePoint.getLat();


            //String description = inputDesc.getText().toString();
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("sm_id", sm_id));
            params.add(new BasicNameValuePair("cust_code", Cust_code));
            params.add(new BasicNameValuePair("sm_id", "" + agent.getA_id()));

            JSONObject Services = new JSONObject();
            //adding services and its descriptions
            JSONObject json = jsonParser.makeHttpRequest(url_save_trn_header, "GET", params);

            if (json == null) {
                Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                i.putExtra("crashed", true);
                i.putExtra("SalePoint", salePoint);
                i.putExtra("Agent", agent);
                i.putExtra("message", "Server not reached");
                i.putExtra("Lang", "Arabic");
                startActivity(i);
            } else {

                // check log cat fro response
                Log.d("Create Response", json.toString());

                // check for success tag
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    String trn_id = json.getString(TAG_TRN_ID);

                    if (success == 1) {

                        // successfully created product
                        SalePoint co = new SalePoint();
//                        salePoint.setSmId(((SalePoint) getIntent().getSerializableExtra("CustomerOrder")).getSmId());
                        Intent i = new Intent(getApplicationContext(), OrderSavedActivity.class);
                        i.putExtra("SalePoint", co);
                        i.putExtra("Agent", agent);
                        i.putExtra("Lang", Lang);
                        startActivity(i);

                        // closing this screen
                        //finish();
                    } else {
                        // failed to create product
                        Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                        i.putExtra("crashed", true);
                        i.putExtra("CustomerOrder", salePoint);
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


    public List<VisitResult> getAllSellected(String visit_id) {
        List<VisitResult> visitResults = new ArrayList<>();

        if (zainStickCB.isChecked()) {
            VisitResult visitResult = setVisitResult(1, 0, 
                    zainStickStatuse_RG, zainStickBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }

        if (zainDangCB.isChecked()) {
            VisitResult visitResult = setVisitResult(1, 1, 
                    zainDangStatuse_RG, zainDangBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }

        if (zainWashCB.isChecked()) {
            VisitResult visitResult = setVisitResult(1, 2, 
                    zainWashStatuse_RG, zainWashBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }

        if (mtnStickCB.isChecked()) {
            VisitResult visitResult = setVisitResult(2, 0,
                    mtnStickStatuse_RG, mtnStickBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }

        if (mtnDangCB.isChecked()) {
            VisitResult visitResult = setVisitResult(2, 1,
                    mtnDangStatuse_RG, mtnDangBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }

        if (mtnWashCB.isChecked()) {
            VisitResult visitResult = setVisitResult(2, 2,
                    mtnWashStatuse_RG, mtnWashBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }

        if (sudaniStickCB.isChecked()) {
            VisitResult visitResult = setVisitResult(3, 0,
                    sudaniStickStatuse_RG, sudaniStickBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }

        if (sudaniDangCB.isChecked()) {
            VisitResult visitResult = setVisitResult(3, 1,
                    sudaniDangStatuse_RG, sudaniDangBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }

        if (sudaniWashCB.isChecked()) {
            VisitResult visitResult = setVisitResult(3, 2,
                    sudaniWashStatuse_RG, sudaniWashBadReason_RG);
            visitResult.setVisit_id(visit_id);

            visitResults.add(visitResult);
        }
        

        return visitResults;
    }

    public VisitResult setVisitResult(int opr, int AdType, RadioGroup StickStatuse_RG,
                                      RadioGroup StickBadReason_RG) {
        VisitResult visitResult = new VisitResult();


        RadioButton selectedId = (RadioButton) findViewById(StickStatuse_RG.
                getCheckedRadioButtonId());
        int indexState = StickStatuse_RG.indexOfChild(selectedId);


        if (indexState == 2) {
            RadioButton selectedBadId = (RadioButton) findViewById(StickBadReason_RG.
                    getCheckedRadioButtonId());
            int indexBadState = StickBadReason_RG.indexOfChild(selectedBadId);

            visitResult.setBad_state_reason(indexBadState);
        }

        visitResult.setOperater_id(opr);
        visitResult.setAd_type(AdType);
        visitResult.setAd_state(indexState);

        return  visitResult;
    }
}
