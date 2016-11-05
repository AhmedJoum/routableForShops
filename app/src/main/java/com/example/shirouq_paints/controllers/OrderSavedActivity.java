package com.example.shirouq_paints.controllers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shirouq_paints.models.Agent;
import com.example.shirouq_paints.models.SalePoint;


public class OrderSavedActivity extends AppCompatActivity {

    Button newOrder;

    FloatingActionButton logoutBtn;

    private String Lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lang = getIntent().getStringExtra("Lang");
        final Agent agent = (Agent) getIntent().getSerializableExtra("Agent");
        final SalePoint salePoint = new SalePoint();

        if(Lang.equals("English"))
            setContentView(R.layout.activity_order_saved_note);
        else
            setContentView(R.layout.activity_order_saved_note_ar);

        newOrder = (Button) findViewById(R.id.newOrder);
        logoutBtn = (FloatingActionButton) findViewById(R.id.LogoutBtn);


        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CustomerInfoActivity.class);
                i.putExtra("SalePoint", salePoint);
                i.putExtra("Lang", "Arabic");
                i.putExtra("Agent", agent);
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
