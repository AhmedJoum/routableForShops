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
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.LinearLayout.LayoutParams;

import com.yazan.roatableShops.dao.DAO;
import com.yazan.roatableShops.models.Agent;
import com.yazan.roatableShops.models.Route;
import com.yazan.roatableShops.models.Shop;
import com.yazan.roatableShops.models.ShopProductCode;
import com.yazan.roatableShops.models.Visit;
import com.yazan.roatableShops.models.VisitResult;
import com.yazan.roatableShops.util.LocListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.support.v7.widget.CardView;

public class ShopsInfoActivity extends AppCompatActivity {

    private String sp_code;
    View mProgressView;
    View searchLayout;
    LinearLayout mOperatorDetails;

    View salePointInfo;
    View stepTwo_form;


    Spinner spNameTB;
    Spinner cityCodeTB;
    EditText HolderNoTB;
    EditText StandNoTB;

    CheckBox hCB;
    CheckBox vCB;
    CheckBox hdCB;
    CheckBox vdCB;

    RadioGroup spTypeRG;

    RadioButton shopSP;
    RadioButton franchiseSP;
    RadioButton dealerSP;

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

    Shop shop = new Shop();
    Agent agent = new Agent();
    Route route = new Route();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shops_info_ar);

        init();


        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocListener l = new LocListener();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, l);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }


        cityCodeTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DAO dao = new DAO(getApplicationContext());

                List<String> shops = new ArrayList<String>();

                Cursor cr = dao.getShopByCityCode(cityCodeTB.getSelectedItem().toString());
                if (cr.moveToFirst()) {
                    do {
                        shops.add(cr.getString(0));
                    } while (cr.moveToNext());
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ShopsInfoActivity.this,
                        android.R.layout.select_dialog_item, shops);

                spNameTB.setAdapter(dataAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spNameTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DAO dao = new DAO(getApplicationContext());

                Cursor cr = dao.getShopDetailsByName(spNameTB.getSelectedItem().toString());
                if (cr.moveToFirst()) {
                    shop.setSpCode(cr.getString(0));
                    shop.setSpName(cr.getString(1));
                    shop.setSpType(cr.getInt(2));
                    shop.setCityCode(cr.getString(3));

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(getApplicationContext(), "GPS not Enabled.. !!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (l.getLat() == 0.0 || l.getLon() == 0.0) {
                    Toast.makeText(getApplicationContext(), "Wait for GPS till get a location.. !!", Toast.LENGTH_LONG).show();
                    return;
                }

                shop.setLat("" + l.getLat());
                shop.setLng("" + l.getLon());

                System.out.println(shop.getLng());

                Toast.makeText(getApplicationContext(), "تم تسجيل النقطه..", Toast.LENGTH_LONG).show();

                saveShop();
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

                LayoutParams params = new AppBarLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );

                if (flyersBrochuresInitCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = flyersBrochuresInitCardHolder;
                    stub.setLayoutResource(R.layout.shop_flyers_brochurs_init_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("f", "sh", getApplicationContext());

                } else if (postersInitCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = postersInitCardHolder;
                    stub.setLayoutResource(R.layout.shop_posters_init_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("p", "sh", getApplicationContext());

                } else if (danglersCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = danglersCardHolder;
                    stub.setLayoutResource(R.layout.danglers_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("d", "sh", getApplicationContext());

                } else if (flyersBrochuresPresentCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = flyersBrochuresPresentCardHolder;
                    stub.setLayoutResource(R.layout.shop_flyers_brochurs_present_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("f", "sh", getApplicationContext());

                } else if (postersPresentCardHolder.getVisibility() == View.VISIBLE) {
                    cardHolder = postersPresentCardHolder;
                    stub.setLayoutResource(R.layout.shop_posters_present_card);
                    stub.setLayoutParams(params);
                    codes = productCode.getAdCodes("p", "sh", getApplicationContext());

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

    private void saveShop() {


        int sp_type = 0;
        if (shopSP.isChecked()) {
            sp_type = 0;
        }

        if (franchiseSP.isChecked()) {
            sp_type = 1;
        }

        if (dealerSP.isChecked()) {
            sp_type = 2;
        }


        if (shop.getLat() == null || shop.getLng() == null) {
            Toast.makeText(getApplicationContext(), "Please Set A Location .. !", Toast.LENGTH_LONG).show();
            return;
        }

        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        //shop.setSpName(sp_name);
        shop.setSpType(sp_type);
        //shop.setCityCode(city_code);
        shop.setRoute_desc(" ");
        shop.setLastCheckDate(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));

        String stand_types = "";

        if (hCB.isChecked()) stand_types = stand_types + hCB.getText().toString() + " , ";
        if (vCB.isChecked()) stand_types = stand_types + vCB.getText().toString() + " , ";
        if (hdCB.isChecked()) stand_types = stand_types +  hdCB.getText().toString() + " , ";
        if (vdCB.isChecked()) stand_types = stand_types +  vdCB.getText().toString() + " , ";

        Visit visit = new Visit();
        visit.setAgent(agent);
        visit.setShop(shop);
        visit.setVisit_date(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
        visit.setVisit_id();
        visit.setHolderQty(HolderNoTB.getText().toString());
        visit.setStandQty(StandNoTB.getText().toString());
        visit.setStandTypes(stand_types);

        List<VisitResult> visitResults = getVisitResults(visit.getVisit_id());

        System.out.println(visit.getVisit_id());


        DAO dao = new DAO(getApplicationContext());


        dao.insertShop(shop);
        dao.insertVisit(visit);

        for (final VisitResult visitResult : visitResults) {
            dao.insertVisitResult(visitResult);
        }


        Intent i = new Intent(getApplicationContext(), ShopOrderSavedActivity.class);
        i.putExtra("Agent", agent);
        startActivity(i);
    }

    private void init() {


        if (getIntent().getBooleanExtra("crashed", false) == true) {
            Toast.makeText(getApplicationContext(), "server down :/", Toast.LENGTH_LONG).show();
        }

        if ((Shop) getIntent().getSerializableExtra("Shop") != null) {

            shop = (Shop) getIntent().getSerializableExtra("Shop");
            agent = (Agent) getIntent().getSerializableExtra("Agent");
            agent.setA_id(1);
            agent.setR_id("1");

            route = (Route) getIntent().getSerializableExtra("Route");
        } else {
            shop = new Shop();
            agent = new Agent();
            agent.setA_id(1);
            agent.setR_id("1");

            route = new Route();
        }


        mProgressView = findViewById(R.id.pbar);
        mOperatorDetails = (LinearLayout) findViewById(R.id.operatorInfo);

        searchLayout = findViewById(R.id.searchLayout);
        salePointInfo = findViewById(R.id.salePointInfo);
        stepTwo_form = findViewById(R.id.stepTwo_form);


        spNameTB = (Spinner) findViewById(R.id.spNameTB);
        cityCodeTB = (Spinner) findViewById(R.id.cityCodeTB);
        spTypeRG = (RadioGroup) findViewById(R.id.spTypeRG);
        //
        shopSP = (RadioButton) findViewById(R.id.type1);
        franchiseSP = (RadioButton) findViewById(R.id.type2);
        dealerSP = (RadioButton) findViewById(R.id.type3);

        HolderNoTB = (EditText) findViewById(R.id.HolderNoTB);
        StandNoTB = (EditText) findViewById(R.id.StandNoTB);

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

        hCB = (CheckBox) findViewById(R.id.hCB);
        vCB = (CheckBox) findViewById(R.id.vCB);
        hdCB = (CheckBox) findViewById(R.id.hdCB);
        vdCB = (CheckBox) findViewById(R.id.vdCB);

        if (shop != null && route != null) {


            ((RadioButton) spTypeRG.getChildAt(shop.getSpType())).setChecked(true);


        } else {
            if (getIntent().getStringExtra("message") != null) {
                Toast.makeText(getApplicationContext(),
                        getIntent().getStringExtra("message"),
                        Toast.LENGTH_LONG).show();
            }

        }

        DAO dao = new DAO(getApplicationContext());

        List<String> cities = new ArrayList<String>();

        Cursor cr = dao.getCityCode();
        if (cr.moveToFirst()) {
            do {
                cities.add(cr.getString(0));
            } while (cr.moveToNext());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ShopsInfoActivity.this,
                android.R.layout.select_dialog_item, cities);

        cityCodeTB.setAdapter(dataAdapter);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.
                INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setSalePointInfo() {
        salePointInfo.setVisibility(View.VISIBLE);


        int index = shop.getSpType();

        RadioButton typeRB = (RadioButton) spTypeRG.getChildAt(index);
        typeRB.setChecked(true);

    }

    @Override
    public void onBackPressed() {
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

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
                Spinner adQTYSpinner = (Spinner) adDetails.findViewById(R.id.ad_qty_spinner);

                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY(adQTYSpinner.getSelectedItem().toString());
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
                Spinner adQTYSpinner = (Spinner) adDetails.findViewById(R.id.ad_qty_spinner);

                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY(adQTYSpinner.getSelectedItem().toString());
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


                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY("adequate");
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

                vr.setAd_Code(adCodesSpinner.getSelectedItem().toString());
                vr.setAd_QTY("adequate");
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
                Intent i = new Intent(getApplicationContext(), ShopOrderSavedActivity.class);
                startActivity(i);
                return true;

            case R.id.GoInfo:
                Intent in = new Intent(getApplicationContext(), ShopsInfoActivity.class);
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
}

