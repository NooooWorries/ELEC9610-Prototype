package com.dontbelate.ServiceProviderActivities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelate.PaymentSystemActivities.PaymentFeaturedActivity;
import com.dontbelate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceProviderMainActivity extends AppCompatActivity {

    // Card view instance
    ImageView imageView;
    ImageButton imageButton;
    LinearLayout revealView, layoutButtons;
    Animation alphaAnimation;
    float pixelDensity;
    boolean flag = true;
    private android.support.v7.widget.Toolbar mToolbar;
    private TextView mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_main);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_serviceProviderMain);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Service provider main page");


        // Find cardview
        pixelDensity = getResources().getDisplayMetrics().density;
        imageView = (ImageView) findViewById(R.id.imageView);
        imageButton = (ImageButton) findViewById(R.id.launchTwitterAnimation);
        revealView = (LinearLayout) findViewById(R.id.linearView);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        mName = (TextView) findViewById(R.id.textProviderName);

        // Get service provider name
        DatabaseReference nameReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name");
        nameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mName.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ServiceProviderMainActivity.this, "Failed fetch data, please try again",
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    public void launchMenu(View view) {
        int x = imageView.getRight();
        int y = imageView.getBottom();
        x -= ((28 * pixelDensity) + (16 * pixelDensity));

        int hypotenuse = (int) Math.hypot(imageView.getWidth(), imageView.getHeight());

        if (flag) {

            imageButton.setBackgroundResource(R.drawable.rounded_cancel_button);
            imageButton.setImageResource(R.mipmap.image_cancel);

            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                    revealView.getLayoutParams();
            parameters.height = imageView.getHeight();
            revealView.setLayoutParams(parameters);

            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, 0, hypotenuse);
            anim.setDuration(700);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.VISIBLE);
                    layoutButtons.startAnimation(alphaAnimation);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            revealView.setVisibility(View.VISIBLE);
            anim.start();

            flag = false;
        } else {

            imageButton.setBackgroundResource(R.drawable.rounded_button);
            imageButton.setImageResource(android.R.drawable.ic_menu_slideshow);

            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, hypotenuse, 0);
            anim.setDuration(400);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    revealView.setVisibility(View.GONE);
                    layoutButtons.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            anim.start();
            flag = true;
        }
    }

    public void postClick(View view) {
        startActivity(new Intent(ServiceProviderMainActivity.this, PostServiceActivity.class));
    }

    public void contact(View view) {
        startActivity(new Intent(ServiceProviderMainActivity.this, ProvideServiceActivity.class));
    }

    public void wallet(View view) {
        startActivity(new Intent(ServiceProviderMainActivity.this, WalletActivity.class));
    }

    public void featured(View view) {
        startActivity(new Intent(ServiceProviderMainActivity.this, PaymentFeaturedActivity.class));
    }
}
