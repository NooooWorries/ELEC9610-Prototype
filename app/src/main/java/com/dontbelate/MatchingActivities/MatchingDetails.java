package com.dontbelate.MatchingActivities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dontbelate.Class.Message;
import com.dontbelate.Class.UserCharacteristics;
import com.dontbelate.Class.UserDetail;
import com.dontbelate.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class MatchingDetails extends AppCompatActivity {

    // Attributes: TextView and ImageView
    private TextView mName;
    private TextView mGender;
    private TextView mLocation;
    private TextView mUniversity;
    private TextView mBirthday;
    private TextView mWeight;
    private TextView mHeight;
    private TextView mMartialStatus;
    private TextView mEducationBackground;
    private TextView mPlaceOfBirth;
    private TextView mOccupation;
    private TextView mBelief;
    private TextView mChild;
    private TextView mSoliloquy;
    private TextView mHappyThings;
    private TextView mStuffCannotDoWithout;
    private TextView mIdealPartnerCharacteristics;
    private ImageView mImageView;

    // Attributes: Data from previous page
    private String name;
    private String id;
    private String disable;

    // Attributes: Database reference
    private DatabaseReference userDetailReference;
    private DatabaseReference userCharacteristicsReference;

    // Attributes: instances
    private UserCharacteristics userCharacteristics;
    private UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_details);
        disable = "false";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setBackground(getDrawable(R.drawable.splash));
        setSupportActionBar(toolbar);

        // Set toolbar title
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("key");
            getSupportActionBar().setTitle(name);
            id = extras.getString("id");
            disable = extras.getString("disable");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (disable.equals("true"))
            fab.setVisibility(View.INVISIBLE);
        else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ProgressDialog progressDialog = new ProgressDialog(MatchingDetails.this);
                    progressDialog.setTitle("Sending......");
                    progressDialog.show();
                    Message message = new Message();
                    message.setSender(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    message.setReceiver(id);
                    message.setSendTime(Calendar.getInstance().getTime().toString());
                    message.setTitle("You have received a matching request");
                    message.setContent("A user invite you to match up with him/her, please click view to get more information about this user. " +
                            "And select accept or reject.");
                    message.setResult("waiting for response");
                    message.setStatus("no response");
                    long timeStamp = System.currentTimeMillis() / 1000;
                    message.setId(Long.toString(timeStamp));
                    DatabaseReference messageReferenceReceiver = FirebaseDatabase.getInstance().getReference().child("message").child(id).child(Long.toString(timeStamp));
                    message.setType("send");
                    DatabaseReference messageReferenceSender = FirebaseDatabase.getInstance().getReference().child("message").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Long.toString(timeStamp));
                    messageReferenceSender.setValue(message);
                    message.setType("receive");
                    messageReferenceReceiver.setValue(message);
                    progressDialog.hide();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MatchingDetails.this);
                    builder.setMessage("Request had already been sent to the receiver, good luck.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(MatchingDetails.this, MatchPoolActivity.class));
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        // Find TextView ad ImageView by ID
        mName = (TextView) findViewById(R.id.textName);
        mGender = (TextView) findViewById(R.id.textGender);
        mLocation = (TextView) findViewById(R.id.textLocation);
        mUniversity = (TextView) findViewById(R.id.textUniversity);
        mBirthday = (TextView) findViewById(R.id.textBirthday);
        mWeight = (TextView) findViewById(R.id.textWeight);
        mHeight = (TextView) findViewById(R.id.textHeight);
        mMartialStatus = (TextView) findViewById(R.id.textMartialStatus);
        mEducationBackground = (TextView) findViewById(R.id.textEducationBackground);
        mPlaceOfBirth = (TextView) findViewById(R.id.textPlaceOfBirth);
        mOccupation = (TextView) findViewById(R.id.textOccupation);
        mBelief = (TextView) findViewById(R.id.textBelief);
        mChild = (TextView) findViewById(R.id.textChild);
        mSoliloquy = (TextView) findViewById(R.id.textSoliloquy);
        mHappyThings = (TextView) findViewById(R.id.textHappyThings);
        mStuffCannotDoWithout = (TextView) findViewById(R.id.textStuffCannotDoWithout);
        mIdealPartnerCharacteristics = (TextView) findViewById(R.id.textIdealPartnetCharacteristics);
        mImageView = (ImageView) findViewById(R.id.image_matching);

        // Get profile picture
        StorageReference imageReference = FirebaseStorage.getInstance().getReference().child("images/" + id + "/PersonalPicture.jpg");
        Glide.with(this /* context */).using(new FirebaseImageLoader()).load(imageReference).into(mImageView);

        // Get data from database
        userDetailReference = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        userCharacteristicsReference = FirebaseDatabase.getInstance().getReference().child("requests").child(id);

        userDetailReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDetail = dataSnapshot.getValue(UserDetail.class);
                mName.setText(userDetail.name);
                getSupportActionBar().setTitle(userDetail.name);
                mGender.setText(userDetail.gender);
                mLocation.setText(userDetail.suburb + " " + userDetail.city + ", " + userDetail.state);
                mUniversity.setText(userDetail.university);
                mBirthday.setText(userDetail.birthday);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MatchingDetails.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();
            }
        });
        userCharacteristicsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userCharacteristics = dataSnapshot.getValue(UserCharacteristics.class);
                mWeight.setText(userCharacteristics.getWeight());
                mHeight.setText(userCharacteristics.getHeight());
                mMartialStatus.setText(userCharacteristics.getMartialStatus());
                mEducationBackground.setText(userCharacteristics.getEducationBackground());
                mPlaceOfBirth.setText(userCharacteristics.getPlaceOfBirth());
                mOccupation.setText(userCharacteristics.getOccupation());
                mBelief.setText(userCharacteristics.getBelief());
                mChild.setText(userCharacteristics.getChild());
                mSoliloquy.setText(userCharacteristics.getSoliloquy());
                mHappyThings.setText(userCharacteristics.getHappyThings());
                mStuffCannotDoWithout.setText(userCharacteristics.getStuffCannotDoWithout());
                mIdealPartnerCharacteristics.setText(userCharacteristics.getIdealPartnerCharacteristics());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MatchingDetails.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
