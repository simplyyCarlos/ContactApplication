package com.example.contactapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailedContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_contact);
        Contact c = (Contact) getIntent().getSerializableExtra("selected");

        ImageView iv = findViewById(R.id.imageViewDetails);
        TextView nt = findViewById(R.id.nameDetail);
        TextView st = findViewById(R.id.surname_detail);
        TextView num = findViewById(R.id.detail_phone);
        TextView adrr = findViewById(R.id.adress_detail);
        TextView zip = findViewById(R.id.zip_code);

        iv.setImageResource(R.drawable.boy);
        nt.setText(c.getName());
        st.setText(c.getSurname());
        num.setText(c.getPhoneNumber());
        adrr.setText(c.getAddress());
        zip.setText(c.getZipcode());

    }
}