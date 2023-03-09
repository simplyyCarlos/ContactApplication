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
        ExtendedFloatingActionButton fab = findViewById(R.id.extended_fab);
        lView.setAdapter(adapter);
        restoreListViewInstance();


        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                contacts.remove(i);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailedContactActivity.class);
                intent.putExtra("selected",contacts.get(i));
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("IUT", "onClick:fab done " );
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
    private void restoreListViewInstance() {
        File directory = this.getFilesDir();
        File file = new File(directory, "saveFile");
        if(file.exists()){
            Log.e("IUT","File exist");
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = openFileInput("saveFile");
                in = new ObjectInputStream(fis);
                contacts.addAll((ArrayList<Contact>) in.readObject());

                Log.e("IUT",contacts.get(0).getUri());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveListViewInstance() {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = openFileOutput("saveFile", Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fos);
            out.writeObject(contacts);
            out.close();
            Log.e("IUT","contacts save");
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
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