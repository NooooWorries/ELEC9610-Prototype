package com.dontbelate.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dontbelate.Class.UserDetail;
import com.dontbelate.MainActivity;
import com.dontbelate.R;
import com.dontbelate.ServiceProviderActivities.ServiceProviderMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class account_login extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;
    private UserDetail userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);


        mAuth = FirebaseAuth.getInstance();

        // Views
        mEmailField = (EditText) findViewById(R.id.input_email1);
        mPasswordField = (EditText) findViewById(R.id.input_password1);

        // Button
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        // Initialize variables
        userType = new UserDetail();

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // Show progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(account_login.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in......");
        progressDialog.show();

        // Login
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get user type

                    DatabaseReference userTypeReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                    userTypeReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            userType = dataSnapshot.getValue(UserDetail.class);
                            // Redirect to main page based on user type

                            if (userType.getUserType().equals("Customer")) {
                                startActivity(new Intent(account_login.this, MainActivity.class));
                            } else if (userType.getUserType().equals("Service Provider")) {
                                startActivity(new Intent(account_login.this, ServiceProviderMainActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(account_login.this, "Authentication correct, but failed to fetch data, please try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });


                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(account_login.this, "Authentication failed, please try again",
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.hide();
            }
        });

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return valid;
    }
}
