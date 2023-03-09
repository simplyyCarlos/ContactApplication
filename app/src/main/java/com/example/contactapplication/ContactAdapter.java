package com.example.contactapplication;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter implements Serializable {

    ArrayList<Contact> contacts;

    public ContactAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ConstraintLayout layoutItem;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if(view == null){
            layoutItem = (ConstraintLayout) inflater.inflate(R.layout.contact_layout,viewGroup,false);
        }   else {
            layoutItem = (ConstraintLayout) view;
        }
        Contact c = contacts.get(i);
        TextView name = (TextView) layoutItem.findViewById(R.id.contactName);
        TextView surname = (TextView) layoutItem.findViewById(R.id.contactSurname);
        TextView phoneNumber = (TextView) layoutItem.findViewById(R.id.contactPhone);
        ImageView imageView = (ImageView) layoutItem.findViewById(R.id.imageView);

        name.setText(c.getName());
        surname.setText(c.getSurname());
        phoneNumber.setText(c.getPhoneNumber());

        if(c.isUri()){
            Picasso.get()
                    .load(Uri.parse(c.getUri()))
                    .into(imageView);
            return layoutItem;
        }

            switch (c.getGender()) {
                case "male":
                    imageView.setImageResource(R.drawable.boy);
                    break;
                case "female":
                    imageView.setImageResource(R.drawable.girl);
                    break;
                case "other":
                    imageView.setImageResource(R.drawable.heli);
                    break;
                default:
                    break;
            }

        return layoutItem;

    }
}
