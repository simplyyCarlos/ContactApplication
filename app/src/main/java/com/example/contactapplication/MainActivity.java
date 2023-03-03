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
       // saveListViewInstance();

    }
    private void restoreListViewInstance() {
        Log.e("IUT", "instance restored");
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(getFilesDir(), "listview_instance")));
            contacts = (ArrayList<Contact>) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveListViewInstance() {
        Log.e("IUT", "instance saved");
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "listview_instance")));
            outputStream.writeObject(contacts);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
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