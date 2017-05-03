package com.yazan.roatableShops.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yazan.roatableShops.dao.DAO;
import com.yazan.roatableShops.models.Agent;
import com.yazan.roatableShops.models.Route;
import com.yazan.roatableShops.models.SalePoint;
import com.yazan.roatableShops.models.ShopProductCode;
import com.yazan.roatableShops.models.Visit;
import com.yazan.roatableShops.models.VisitResult;
import com.yazan.roatableShops.util.LocListener;


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

    EditText spCodeTB;
    Button searchBtn;

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
    RadioButton type8;
    //

    RadioGroup mICON;
    //
    RadioButton mYesICON;
    RadioButton mNoICON;
    //

    RadioGroup mZIM;
    //
    RadioButton mYesZIM;
    RadioButton mNoZIM;
    //

    RadioGroup mEVD;
    //
    RadioButton mYesEVD;
    RadioButton mNoEVD;
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


    TextView flyersBrochuresInitCB;
    LinearLayout flyersBrochuresInitCardHolder;

    TextView postersInitCB;
    LinearLayout postersInitCardHolder;

    TextView flyersBrochuresPresentCB;
    LinearLayout flyersBrochuresPresentCardHolder;

    TextView postersPresentCB;
    LinearLayout postersPresentCardHolder;

    TextView danglersCB;
    LinearLayout danglersCardHolder;

    Button saveBtn;
    android.support.design.widget.FloatingActionButton addCard;

    SalePoint salePoint = new SalePoint();
    Agent agent = new Agent();
    Route route = new Route();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sale_point_info_ar);

        init();

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
                if (cr.getCount() == 0) {
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

//                salePoint.setLat(cr.getString(9));
//                salePoint.setLng(cr.getString(10));
                salePoint.setRoute_desc(cr.getString(8));
                salePoint.setICON(cr.getInt(11));
                salePoint.setZIM(cr.getInt(12));
                salePoint.setEVD(cr.getInt(13));


                setSalePointInfo();
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


        saveBtn.setOnClickListener(new View.OnClickListener() {
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

                //setSalePointInfo();

                String sp_name = spNameTB.getText().toString();
                String sp_owner = spOwnerTB.getText().toString();
                String sp_address = spAddressTB.getText().toString();
                String sp_phone = spPhoneTB.getText().toString();
//                String sp_route_desc = spRoatTB.getText().toString();

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

                if (type8.isChecked()) {
                    sp_type = 7;
                }

                /**
                 *
                 */

                int icon = 2;
                if (mYesICON.isChecked()) {
                    icon = 1;
                }
                int zim = 2;
                if (mYesZIM.isChecked()) {
                    zim = 1;
                }
                int evd = 2;
                if (mYesEVD.isChecked()) {
                    evd = 1;
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

                System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

                salePoint.setSpCode(spCodeTB.getText().toString());
                salePoint.setSpName(sp_name);
                salePoint.setSpOwnerName(sp_owner);
                salePoint.setSpAddress(sp_address);
                salePoint.setSpPhone(sp_phone);
                salePoint.setSpType(sp_type);
                salePoint.setBlock_type(spBlock_type);
                salePoint.setStreet_type(spStreet_type);
                salePoint.setRoute_desc(" ");
                salePoint.setICON(icon);
                salePoint.setZIM(zim);
                salePoint.setEVD(evd);


                Visit visit = new Visit();
                visit.setAgent(agent);
                visit.setSalePoint(salePoint);
                visit.setVisit_date(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
                visit.setVisit_id();
                visit.setHolderQty(" ");
                visit.setStandQty(" ");
                visit.setStandTypes(" ");


                List<VisitResult> visitResults = getVisitResults(visit.getVisit_id());

                System.out.println(visit.getVisit_id());


                DAO dao = new DAO(getApplicationContext());


                dao.insertSalePoint(salePoint);
                dao.insertVisit(visit);

                for (final VisitResult visitResult : visitResults) {
                    dao.insertVisitResult(visitResult);
                }


                Intent i = new Intent(getApplicationContext(), SalePointOrderSavedActivity.class);
                i.putExtra("Agent", agent);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "تم تسجيل النقطه..", Toast.LENGTH_LONG).show();

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


        danglersCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCardHolder(danglersCardHolder, danglersCB);
            }
        });

        flyersBrochuresInitCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCardHolder(flyersBrochuresInitCardHolder, flyersBrochuresInitCB);
            }
        });

        postersInitCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCardHolder(postersInitCardHolder, postersInitCB);
            }
        });

        flyersBrochuresPresentCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCardHolder(flyersBrochuresPresentCardHolder, flyersBrochuresPresentCB);
            }
        });

        postersPresentCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCardHolder(postersPresentCardHolder, postersPresentCB);
            }
        });


        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopProductCode productCode = new ShopProductCode();
                List<String> codes = new ArrayList<String>();
                final LinearLayout cardHolder;
                ViewStub stub = new ViewStub(getApplicationContext());

                LinearLayout.LayoutParams params = new AppBarLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                if (flyersBrochuresInitCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = flyersBrochuresInitCardHolder;
                    stub.setLayoutResource(R.layout.sp_flyers_brochurs_init_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("f", "sp", getApplicationContext());

                } else if (postersInitCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = postersInitCardHolder;
                    stub.setLayoutResource(R.layout.sp_posters_init_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("p", "sp", getApplicationContext());

                } else if (danglersCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = danglersCardHolder;
                    stub.setLayoutResource(R.layout.sp_danglers_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("d", "sh", getApplicationContext());

                } else if (flyersBrochuresPresentCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = flyersBrochuresPresentCardHolder;
                    stub.setLayoutResource(R.layout.sp_flyers_brochurs_present_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("f", "sp", getApplicationContext());

                } else if (postersPresentCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = postersPresentCardHolder;
                    stub.setLayoutResource(R.layout.sp_posters_present_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("p", "sp", getApplicationContext());

                } else if (flyersBrochuresInitCardHolder.getVisibility() != View.VISIBLE &&
                        postersInitCardHolder.getVisibility() != View.VISIBLE) {
                    return;

                } else { // this else made to avoid: "cardHolder not initialized"
                    // on delete.setOnClickListener
                    cardHolder = flyersBrochuresInitCardHolder;

                }


                //create ad cardView
                final CardView adDetailCard = new CardView(getApplicationContext());

                adDetailCard.setLayoutParams(params);
                adDetailCard.setRadius(9);
                adDetailCard.setContentPadding(15, 15, 15, 15);
                adDetailCard.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"));
                adDetailCard.setMaxCardElevation(15);

                adDetailCard.addView(stub);
                View inflatedView = stub.inflate();

                if (codes.isEmpty()) {
                    cardHolder.removeView(adDetailCard);
                    return;
                }
                // adding items to ad_code_spinner
                Spinner adCodes = (Spinner) inflatedView.findViewById(R.id.ad_code_spinner);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.select_dialog_item, codes);
                adCodes.setAdapter(dataAdapter);


                //get the views from stub
                TextView delete = (TextView) inflatedView.findViewById(R.id.deleteTV);


                //delete the card when delete textView pressed.
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cardHolder.removeView(adDetailCard);
                    }
                });

                // add the card to the ScrollView
                cardHolder.addView(adDetailCard);


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


        spCodeTB = (EditText) findViewById(R.id.spCodeTB);

        searchBtn = (Button) findViewById(R.id.searchBtn);

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
        type8 = (RadioButton) findViewById(R.id.type8);
        //
        mICON = (RadioGroup) findViewById(R.id.ICON);
        mZIM = (RadioGroup) findViewById(R.id.ZIM);
        mEVD = (RadioGroup) findViewById(R.id.EVD);

        mYesICON = (RadioButton) findViewById(R.id.YesICON);
        mNoICON = (RadioButton) findViewById(R.id.NoICON);

        mYesZIM = (RadioButton) findViewById(R.id.YesZIM);
        mNoZIM = (RadioButton) findViewById(R.id.NoZIM);

        mYesEVD = (RadioButton) findViewById(R.id.YesEVD);
        mNoEVD = (RadioButton) findViewById(R.id.NoEVD);

        spPhoneTB = (EditText) findViewById(R.id.spPhoneTB);
        spAddressTB = (EditText) findViewById(R.id.spAddressTB);
        //spRoatTB = (EditText) findViewById(R.id.spRoatTB);

        spBlockTypeRG = (RadioGroup) findViewById(R.id.spBlockTypeRG);
        spStreetTypeRG = (RadioGroup) findViewById(R.id.spStreetypeRG);

        blockType1 = (RadioButton) findViewById(R.id.blockType1);
        blockType2 = (RadioButton) findViewById(R.id.blockType2);
        blockType3 = (RadioButton) findViewById(R.id.blockType3);
        streetType1 = (RadioButton) findViewById(R.id.streetType1);
        streetType2 = (RadioButton) findViewById(R.id.streetType2);
        streetType3 = (RadioButton) findViewById(R.id.streetType3);

        addCard = (FloatingActionButton) findViewById(R.id.addCardBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        flyersBrochuresInitCB = (TextView) findViewById(R.id.flyersBrochuresInitCB);
        flyersBrochuresInitCardHolder = (LinearLayout)
                findViewById(R.id.flyersBrochuresInitCardHolder);

        postersInitCB = (TextView) findViewById(R.id.postersInitCB);
        postersInitCardHolder = (LinearLayout) findViewById(R.id.postersInitCardHolder);

        flyersBrochuresPresentCB = (TextView) findViewById(R.id.flyersBrochuresPresentCB);
        flyersBrochuresPresentCardHolder = (LinearLayout)
                findViewById(R.id.flyersBrochuresPresentCardHolder);

        postersPresentCB = (TextView) findViewById(R.id.postersPresentCB);
        postersPresentCardHolder = (LinearLayout) findViewById(R.id.postersPresentCardHolder);

        danglersCB = (TextView) findViewById(R.id.DanglersInitCB);
        danglersCardHolder = (LinearLayout) findViewById(R.id.DanglerssInitCardHolder);


        if (salePoint != null && route != null) {

            spCodeTB.setText(salePoint.getSpCode());
            spNameTB.setText(salePoint.getSpName());
            spOwnerTB.setText(salePoint.getSpOwnerName());
            ((RadioButton) spTypeRG.getChildAt(salePoint.getSpType())).setChecked(true);
            spPhoneTB.setText(salePoint.getSpPhone());
            //spRoatTB.setText(route.getRoute_description());
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
            // spRoatTB.setText("");
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


        int indexICON = salePoint.getICON();
        RadioButton Icon = (RadioButton) mICON.getChildAt(indexICON);
        Icon.setChecked(true);

        int indexZIM = salePoint.getZIM();
        RadioButton Zim = (RadioButton) mZIM.getChildAt(indexZIM);
        Zim.setChecked(true);

        int indexEVD = salePoint.getICON();
        RadioButton Evd = (RadioButton) mEVD.getChildAt(indexEVD);
        Evd.setChecked(true);

        int indexStreet = salePoint.getStreet_type();
        RadioButton streetType = (RadioButton) spStreetTypeRG.getChildAt(indexStreet);
        streetType.setChecked(true);

        int indexBlock = salePoint.getBlock_type();
        RadioButton streetBlock = (RadioButton) spBlockTypeRG.getChildAt(indexBlock);
        streetBlock.setChecked(true);

        spPhoneTB.setText(salePoint.getSpPhone());
        spAddressTB.setText(salePoint.getSpAddress());
        //spRoatTB.setText(salePoint.getRoute_desc());

        lat = salePoint.getLat();
        lng = salePoint.getLng();
    }


    @Override
    public void onBackPressed() {
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     *
     */


    public List<VisitResult> getVisitResults(String visit_id) {
        List<VisitResult> visitResults = new ArrayList<>();

        //

        for (int i = danglersCardHolder.getChildCount() - 1; i >= 0; i--) {
            final View child = danglersCardHolder.getChildAt(i);
            if (child instanceof CardView) {
                VisitResult vr = new VisitResult();

                final View adDetails = ((CardView) child).getChildAt(0);
                Spinner adCodesSpinner = (Spinner) adDetails.findViewById(R.id.ad_code_spinner);
                EditText adQTY = (EditText) adDetails.findViewById(R.id.ad_qty_spinner);

                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY(adQTY.getText().toString());
                vr.setWhen("Initial");
                vr.setVisit_id(visit_id);

                visitResults.add(vr);
            }
        }

        for (int i = flyersBrochuresInitCardHolder.getChildCount() - 1; i >= 0; i--) {
            final View child = flyersBrochuresInitCardHolder.getChildAt(i);
            if (child instanceof CardView) {
                VisitResult vr = new VisitResult();

                final View adDetails = ((CardView) child).getChildAt(0);
                Spinner adCodesSpinner = (Spinner) adDetails.findViewById(R.id.ad_code_spinner);
                EditText adQTY = (EditText) adDetails.findViewById(R.id.ad_qty_spinner);

                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY(adQTY.getText().toString());
                vr.setWhen("Initial");
                vr.setVisit_id(visit_id);

                visitResults.add(vr);
            }
        }

        for (int i = postersInitCardHolder.getChildCount() - 1; i >= 0; i--) {
            final View child = postersInitCardHolder.getChildAt(i);
            if (child instanceof CardView) {
                VisitResult vr = new VisitResult();

                final View adDetails = ((CardView) child).getChildAt(0);
                Spinner adCodesSpinner = (Spinner) adDetails.findViewById(R.id.ad_code_spinner);
                EditText adQTY = (EditText) adDetails.findViewById(R.id.ad_qty_spinner);

                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY(adQTY.getText().toString());
                vr.setWhen("Initial");
                vr.setVisit_id(visit_id);

                visitResults.add(vr);
            }
        }

        for (int i = flyersBrochuresPresentCardHolder.getChildCount() - 1; i >= 0; i--) {
            final View child = flyersBrochuresPresentCardHolder.getChildAt(i);
            if (child instanceof CardView) {
                VisitResult vr = new VisitResult();

                final View adDetails = ((CardView) child).getChildAt(0);
                Spinner adCodesSpinner = (Spinner) adDetails.findViewById(R.id.ad_code_spinner);
                EditText adQTY = (EditText) adDetails.findViewById(R.id.ad_qty_spinner);

                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY(adQTY.getText().toString());
                vr.setWhen("Present");
                vr.setVisit_id(visit_id);

                visitResults.add(vr);
            }
        }

        for (int i = postersPresentCardHolder.getChildCount() - 1; i >= 0; i--) {
            final View child = postersPresentCardHolder.getChildAt(i);
            if (child instanceof CardView) {
                VisitResult vr = new VisitResult();

                final View adDetails = ((CardView) child).getChildAt(0);
                Spinner adCodesSpinner = (Spinner) adDetails.findViewById(R.id.ad_code_spinner);
                EditText adQTY = (EditText) adDetails.findViewById(R.id.ad_qty_spinner);


                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY(adQTY.getText().toString());
                vr.setWhen("Present");
                vr.setVisit_id(visit_id);

                visitResults.add(vr);
            }
        }


        return visitResults;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.acts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.GoSync:
                Intent i = new Intent(getApplicationContext(), SalePointOrderSavedActivity.class);
                startActivity(i);
                return true;

            case R.id.GoInfo:
                Intent in = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                startActivity(in);
                return true;

            case R.id.GoMain:
                Intent in_ = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in_);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCardHolder(LinearLayout CardHolder1, TextView CB1) {

        boolean checkVisible = CardHolder1.getVisibility() == View.VISIBLE;

        danglersCB.setBackgroundColor(Color.parseColor("#DCDCDC"));
        flyersBrochuresInitCB.setBackgroundColor(Color.parseColor("#DCDCDC"));
        postersInitCB.setBackgroundColor(Color.parseColor("#DCDCDC"));
        flyersBrochuresPresentCB.setBackgroundColor(Color.parseColor("#DCDCDC"));
        postersPresentCB.setBackgroundColor(Color.parseColor("#DCDCDC"));

        danglersCardHolder.setVisibility(View.GONE);
        flyersBrochuresInitCardHolder.setVisibility(View.GONE);
        postersInitCardHolder.setVisibility(View.GONE);
        flyersBrochuresPresentCardHolder.setVisibility(View.GONE);
        postersPresentCardHolder.setVisibility(View.GONE);

        if (checkVisible) {
            CardHolder1.setVisibility(View.GONE);
            CB1.setBackgroundColor(Color.parseColor("#DCDCDC"));
        } else {
            CardHolder1.setVisibility(View.VISIBLE);
            CB1.setBackgroundColor(Color.parseColor("#D8BFD8")); // perble
        }
    }

}
