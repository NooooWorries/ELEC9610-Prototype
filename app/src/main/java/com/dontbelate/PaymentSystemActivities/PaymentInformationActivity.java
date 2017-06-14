package com.dontbelate.PaymentSystemActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dontbelate.Class.ChatMessage;
import com.dontbelate.Class.Couple;
import com.dontbelate.Class.Payment;
import com.dontbelate.Class.ServiceContract;
import com.dontbelate.MainActivity;
import com.dontbelate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PaymentInformationActivity extends AppCompatActivity {
    private EditText mCardNumber;
    private EditText mHolder;
    private EditText mExpiry;
    private EditText mCvv;
    private String providerId;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCardNumber = (EditText) findViewById(R.id.input_CardNumber);
        mHolder = (EditText) findViewById(R.id.input_HolderName);
        mExpiry = (EditText) findViewById(R.id.input_CardExpiry);
        mCvv = (EditText) findViewById(R.id.input_cvv);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            providerId = extras.getString("provider");
            price = extras.getString("price");
        }
    }

    public void pay(View view) {
        // Our program will NOT store user's card information
        // Step one, finish payment and generate payment record
        Payment payment = new Payment();
        payment.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final long timeStamp = System.currentTimeMillis() / 1000;
        payment.setPaymentId(Long.toString(timeStamp));
        payment.setTimePaid(Calendar.getInstance().getTime().toString());
        payment.setProviderId(providerId);
        payment.setPrice(price);
        DatabaseReference paymentReference = FirebaseDatabase.getInstance().getReference().child("payment").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(payment.getPaymentId());
        paymentReference.setValue(payment);

        // Step two, get coupleId;
        DatabaseReference userDetailReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("coupleId");
        userDetailReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String coupleId = dataSnapshot.getValue(String.class);
                // Step three, get couple party one and party two
                DatabaseReference coupleReference = FirebaseDatabase.getInstance().getReference().child("couple").child(coupleId);
                coupleReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Couple couple = new Couple();
                        couple = dataSnapshot.getValue(Couple.class);
                        String partyOne = couple.getPartyOneId();
                        String partyTwo = couple.getPartyTwoId();
                        // Step four, set service contract
                        ServiceContract partyOneContract = new ServiceContract();
                        partyOneContract.setUserId(partyOne);
                        partyOneContract.setProviderId(providerId);
                        ServiceContract partyTwoContract = new ServiceContract();
                        long timeStampTwo = System.currentTimeMillis() / 1000;
                        DatabaseReference partyOneContractReference = FirebaseDatabase.getInstance().getReference().child("contract").child(partyOne);
                        partyOneContractReference.setValue(partyOneContract);
                        partyTwoContract.setUserId(partyTwo);
                        partyTwoContract.setProviderId(providerId);
                        DatabaseReference partyTwpContractReference = FirebaseDatabase.getInstance().getReference().child("contract").child(partyTwo);
                        partyTwpContractReference.setValue(partyTwoContract);

                        // Register for chatting
                        DatabaseReference chatReference1 = FirebaseDatabase.getInstance().getReference().child("chat").child(providerId).child(partyOne).child(Long.toString(timeStampTwo));
                        DatabaseReference chatReference2 = FirebaseDatabase.getInstance().getReference().child("chat").child(providerId).child(partyTwo).child(Long.toString(timeStampTwo));
                        ChatMessage chatMessage1 = new ChatMessage();
                        chatMessage1.setMessageText("Thanks for choosing with us.");
                        chatMessage1.setMessageUser("System");
                        chatMessage1.setMessageTime(timeStampTwo);
                        ChatMessage chatMessage2 = new ChatMessage();
                        chatMessage2.setMessageText("Thanks for choosing with us.");
                        chatMessage2.setMessageUser("System");
                        chatMessage2.setMessageTime(timeStampTwo);

                        chatReference1.setValue(chatMessage1);
                        chatReference2.setValue(chatMessage2);
                        // Inform user that payment success and redirect to main page
                        Toast.makeText(PaymentInformationActivity.this, "Payment success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PaymentInformationActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(PaymentInformationActivity.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PaymentInformationActivity.this, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private boolean validateForm() {
        boolean valid = true;

        String cardNumber = mCardNumber.getText().toString();
        if (TextUtils.isEmpty(cardNumber)) {
            mCardNumber.setError("Required.");
            valid = false;
        } else {
            mCardNumber.setError(null);
        }

        String holder = mHolder.getText().toString();
        if (TextUtils.isEmpty(holder)) {
            mHolder.setError("Required.");
            valid = false;
        } else {
            mHolder.setError(null);
        }

        String expiry = mExpiry.getText().toString();
        if (TextUtils.isEmpty(expiry)) {
            mExpiry.setError("Required.");
            valid = false;
        } else {
            mExpiry.setError(null);
        }

        String cvv = mCvv.getText().toString();
        if (TextUtils.isEmpty(cvv)) {
            mCvv.setError("Required.");
            valid = false;
        } else {
            mCvv.setError(null);
        }

        return valid;
    }
}
