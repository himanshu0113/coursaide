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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddCourse_MT17015 extends AppCompatActivity {

    private EditText acCnameEditText, acCcodeEditText, acCreditsEditText;
    private Spinner acBucketSpinner, acSplSpinner, acBranchSpinner;
    private Button acAddButton;
    private DatabaseReference databaseCourses;
    private DatabaseReference databaseBuckets;
    private DatabaseReference databaseSpecs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        acCnameEditText = (EditText) findViewById(R.id.acCnameEditText);
        acCcodeEditText = (EditText) findViewById(R.id.acCcodeEditText);
        acBucketSpinner = (Spinner) findViewById(R.id.acBucketSpinner);
        acSplSpinner = (Spinner) findViewById(R.id.acSplSpinner);
        acBranchSpinner = (Spinner) findViewById(R.id.acBranchSpinner);
        acAddButton = (Button) findViewById(R.id.acAddButton);
        acCreditsEditText=(EditText)findViewById(R.id.ac_creditsEditText);

        databaseCourses = FirebaseDatabase.getInstance().getReference("Courses_MT17015");
        databaseSpecs = FirebaseDatabase.getInstance().getReference("Specialisations_MT17015");
        databaseBuckets = FirebaseDatabase.getInstance().getReference("Bucket_MT17015");

        acAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseSpecs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> special = new ArrayList<String>();

                for (DataSnapshot specSnapshot: dataSnapshot.getChildren()) {
                    String specName = specSnapshot.child("specialisation").getValue(String.class);
                    special.add(specName);
                }

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> specAdapter = new ArrayAdapter<String>(AddCourse_MT17015.this, android.R.layout.simple_spinner_item, special);
                specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                acSplSpinner.setAdapter(specAdapter);

                acSplSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) { }
                });

                acBranchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) { }
                });


                acBucketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        databaseBuckets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> buckets = new ArrayList<String>();

                for (DataSnapshot specSnapshot: dataSnapshot.getChildren()) {
                    String bName = specSnapshot.child("bName").getValue(String.class);
                    buckets.add(bName);
                }

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> bAdapter = new ArrayAdapter<String>(AddCourse_MT17015.this, android.R.layout.simple_spinner_item, buckets);
                bAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                acBucketSpinner.setAdapter(bAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void addCourse() {
        String Cname = acCnameEditText.getText().toString().trim();
        String Ccode = acCcodeEditText.getText().toString().trim();
        String Cbuck = acBucketSpinner.getSelectedItem().toString();
        String Cspec = acSplSpinner.getSelectedItem().toString();
        String Cbranch = acBranchSpinner.getSelectedItem().toString();
        String Credits=acCreditsEditText.getText().toString();

        if(!TextUtils.isEmpty(Cname) && !TextUtils.isEmpty(Ccode)) {
            String id = databaseCourses.push().getKey();

            Courses_MT17015 course = new Courses_MT17015(Ccode, Cname, Cspec, Cbuck, Cbranch, Credits);
            databaseCourses.child(id).setValue(course);

            Toast.makeText(this,"Course saved!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Incomplete information. All fields are compulsory!", Toast.LENGTH_LONG).show();
        }
    }
}
