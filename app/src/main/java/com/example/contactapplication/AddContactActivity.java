package com.example.contactapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        ImageView iv = (ImageView) findViewById(R.id.addImageView);
        EditText edName= (EditText) findViewById(R.id.addName);
        RadioButton male = (RadioButton) findViewById(R.id.male_radio_button);
        RadioButton fmale = (RadioButton) findViewById(R.id.female_radio_button);
        RadioButton  other =(RadioButton) findViewById(R.id.other_radio_button);


        EditText edSurname= (EditText) findViewById(R.id.addSurname);
        EditText edAddress= (EditText) findViewById(R.id.addAddress);
        EditText zipCode= (EditText) findViewById(R.id.addZipCode);
        EditText phoneNumber= (EditText) findViewById(R.id.addPhoneNumber);

        ExtendedFloatingActionButton fab = (ExtendedFloatingActionButton) findViewById(R.id.extended_fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getParent();
                String checked;

                Contact temp= new Contact(edName.getText().toString(),edSurname.getText().toString(),phoneNumber.getText().toString(),edAddress.getText().toString(),"",zipCode.getText().toString(),"male","");
                Intent intent = new Intent();
                intent.putExtra("contact",temp);
                setResult(20,intent);
                finish();
            }
        });


    }
}