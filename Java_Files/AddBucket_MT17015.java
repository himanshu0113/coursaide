package com.example.ubantu.coursaide_admin;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddBucket_MT17015 extends AppCompatActivity {
    private EditText ubCnameeditText;
    private Button ubAddButton, ubDeleteButton;
    private Spinner ubBranchSpinner, ubBucketSpinner;
    private DatabaseReference Specs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bucket);
        ubCnameeditText = (EditText) findViewById(R.id.ubCnameEditText);
        ubBranchSpinner = (Spinner) findViewById(R.id.ubBranchSpinner);
        ubBucketSpinner = (Spinner) findViewById(R.id.ubBucketSpinner);
        Specs= FirebaseDatabase.getInstance().getReference("Bucket_MT17015");
        ubAddButton = (Button) findViewById(R.id.ubAddButton);
        ubDeleteButton = (Button) findViewById(R.id.ubDeleteButton);
        ubAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                addBucket();
            }

        });
        ubDeleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                deleteBucket();
            }

        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Specs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> special = new ArrayList<String>();

                for (DataSnapshot specSnapshot: dataSnapshot.getChildren()) {
                    String specName = specSnapshot.child("bName").getValue(String.class);
                    special.add(specName);
                }

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> specAdapter = new ArrayAdapter<String>(AddBucket_MT17015.this, android.R.layout.simple_spinner_item, special);
                specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ubBucketSpinner.setAdapter(specAdapter);

                ubBucketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) { }
                });

                ubBranchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) { }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addBucket()
    {
        String name= ubCnameeditText.getText().toString().trim();
        String branch= ubBranchSpinner.getSelectedItem().toString().trim();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(branch)) {
            // String id=dataCourses.push().getKey();
            //Courses_MT17015 course=new Courses_MT17015(id,name,courses);
            String id = Specs.push().getKey();
            Bucket_MT17015 buck = new Bucket_MT17015(name, branch);
            Specs.child(id).setValue(buck);
            Toast.makeText(this, "Bucket_MT17015 Added!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please enter the complete information before proceeding!", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteBucket()
    {
        String name= ubBucketSpinner.getSelectedItem().toString().trim();
        Query remspec = Specs.orderByChild("bName").equalTo(name);
        if(!TextUtils.isEmpty(name)) {
            remspec.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null)
                        Toast.makeText(getApplicationContext(), "No such bucket", Toast.LENGTH_SHORT).show();
                    else {
                        for (DataSnapshot specSnapshot : dataSnapshot.getChildren()) {
                            specSnapshot.getRef().removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Toast.makeText(this, "Bucket_MT17015 Deleted!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please enter the complete information before proceeding!", Toast.LENGTH_SHORT).show();
        }
    }



}
