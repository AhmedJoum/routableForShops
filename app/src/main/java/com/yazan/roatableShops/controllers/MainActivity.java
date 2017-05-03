package com.yazan.roatableShops.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button ShopBtn, SpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShopBtn = (Button) findViewById(R.id.shopBtn);
        SpBtn = (Button) findViewById(R.id.spBtn);

        ShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShopsInfoActivity.class);
                startActivity(i);
            }
        });


        SpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SalePointInfoActivity.class);
                startActivity(i);
            }
        });

    }
}
