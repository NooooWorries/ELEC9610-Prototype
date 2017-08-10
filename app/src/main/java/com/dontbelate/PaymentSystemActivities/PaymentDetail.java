package com.dontbelate.PaymentSystemActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dontbelate.R;

public class PaymentDetail extends AppCompatActivity {
    private String serviceTitle;
    private String servicePrice;
    private String serviceProvider;
    private TextView mTitle;
    private TextView mProvider;
    private TextView mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            serviceTitle = extras.getString("title");
            serviceProvider = extras.getString("provider");
            servicePrice = extras.getString("price");
        }
        mTitle = (TextView) findViewById(R.id.textTitle_payment);
        mProvider = (TextView) findViewById(R.id.textProvider_payment);
        mPrice = (TextView) findViewById(R.id.textPrice_payment);

        mTitle.setText(serviceTitle);
        mProvider.setText(serviceProvider);
        mPrice.setText(servicePrice);
    }

    public void pay(View view) {
        Intent intent = new Intent(PaymentDetail.this, PaymentInformationActivity.class);
        intent.putExtra("provider", serviceProvider);
        intent.putExtra("price", servicePrice);
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }


}
