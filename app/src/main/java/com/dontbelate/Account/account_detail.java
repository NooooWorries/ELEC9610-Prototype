package com.dontbelate.Account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dontbelate.Class.UserDetail;
import com.dontbelate.MainActivity;
import com.dontbelate.R;
import com.dontbelate.ServiceProviderActivities.ServiceProviderMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class account_detail extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private DatabaseReference mDatabase;
    private UserDetail userDetail;
    private TextView mName;
    private TextView mStreet;
    private TextView mSuburb;
    private TextView mCity;
    private TextView mState;
    private TextView mPostcode;
    private TextView mUniversity;
    private TextView mPhone;
    private TextView mBirthday;
    private RadioGroup radioGroupGender;
    private RadioGroup radioGroupUserType;
    private RadioButton radioButtonGender;
    private RadioButton radioButtonUserType;
    private Button mSubmit;

    // Date picker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userDetail = new UserDetail();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mName = (TextView) findViewById(R.id.input_name);
        mStreet = (TextView) findViewById(R.id.input_street);
        mSuburb = (TextView) findViewById(R.id.input_suburb);
        mCity = (TextView) findViewById(R.id.input_city);
        mState = (TextView) findViewById(R.id.input_state);
        mPostcode = (TextView) findViewById(R.id.input_postcode);
        mUniversity = (TextView) findViewById(R.id.input_university);
        mPhone = (TextView) findViewById(R.id.input_phone);
        mBirthday = (TextView) findViewById(R.id.input_birthday);
        mSubmit = (Button) findViewById(R.id.btn_submit);

        // Create radio button listener
        radioGroupGender = (RadioGroup) findViewById(R.id.gender);
        radioGroupUserType = (RadioGroup) findViewById(R.id.userType);

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonGender = (RadioButton) findViewById(checkedId);
                userDetail.setGender(radioButtonGender.getText().toString());
            }
        });

        radioGroupUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonUserType = (RadioButton) findViewById(checkedId);
                userDetail.setUserType(radioButtonUserType.getText().toString());
            }
        });

        // Create submit button listener
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        // Create birthday popup date picker
        mBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.YEAR, 1995);
                mCalendar.set(Calendar.MONTH, 1);
                mCalendar.set(Calendar.DAY_OF_MONTH, 1);

                DatePickerDialog mDatePicker = new DatePickerDialog(account_detail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mBirthday.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, 1995, 1, 1);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });


    }

    private void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbtn_male:
                if (checked)
                    userDetail.setGender("male");
                break;
            case R.id.rbtnFemale:
                if (checked)
                    userDetail.setGender("female");
                break;
            case R.id.rbtnCustomer:
                if (checked)
                    userDetail.setUserType("Customer");
                break;
            case R.id.rbtnService:
                if (checked)
                    userDetail.setUserType("Service provider");
                break;
        }
    }

    private void submit() {
        Log.d(TAG, "submit");
        if (!validateForm()) {
            return;
        }
        if (userDetail.getGender().equals(null) || userDetail.getUserType().equals(null))
            return;

        // Save data
        userDetail.setIsPublished("no");
        userDetail.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userDetail.setCoupleId("0");
        userDetail.featured = "no";

        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userDetail);
        if (userDetail.getUserType().equals("Customer"))
            startActivity(new Intent(account_detail.this, MainActivity.class));
        else if (userDetail.getUserType().equals("Service Provider"))
            startActivity(new Intent(account_detail.this, ServiceProviderMainActivity.class));
    }


    private boolean validateForm() {
        boolean valid = true;

        String name = mName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mName.setError("Required.");
            valid = false;
        } else {
            mName.setError(null);
            userDetail.setName(name);
        }

        String street = mStreet.getText().toString();
        if (TextUtils.isEmpty(street)) {
            mStreet.setError("Required.");
            valid = false;
        } else {
            mStreet.setError(null);
            userDetail.setStreet(street);
        }

        String suburb = mSuburb.getText().toString();
        if (TextUtils.isEmpty(suburb)) {
            mSuburb.setError("Required.");
            valid = false;
        } else {
            mSuburb.setError(null);
            userDetail.setSuburb(suburb);
        }

        String city = mCity.getText().toString();
        if (TextUtils.isEmpty(city)) {
            mCity.setError("Required.");
            valid = false;
        } else {
            mCity.setError(null);
            userDetail.setCity(city);
        }

        String state = mState.getText().toString();
        if (TextUtils.isEmpty(suburb)) {
            mState.setError("Required.");
            valid = false;
        } else {
            mState.setError(null);
            userDetail.setState(state);
        }

        String postcode = mPostcode.getText().toString();
        if (TextUtils.isEmpty(postcode)) {
            mPostcode.setError("Required.");
            valid = false;
        } else {
            mPostcode.setError(null);
            userDetail.setPostcode(postcode);
        }

        String university = mUniversity.getText().toString();
        if (TextUtils.isEmpty(university)) {
            mUniversity.setError("Required.");
            valid = false;
        } else {
            mUniversity.setError(null);
            userDetail.setUniversity(university);
        }

        String phone = mPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mPhone.setError("Required.");
            valid = false;
        } else {
            mPhone.setError(null);
            userDetail.setPhone(phone);
        }

        String birthday = mBirthday.getText().toString();
        if (TextUtils.isEmpty(birthday)) {
            mBirthday.setError("Required.");
            valid = false;
        } else {
            mBirthday.setError(null);
            userDetail.setBirthday(birthday);
        }
        return valid;
    }
}
