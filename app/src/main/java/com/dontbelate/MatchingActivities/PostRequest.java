package com.dontbelate.MatchingActivities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelate.Class.UserCharacteristics;
import com.dontbelate.MainActivity;
import com.dontbelate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class PostRequest extends AppCompatActivity {

    // Picture related variables
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Uri selectedImage;
    boolean isPictureSuccess = false;
    private android.support.v7.widget.Toolbar mToolbar;
    private DatabaseReference mDatabase;
    private UserCharacteristics userCharacteristics;
    private TextView mHeight;
    private TextView mWeight;
    private TextView mMartialStatus;
    private TextView mEducationBackground;
    private TextView mPlaceOfBirth;
    private TextView mOccupation;
    private TextView mBelief;
    private TextView mChild;
    private TextView mSoliloquy;
    private TextView mHappyThings;
    private TextView mImpression;
    private TextView mStuffCannotDoWithout;
    private TextView mIdealPartnerCharacteristics;
    private Button mUploadImage;
    private Button mPostRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_request);
        userCharacteristics = new UserCharacteristics();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_postRequest);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Post Dating Request");

        // Bind Textview
        mHeight = (TextView) findViewById(R.id.input_height);
        mWeight = (TextView) findViewById(R.id.input_weight);
        mMartialStatus = (TextView) findViewById(R.id.input_martialStatus);
        mEducationBackground = (TextView) findViewById(R.id.input_educationBackground);
        mPlaceOfBirth = (TextView) findViewById(R.id.input_placeOfBirth);
        mOccupation = (TextView) findViewById(R.id.input_occupation);
        mBelief = (TextView) findViewById(R.id.input_belief);
        mChild = (TextView) findViewById(R.id.input_child);
        mSoliloquy = (TextView) findViewById(R.id.input_soliloquy);
        mHappyThings = (TextView) findViewById(R.id.input_happyThings);
        mImpression = (TextView) findViewById(R.id.input_impression);
        mStuffCannotDoWithout = (TextView) findViewById(R.id.input_stuffCannotDoWithout);
        mIdealPartnerCharacteristics = (TextView) findViewById(R.id.input_idealPartnerCharacteristics);

        // Bind button
        mUploadImage = (Button) findViewById(R.id.btn_uploadImage);
        mPostRequest = (Button) findViewById(R.id.btn_postRequest);

        mPostRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }

    private boolean validateForm() {
        boolean valid = true;

        String height = mHeight.getText().toString();
        if (TextUtils.isEmpty(height)) {
            mHeight.setError("Required.");
            valid = false;
        } else {
            mHeight.setError(null);
            userCharacteristics.setHeight(height);
        }

        String weight = mWeight.getText().toString();
        if (TextUtils.isEmpty(weight)) {
            mWeight.setError("Required.");
            valid = false;
        } else {
            mWeight.setError(null);
            userCharacteristics.setWeight(weight);
        }

        String martialStatus = mMartialStatus.getText().toString();
        if (TextUtils.isEmpty(martialStatus)) {
            mMartialStatus.setError("Required.");
            valid = false;
        } else {
            mMartialStatus.setError(null);
            userCharacteristics.setMartialStatus(martialStatus);
        }

        String educationBackground = mEducationBackground.getText().toString();
        if (TextUtils.isEmpty(educationBackground)) {
            mEducationBackground.setError("Required.");
            valid = false;
        } else {
            mEducationBackground.setError(null);
            userCharacteristics.setEducationBackground(educationBackground);
        }

        String placeOfBirth = mPlaceOfBirth.getText().toString();
        if (TextUtils.isEmpty(placeOfBirth)) {
            mPlaceOfBirth.setError("Required.");
            valid = false;
        } else {
            mPlaceOfBirth.setError(null);
            userCharacteristics.setPlaceOfBirth(placeOfBirth);
        }

        String occupation = mOccupation.getText().toString();
        if (TextUtils.isEmpty(occupation)) {
            mOccupation.setError("Required.");
            valid = false;
        } else {
            mOccupation.setError(null);
            userCharacteristics.setOccupation(occupation);
        }

        String belief = mBelief.getText().toString();
        if (TextUtils.isEmpty(belief)) {
            mBelief.setError("Required.");
            valid = false;
        } else {
            mBelief.setError(null);
            userCharacteristics.setBelief(belief);
        }

        String child = mChild.getText().toString();
        if (TextUtils.isEmpty(child)) {
            mChild.setError("Required.");
            valid = false;
        } else {
            mChild.setError(null);
            userCharacteristics.setChild(child);
        }

        String soliloquy = mSoliloquy.getText().toString();
        if (TextUtils.isEmpty(soliloquy)) {
            mSoliloquy.setError("Required.");
            valid = false;
        } else {
            mSoliloquy.setError(null);
            userCharacteristics.setSoliloquy(soliloquy);
        }

        String happyThings = mHappyThings.getText().toString();
        if (TextUtils.isEmpty(happyThings)) {
            mHappyThings.setError("Required.");
            valid = false;
        } else {
            mHappyThings.setError(null);
            userCharacteristics.setHappyThings(happyThings);
        }

        String impression = mImpression.getText().toString();
        if (TextUtils.isEmpty(impression)) {
            mImpression.setError("Required.");
            valid = false;
        } else {
            mImpression.setError(null);
            userCharacteristics.setImpression(impression);
        }

        String stuffCannotDoWithout = mStuffCannotDoWithout.getText().toString();
        if (TextUtils.isEmpty(stuffCannotDoWithout)) {
            mStuffCannotDoWithout.setError("Required.");
            valid = false;
        } else {
            mStuffCannotDoWithout.setError(null);
            userCharacteristics.setStuffCannotDoWithout(stuffCannotDoWithout);
        }

        String idealPartnerCharacteristics = mIdealPartnerCharacteristics.getText().toString();
        if (TextUtils.isEmpty(idealPartnerCharacteristics)) {
            mIdealPartnerCharacteristics.setError("Required.");
            valid = false;
        } else {
            mIdealPartnerCharacteristics.setError(null);
            userCharacteristics.setIdealPartnerCharacteristics(idealPartnerCharacteristics);
        }
        return valid;
    }

    private void submit() {
        if (!validateForm()) {
            return;
        }

        // Upload picture
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Submitting......");
        progressDialog.show();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/PersonalPicture.jpg");

        // Get picture data from memory
        ImageView imageView = (ImageView) findViewById(R.id.image_tapUpload);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Upload
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.hide();
                Toast.makeText(PostRequest.this, "Failed uploading picture, please try again.",
                        Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Upload data
                mDatabase.child("requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userCharacteristics);

                // Update isPublished
                String taskId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference();
                updateRef.child("users").child(taskId).child("isPublished").setValue("yes");

                progressDialog.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(PostRequest.this);
                builder.setMessage("Submit success.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(PostRequest.this, MainActivity.class));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.image_tapUpload);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

}
