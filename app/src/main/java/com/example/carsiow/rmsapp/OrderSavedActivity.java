package com.example.carsiow.rmsapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class OrderSavedActivity extends AppCompatActivity {

    Button newOrder;

    FloatingActionButton logoutBtn;

    private String Lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lang = getIntent().getStringExtra("Lang");

        if(Lang.equals("English"))
            setContentView(R.layout.activity_order_saved);
        else
            setContentView(R.layout.activity_order_saved_ar);

        newOrder = (Button) findViewById(R.id.newOrder);
        logoutBtn = (FloatingActionButton) findViewById(R.id.LogoutBtn);

        final CustOrder co = (CustOrder) getIntent().getSerializableExtra("CustomerOrder");

        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StepOneActivity.class);
                i.putExtra("CustomerOrder", co);
                i.putExtra("Lang", Lang);
                startActivity(i);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
