package com.example.contactapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailedContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_contact);
        Contact c = (Contact) getIntent().getSerializableExtra("selected");

        ImageView iv = findViewById(R.id.imageViewDetails);
        TextView nt = findViewById(R.id.nameDetail);
        TextView num = findViewById(R.id.detailPhone);
        TextView adrr = findViewById(R.id.detailAdress);
        TextView zip = findViewById(R.id.detailZIP);
        if(c.isUri()){
            Picasso.get().load(Uri.parse(c.getUri())).into(iv);
        }else {
            switch (c.getGender()) {
                case "male":
                    iv.setImageResource(R.drawable.boy);
                    break;
                case "female":
                    iv.setImageResource(R.drawable.girl);
                    break;
                case "other":
                    iv.setImageResource(R.drawable.heli);
                    break;
                default:
                    break;
            }
        }
        nt.setText(c.getName() + " " + c.getSurname());

        num.setText(c.getPhoneNumber());
        adrr.setText(c.getAddress());
        zip.setText(c.getZipcode());

    }
}