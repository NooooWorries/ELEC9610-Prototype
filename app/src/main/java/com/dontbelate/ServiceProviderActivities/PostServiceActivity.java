package com.dontbelate.ServiceProviderActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dontbelate.Class.ServiceDetail;
import com.dontbelate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class PostServiceActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mContent;
    private EditText mFeature;
    private EditText mPrice;
    private ServiceDetail service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_service);

        mTitle = (EditText) findViewById(R.id.input_title);
        mContent = (EditText) findViewById(R.id.input_description);
        mFeature = (EditText) findViewById(R.id.input_feature);
        mPrice = (EditText) findViewById(R.id.input_price);
        service = new ServiceDetail();

    }


    public void submit(View view) {
        if (!validateForm()) {
            return;
        }
        service.setConsultantId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        long timeStamp = System.currentTimeMillis() / 1000;
        service.setServiceId(Long.toString(timeStamp));
        service.setTop("false");
        service.setPostDate(Calendar.getInstance().getTime().toString());

        DatabaseReference serviceReference = FirebaseDatabase.getInstance().getReference().child("service").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        serviceReference.setValue(service);

        Toast.makeText(PostServiceActivity.this, "Service added",
                Toast.LENGTH_LONG).show();
        startActivity(new Intent(PostServiceActivity.this, ServiceProviderMainActivity.class));
    }

    private boolean validateForm() {
        boolean valid = true;

        String title = mTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            mTitle.setError("Required.");
            valid = false;
        } else {
            mTitle.setError(null);
            service.setTitle(title);
        }

        String content = mContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            mContent.setError("Required.");
            valid = false;
        } else {
            mContent.setError(null);
            service.setDescription(content);
        }

        String feature = mFeature.getText().toString();
        if (TextUtils.isEmpty(title)) {
            mFeature.setError("Required.");
            valid = false;
        } else {
            mFeature.setError(null);
            service.setFeatures(feature);
        }

        String price = mPrice.getText().toString();
        if (TextUtils.isEmpty(price)) {
            mPrice.setError("Required.");
            valid = false;
        } else {
            mPrice.setError(null);
            service.setPrice(price);
        }
        return valid;
    }
}