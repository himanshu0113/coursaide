package com.example.ubantu.coursaide_admin;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
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

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Update_TT_MT17015 extends AppCompatActivity {
    EditText ut_cname_editT2,ut_ccode_editT2,ut_credits_editT2;
    Button update_button,gvalues_button;
    Spinner splz_spinner,bucket_spinner;
    DatabaseReference Courses,Specialisations,Buckets;
    String tmp1,tmp2,tmp3,tmp4,tmp5;
    DataSnapshot courseSnapshot;
    private static final int REQUEST_PERMISSIONS = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__tt);
        ut_cname_editT2 = (EditText) findViewById(R.id.ut_cname_editT2);
        ut_ccode_editT2 = (EditText) findViewById(R.id.ut_ccode_editT2);
        ut_credits_editT2=(EditText)findViewById(R.id.rm_credit_edit_text);
        update_button = (Button) findViewById(R.id.update_button);
        gvalues_button = (Button) findViewById(R.id.gvalues_button);
        splz_spinner = (Spinner) findViewById(R.id.splz_spinner);
        bucket_spinner = (Spinner) findViewById(R.id.bucket_spinner);
        courseSnapshot = null;
        Courses = FirebaseDatabase.getInstance().getReference("Courses_MT17015");
        Specialisations = FirebaseDatabase.getInstance().getReference("Specialisations_MT17015");
        Buckets = FirebaseDatabase.getInstance().getReference("Bucket_MT17015");
        update_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateCourse();
            }

        });
        gvalues_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getValues();
            }

        });

//        if (ActivityCompat.checkSelfPermission(Update_TT_MT17015.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
//                    REQUEST_PERMISSIONS);
//        }

       /*     @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    String code = ut_cname_editT2.getText().toString().trim();
                    Query remspec = Courses_MT17015.orderByChild("Ccode").equalTo(code);
                    if (!TextUtils.isEmpty(code)) {
                        remspec.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() == null)
                                    Toast.makeText(getApplicationContext(), "Invalid Course Code!", Toast.LENGTH_SHORT).show();
                                else {
                                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                        Courses_MT17015 course = Snapshot.getChildren().iterator().next()
                                                .getValue(Courses_MT17015.class);
                                        Toast.makeText(Update_TT_MT17015.this, course.getCname() + "," + course.getCcode(), Toast.LENGTH_SHORT).show();
                                        ut_cname_editT2.setText(course.getCname());
                                        ut_ccode_editT2.setText(course.getCcode());
                                        splz_spinner.setSelection(getIndex(splz_spinner, course.getCspec()));
                                        bucket_spinner.setSelection(getIndex(bucket_spinner, course.getCbuck()));
                                        Snapshot.getRef().removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        Specialisations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> special = new ArrayList<String>();
                for (DataSnapshot specSnapshot: dataSnapshot.getChildren()) {
                    String specName = specSnapshot.child("specialisation").getValue(String.class);
                    special.add(specName);
                }

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> specAdapter = new ArrayAdapter<String>(Update_TT_MT17015.this, android.R.layout.simple_spinner_item, special);
                specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                splz_spinner.setAdapter(specAdapter);

                splz_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        Buckets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> bucke = new ArrayList<String>();
                for (DataSnapshot specSnapshot: dataSnapshot.getChildren()) {
                    String buckName = specSnapshot.child("bName").getValue(String.class);
                    bucke.add(buckName);
                }

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> specAdapter = new ArrayAdapter<String>(Update_TT_MT17015.this, android.R.layout.simple_spinner_item, bucke);
                specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bucket_spinner.setAdapter(specAdapter);
                bucket_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    //private method of your class
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void updateCourse()
    {
        String cname=ut_cname_editT2.getText().toString().trim();
        String ccode=ut_ccode_editT2.getText().toString().trim();
        String credits=ut_credits_editT2.getText().toString().trim();
        String cspec=splz_spinner.getSelectedItem().toString().trim();
        String cbuck=bucket_spinner.getSelectedItem().toString().trim();
        String cbranch = tmp5;
        if(!TextUtils.isEmpty(ccode) && !TextUtils.isEmpty(cname))
        {
                courseSnapshot.getRef().removeValue();
                String id=Courses.push().getKey();
                Courses_MT17015 course2=new Courses_MT17015(ccode,cname,cspec,cbuck, cbranch,credits);
                Courses.child(id).setValue(course2);
                //dataCourses.child(id).setValue(course);
                Toast.makeText(this, "Course Updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please enter a Valid Course Code or Course Name before proceeding!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getValues() {

        splz_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        bucket_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        String code = ut_ccode_editT2.getText().toString().trim();
        String cname = ut_cname_editT2.getText().toString().trim();

        //Toast.makeText(this, "CP1", Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(code)) {
            Query remspec = Courses.orderByChild("ccode").equalTo(code);
            //Toast.makeText(this, "CP2", Toast.LENGTH_SHORT).show();
            remspec.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null)
                        Toast.makeText(getApplicationContext(), "Invalid Course Code!", Toast.LENGTH_SHORT).show();
                    else {
                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                            Courses_MT17015 course2=Snapshot.getValue(Courses_MT17015.class);
                            //Toast.makeText(Update_TT_MT17015.this, course.getCname() + "," + course.getCcode(), Toast.LENGTH_SHORT).show();
                            ut_cname_editT2.setText(course2.getCname());
                            ut_ccode_editT2.setText(course2.getCcode());
                            ut_credits_editT2.setText(course2.getCredits());
                            splz_spinner.setSelection(getIndex(splz_spinner, course2.getCspec()));
                            bucket_spinner.setSelection(getIndex(bucket_spinner, course2.getCbuck()));
                            tmp5=course2.getCbranch();
                            courseSnapshot = Snapshot;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if (!TextUtils.isEmpty(cname)) {
            Query remspec = Courses.orderByChild("cname").equalTo(cname);
            //Toast.makeText(this, "CP2", Toast.LENGTH_SHORT).show();
            remspec.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null)
                        Toast.makeText(getApplicationContext(), "Invalid Course Name!", Toast.LENGTH_SHORT).show();
                    else {
                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                            Courses_MT17015 course2=Snapshot.getValue(Courses_MT17015.class);
                            //Toast.makeText(Update_TT_MT17015.this, course.getCname() + "," + course.getCcode(), Toast.LENGTH_SHORT).show();
                            ut_cname_editT2.setText(course2.getCname());
                            ut_ccode_editT2.setText(course2.getCcode());
                            ut_credits_editT2.setText(course2.getCredits());
                            splz_spinner.setSelection(getIndex(splz_spinner, course2.getCspec()));
                            bucket_spinner.setSelection(getIndex(bucket_spinner, course2.getCbuck()));
                            tmp5=course2.getCbranch();
                            courseSnapshot = Snapshot;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else Toast.makeText(this, "Please enter a Valid Course Code or Course Name to autofill other fields", Toast.LENGTH_LONG).show();
    }
}

