package com.dontbelate;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dontbelate.Account.acoount_profilePicture;
import com.dontbelate.Class.Couple;
import com.dontbelate.Class.UserDetail;
import com.dontbelate.ConsultActivities.ChooseServiceActivity;
import com.dontbelate.MatchingActivities.CoupleActivity;
import com.dontbelate.MatchingActivities.MatchPoolActivity;
import com.dontbelate.MatchingActivities.MessageActivity;
import com.dontbelate.MatchingActivities.PostRequest;
import com.firebase.ui.auth.ui.User;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.animation.Animator;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView userDisplay;
    private TextView typeDisplay;

    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;
    private UserDetail userDetail;
    private ImageView profileDisplay;

    // Card view instance
    ImageView imageView;
    ImageButton imageButton;
    LinearLayout revealView, layoutButtons;
    Animation alphaAnimation;
    float pixelDensity;
    boolean flag = true;
    Button button1;
    Button button2;
    Button button3;

    private TextView mTitle;
    private TextView mContent;

    private DatabaseReference mCouple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        mCouple = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("coupleId");

        // Get user information
        mTitle = (TextView)findViewById(R.id.textTitle);
        mContent = (TextView)findViewById(R.id.textProviderName);
        mContent.setText("The right one, is waiting for you.");
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Set the content of user infomation card
                UserDetail userDetail = dataSnapshot.getValue(UserDetail.class);
                mTitle.setText("Welcome, " + userDetail.name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed retriving data, please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });



        // Retriving data from database (User information)
        // Info one: User email address
        // Info two: User type

        userDisplay = (TextView)headerView.findViewById(R.id.userDisplay);
        userDisplay.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        typeDisplay = (TextView)headerView.findViewById(R.id.typeDisplay);

        mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Data listener: show user type
        mUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDetail = dataSnapshot.getValue(UserDetail.class);
                typeDisplay.setText("Login as a " + userDetail.getUserType());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed retriving data, please try again.",
                        Toast.LENGTH_SHORT).show();

            }
        };
        mUserReference.addValueEventListener(mUserListener);

        // Add profile picture onclick listener

        profileDisplay = (ImageView)headerView.findViewById(R.id.profileDisplay);

        profileDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, acoount_profilePicture.class));
            }
        });

        // Load profile picture from database
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/ProfilePicture.jpg");
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(profileDisplay);

        // Find cardview
        pixelDensity = getResources().getDisplayMetrics().density;
        imageView = (ImageView) findViewById(R.id.imageView);
        imageButton = (ImageButton) findViewById(R.id.launchTwitterAnimation);
        revealView = (LinearLayout) findViewById(R.id.linearView);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);

        // Find menu card button
        button1 = (Button)findViewById(R.id.cardPost);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

        // Get couple id;
        DatabaseReference coupleIdReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("coupleId");
        coupleIdReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String coupleId = dataSnapshot.getValue(String.class);
                if (coupleId.equals("0"))
                {
                    button2.setVisibility(View.INVISIBLE);
                    button3.setVisibility(View.INVISIBLE);
                }
                else
                {
                    button1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed retriving data, please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void launchMenu (View view) {
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

    public void postClick(View view)
    {
        startActivity(new Intent(MainActivity.this, PostRequest.class));
    }

    public void viewClick(View view)
    {
        startActivity(new Intent(MainActivity.this, CoupleActivity.class));
    }

    public void consultClick(View view)
    {
        DatabaseReference providerReference = FirebaseDatabase.getInstance().getReference().child("contract").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("providerId");
        providerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (TextUtils.isEmpty(dataSnapshot.getValue(String.class))) {
                    startActivity(new Intent(MainActivity.this, ChooseServiceActivity.class));
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    intent.putExtra("type","consult");
                    intent.putExtra("provider", dataSnapshot.getValue(String.class));
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //creating fragment object

        if (id == R.id.nav_main) {

        } else if (id == R.id.nav_matching) {
            mCouple.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(String.class).equals("0"))
                        startActivity(new Intent(MainActivity.this, MatchPoolActivity.class));
                    else
                        Toast.makeText(MainActivity.this, "You had already matched with a person.",
                                Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else if (id == R.id.nav_couple) {
            mCouple.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(String.class).equals("0"))
                        Toast.makeText(MainActivity.this, "You do not have matched with a person.",
                                Toast.LENGTH_SHORT).show();
                    else
                        startActivity(new Intent(MainActivity.this, CoupleActivity.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else if (id == R.id.nav_message) {
            mCouple.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(String.class).equals("0"))
                        startActivity(new Intent(MainActivity.this, MessageActivity.class));
                    else
                        Toast.makeText(MainActivity.this, "You had already matched with a person.",
                                Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else if (id == R.id.nav_consult) {
            DatabaseReference providerReference = FirebaseDatabase.getInstance().getReference().child("contract").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("providerId");
            providerReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (TextUtils.isEmpty(dataSnapshot.getValue(String.class))) {
                        startActivity(new Intent(MainActivity.this, ChooseServiceActivity.class));
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.putExtra("type","consult");
                        intent.putExtra("provider", dataSnapshot.getValue(String.class));
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, SplashActivity.class));

        }

        // Load drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
