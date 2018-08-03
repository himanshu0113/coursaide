package com.example.ubantu.coursaide_admin;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class RemoveCourse_MT17015 extends AppCompatActivity {

    private TextView rcCcodeEditText, rcBucketEditText, rcBranchEditText, rcSplEditText,rcCreditEditText;
    private Spinner rcCnameSpinner;
    private Button rcDeleteButton;
    private DatabaseReference databaseCourses;
    private DataSnapshot courseSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_course);

        rcCnameSpinner = (Spinner) findViewById(R.id.rcCnameSpinner);
        rcCcodeEditText = (TextView) findViewById(R.id.rcCcodeEditText);
        rcBranchEditText = (TextView) findViewById(R.id.rcBranchEditText);
        rcBucketEditText = (TextView) findViewById(R.id.rcBucketEditText2);
        rcSplEditText = (TextView) findViewById(R.id.rcSplEditText2);
        rcDeleteButton = (Button) findViewById(R.id.rcDeleteButton);
        rcCreditEditText=(TextView)findViewById(R.id.rccreditEditText);

        databaseCourses = FirebaseDatabase.getInstance().getReference("Courses_MT17015");
        courseSnapshot = null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        rcDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });

        rcCnameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getValues();
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        databaseCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> courses = new ArrayList<String>();

                for (DataSnapshot cSnapshot: dataSnapshot.getChildren()) {
                    String cName = cSnapshot.child("cname").getValue(String.class);
                    courses.add(cName);
                }

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(RemoveCourse_MT17015.this, android.R.layout.simple_spinner_item, courses);
                cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rcCnameSpinner.setAdapter(cAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteCourse() {
        courseSnapshot.getRef().removeValue();
    }

    private void getValues() {
        String cname = rcCnameSpinner.getSelectedItem().toString();

        Toast.makeText(this, cname, Toast.LENGTH_SHORT).show();
        Query remspec = databaseCourses.orderByChild("cname").equalTo(cname);

        if(!TextUtils.isEmpty(cname)) {
            remspec.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null)
                        Toast.makeText(getApplicationContext(), "Invalid Course Name!", Toast.LENGTH_SHORT).show();
                    else {
                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                            Courses_MT17015 course2=Snapshot.getValue(Courses_MT17015.class);
                            rcCcodeEditText.setText(course2.getCcode());
                            rcSplEditText.setText(course2.getCspec());
                            rcBucketEditText.setText(course2.getCbuck());
                            rcBranchEditText.setText(course2.getCbranch());
                            rcCreditEditText.setText(course2.getCredits());

                            courseSnapshot = Snapshot;
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