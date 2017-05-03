


package com.yazan.roatableShops.controllers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yazan.roatableShops.dao.DAO;
import com.yazan.roatableShops.models.Agent;
import com.yazan.roatableShops.models.Shop;
import com.yazan.roatableShops.services.SaveShopsLocalService;
import com.yazan.roatableShops.services.UpdateShopService;
import com.yazan.roatableShops.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ShopOrderSavedActivity extends AppCompatActivity {
    // private String Lang;

    private String str, str2;

    Button newOrder, syncPullBtn;
    Button syncBtn;

    public View mProgressView;
    public View mSyncLayout;

    private String Lang;
    Shop shop = new Shop();
    Agent agent = new Agent();


    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Agent agent = new Agent();
        agent.setA_id(1);
        final Shop shop = new Shop();


        setContentView(R.layout.activity_shop_order_saved_note_ar);

        mProgressView = findViewById(R.id.StepTwo_progress);
        mSyncLayout = findViewById(R.id.syncLayout);


        syncPullBtn = (Button) findViewById(R.id.syncPullBtn);
        syncBtn = (Button) findViewById(R.id.syncBtn);


        syncPullBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(),
                            "!! -- Please check your internet connection -- !!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                showProgress(true);
                Intent i = new Intent(getApplicationContext(), SaveShopsLocalService.class);
                startService(i);
            }
        });

        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "!! -- Please check your internet connection -- !!", Toast.LENGTH_LONG).show();
                    return;
                }

                showProgress(true);
                Intent i = new Intent(getApplicationContext(),  UpdateShopService.class);
                startService(i);
            }
        });

    }

    //// TODO: 4/19/17 change the url to the designed web service 


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSyncLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mSyncLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSyncLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSyncLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
                Intent int_ = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(int_);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
