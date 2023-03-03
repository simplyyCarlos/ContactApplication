package com.example.contactapplication;

import static java.lang.System.in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ContactAdapter adapter = new ContactAdapter(contacts);
    private ListView lView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lView = findViewById(R.id.listViewContact);
        //restoreListViewInstance();
        ExtendedFloatingActionButton fab = findViewById(R.id.extended_fab);

        lView.setAdapter(adapter);

        // set up item long click listener to remove contacts from the list
        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                contacts.remove(i);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // set up item click listener to display detailed contact information
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailedContactActivity.class);
                intent.putExtra("selected",contacts.get(i));
                startActivity(intent);
            }
        });

        // set up click listener for the add contact button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent,20);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveListViewInstance();
    }

    // save the ArrayList of Contacts to a file using an ObjectOutputStream
    private void saveListViewInstance() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "listview_instance")));
            outputStream.writeObject(contacts);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read the ArrayList of Contacts from a file using an ObjectInputStream and populate the ListView
    private void restoreListViewInstance() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(getFilesDir(), "listview_instance")));
            contacts = (ArrayList<Contact>) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == 20) {
            if (data != null && data.hasExtra("contact")) {
                Contact tmp = (Contact) data.getSerializableExtra("contact");
                contacts.add(tmp);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
