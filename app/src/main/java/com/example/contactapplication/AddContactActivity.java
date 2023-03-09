package com.example.contactapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

public class AddContactActivity extends AppCompatActivity {
    private  Uri mImageUri = null;
    private ImageView iv;

    private final String NO_URI_STRING = "none";
    private String gender="male";
    private boolean bool = false;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        iv = (ImageView) findViewById(R.id.addImageView);
        iv.setImageResource(R.drawable.boy); // default image boy so as the radio button

        EditText edName= (EditText) findViewById(R.id.addName);
        RadioButton male = (RadioButton) findViewById(R.id.male_radio_button);
        male.setChecked(true); // default checked radio button

        EditText edSurname= (EditText) findViewById(R.id.addSurname);
        EditText edAddress= (EditText) findViewById(R.id.addAddress);
        EditText zipCode= (EditText) findViewById(R.id.addZipCode);
        EditText phoneNumber= (EditText) findViewById(R.id.addPhoneNumber);
        // our floating button "Add Contact"
        ExtendedFloatingActionButton fab = (ExtendedFloatingActionButton) findViewById(R.id.extended_fab2);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male_radio_button:
                        iv.setImageResource(R.drawable.boy);
                        gender = "male";
                        break;
                    case R.id.female_radio_button:
                        iv.setImageResource(R.drawable.girl);
                        gender ="female";
                        break;
                    case R.id.other_radio_button:
                        iv.setImageResource(R.drawable.heli);
                        gender="other";
                        break;
                    default:
                        // do nothing if none of the radio buttons are selected
                        break;
                }
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getParent();
                Contact temp;
                if(mImageUri != null){
                     temp = new Contact(edName.getText().toString(),edSurname.getText().toString(),phoneNumber.getText().toString(),edAddress.getText().toString(), zipCode.getText().toString(),gender,mImageUri.toString(), bool);

                }else{
                     temp = new Contact(edName.getText().toString(),edSurname.getText().toString(),phoneNumber.getText().toString(),edAddress.getText().toString(), zipCode.getText().toString(),gender,NO_URI_STRING,bool);

                }
                Intent intent = new Intent();
                intent.putExtra("contact",temp);
                setResult(20,intent);
                finish();
            }
        });


    }

    @SuppressLint("IntentReset")
    private void openImagePicker(){
        @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == PICK_IMAGE_REQUEST) && (resultCode == RESULT_OK) && (data != null) && (data.getData() != null)){
            mImageUri = data.getData();
            bool = true;
            Picasso.get().load(mImageUri).into(iv);

        }

    }
}