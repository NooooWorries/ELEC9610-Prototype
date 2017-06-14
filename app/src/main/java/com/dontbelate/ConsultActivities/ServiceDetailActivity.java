package com.dontbelate.ConsultActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelate.Class.ServiceDetail;
import com.dontbelate.Class.UserDetail;
import com.dontbelate.PaymentSystemActivities.PaymentDetail;
import com.dontbelate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceDetailActivity extends AppCompatActivity {
    private String title;
    private String sId;
    private UserDetail userDetail;
    private ServiceDetail serviceDetail;
    private TextView mTitle;
    private TextView mProvider;
    private TextView mLocation;
    private TextView mContent;
    private TextView mFeature;
    private TextView mPrice;
    private String serviceTitle;
    private String servicePrice;
    private String serviceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userDetail = new UserDetail();
        serviceDetail = new ServiceDetail();
        mTitle = (TextView) findViewById(R.id.textTitle_service);
        mProvider = (TextView) findViewById(R.id.textProvider);
        mLocation = (TextView) findViewById(R.id.textLocation);
        mContent = (TextView) findViewById(R.id.textContent);
        mFeature = (TextView) findViewById(R.id.textFeature);
        mPrice = (TextView) findViewById(R.id.textPrice);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceDetailActivity.this, PaymentDetail.class);
                intent.putExtra("title", serviceTitle);
                intent.putExtra("price", servicePrice);
                intent.putExtra("provider", serviceProvider);
                startActivity(intent);

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("key");
            getSupportActionBar().setTitle(title);
            sId = extras.getString("id");
        }

        DatabaseReference serviceReference = FirebaseDatabase.getInstance().getReference().child("service").child(sId);
        serviceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviceDetail = dataSnapshot.getValue(ServiceDetail.class);
                mTitle.setText(serviceDetail.getTitle());
                mContent.setText(serviceDetail.getDescription());
                mFeature.setText(serviceDetail.getFeatures());
                mPrice.setText(serviceDetail.getPrice());
                serviceTitle = serviceDetail.getTitle();
                servicePrice = serviceDetail.getPrice();
                serviceProvider = serviceDetail.getConsultantId();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ServiceDetailActivity.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference providerReference = FirebaseDatabase.getInstance().getReference().child("users").child(sId);
        providerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDetail = dataSnapshot.getValue(UserDetail.class);
                mProvider.setText(userDetail.name);
                mLocation.setText(userDetail.city + " ," + userDetail.state);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ServiceDetailActivity.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
