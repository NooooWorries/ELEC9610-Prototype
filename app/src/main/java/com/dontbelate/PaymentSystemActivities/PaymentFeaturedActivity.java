package com.dontbelate.PaymentSystemActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dontbelate.R;
import com.dontbelate.ServiceProviderActivities.ServiceProviderMainActivity;

public class PaymentFeaturedActivity extends AppCompatActivity {

    private EditText mDays;
    private int money;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_featured);
        mDays = (EditText) findViewById(R.id.input_days);
        money = Integer.parseInt(mDays.getText().toString());

    }

    public void payNow(View view) {
        int amount = Integer.parseInt(mDays.getText().toString()) * 10;
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(PaymentFeaturedActivity.this, "You have successful paid and will be featured soon, thanks!",
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PaymentFeaturedActivity.this, ServiceProviderMainActivity.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        // Do nothing
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentFeaturedActivity.this);
        builder.setMessage("You need to pay A$ " + Integer.toString(amount) + ".00, Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}
