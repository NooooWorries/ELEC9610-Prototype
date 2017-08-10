package com.dontbelate.MatchingActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelate.ChatActivity;
import com.dontbelate.Class.Couple;
import com.dontbelate.Class.UserDetail;
import com.dontbelate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CoupleActivity extends AppCompatActivity {
    private Couple couple;
    private UserDetail oppositeDetail;
    private String oppositeId;
    private UserDetail myDetail;
    private android.support.v7.widget.Toolbar mToolbar;
    private TextView mTitle;
    private TextView mTime;
    private String phone;
    private String coupleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_couple);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Couple");
        mTitle = (TextView) findViewById(R.id.textTitle);
        mTime = (TextView) findViewById(R.id.textTime);

        // Get personal Information
        DatabaseReference myDetailReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myDetailReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myDetail = dataSnapshot.getValue(UserDetail.class);

                // Get couple information
                DatabaseReference coupleReference = FirebaseDatabase.getInstance().getReference().child("couple").child(myDetail.getCoupleId());
                coupleReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        couple = dataSnapshot.getValue(Couple.class);
                        mTime.setText("Start from " + couple.getStart());
                        coupleId = couple.getId();
                        // Get opposite id
                        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(couple.getPartyOneId()))
                            oppositeId = couple.getPartyTwoId();
                        else
                            oppositeId = couple.getPartyOneId();

                        // Get opposite detail
                        DatabaseReference oppositeReference = FirebaseDatabase.getInstance().getReference().child("users").child(oppositeId);
                        oppositeReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                oppositeDetail = dataSnapshot.getValue(UserDetail.class);
                                // Set information to text view
                                mTitle.setText(myDetail.name + "  ❤   " + oppositeDetail.name);
                                phone = oppositeDetail.phone;
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(CoupleActivity.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(CoupleActivity.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CoupleActivity.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);

    }

    public void sms(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null)));
    }

    public void chat(View view) {
        Intent intent = new Intent(CoupleActivity.this, ChatActivity.class);
        intent.putExtra("id", coupleId);
        intent.putExtra("type", "couple");
        startActivity(intent);
    }


}
