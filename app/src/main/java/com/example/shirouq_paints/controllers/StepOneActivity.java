package com.example.shirouq_paints.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shirouq_paints.models.Customer;

public class StepOneActivity extends AppCompatActivity {

    EditText custName;
    EditText custPhone;
    EditText custEmail;
    EditText custAddress;

    String nameError1, nameError2, phoneError1, phoneError2, emailError, TBsError;

    int x = 3;

    FloatingActionButton nextBtn, cancelBtn, logoutBtn;


    Customer co = new Customer();
    private String Lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Lang = getIntent().getStringExtra("Lang");

        if (Lang.equals("English")) {
            setContentView(R.layout.activity_step_one);
            nameError1 = "Name contains number..";
            nameError2 = "Name Required";
            phoneError1 = "Should be numbers only..";
            phoneError2 = "Invalid phone number..";
            emailError = "Invalid Email address..";
            TBsError = "Fill all required...";

        } else {
            setContentView(R.layout.activity_step_one_ar);
            nameError1 = "لا يمكن للاسم ان يحتوي ارقاماً!";
            nameError2 = "ادخل اسم العميل";
            phoneError1 = "رقام فقط";
            phoneError2 = "الرجاء ادخال عشر ارقام";
            emailError = "الريد الالكتروني غير صحيح";
            TBsError = "املأ جميع الخانات...";
        }

        custName = (EditText) findViewById(R.id.custName);
        custPhone = (EditText) findViewById(R.id.custPhone);
        custEmail = (EditText) findViewById(R.id.custEmail);
        custAddress = (EditText) findViewById(R.id.custAdress);

        nextBtn = (FloatingActionButton) findViewById(R.id.nextBtn);
        cancelBtn = (FloatingActionButton) findViewById(R.id.cancelBtn);
        logoutBtn = (FloatingActionButton) findViewById(R.id.LogoutBtn);

        //final Customer
        co =  (Customer) getIntent().getSerializableExtra("CustomerOrder");

        custName.setText(co.getCustName());
        custPhone.setText(co.getCustPhone());
        custEmail.setText(co.getCustEmail());
        custAddress.setText(co.getCustAddress());


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = custName.getText().toString();
                String Phone = custPhone.getText().toString();
                String Email = custEmail.getText().toString();
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

                if (!Email.matches(".+@.+\\.[a-z]+")) {
                    custEmail.setError(emailError);
                    cancel = true;
                    focusView = custEmail;
                }

                if (name.equals("") || Phone.equals("") || Email.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            TBsError,
                            Toast.LENGTH_LONG).show();
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {

                    co.setCustName(name);
                    co.setCustPhone(Phone);
                    co.setCustEmail(Email);
                    co.setCustAddress(Address);

                    Intent i = new Intent(getApplicationContext(), StepTowActivity.class);
                    i.putExtra("CustomerOrder", co);
                    i.putExtra("Lang", Lang);
                    startActivity(i);
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custName.setText("");
                custEmail.setText("");
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
    }

    @Override
    public void onBackPressed() {
    }

}
