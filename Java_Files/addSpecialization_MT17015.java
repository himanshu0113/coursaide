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

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class
addSpecialization_MT17015 extends AppCompatActivity {
    EditText us_cname_editT;
    Button add_splz_button,delete_splz_button;
    Spinner us_branch_spinner,us_branch_Del_spinner2;
    DatabaseReference Specs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_specialization);
        us_cname_editT = (EditText) findViewById(R.id.us_cname_editT);
        us_branch_Del_spinner2 = (Spinner) findViewById(R.id.us_branch_Del_spinner2);
        us_branch_spinner = (Spinner) findViewById(R.id.us_branch_spinner);
        Specs= FirebaseDatabase.getInstance().getReference("Specialisations_MT17015");
        add_splz_button= (Button) findViewById(R.id.add_splz_button);
        delete_splz_button= (Button) findViewById(R.id.delete_splz_button);
        add_splz_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                addSpecialisation();
            }

        });
        delete_splz_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                deleteSpecialisation();
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
                    String specName = specSnapshot.child("specialisation").getValue(String.class);
                    special.add(specName);
                }

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> specAdapter = new ArrayAdapter<String>(addSpecialization_MT17015.this, android.R.layout.simple_spinner_item, special);
                specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                us_branch_Del_spinner2.setAdapter(specAdapter);

                us_branch_Del_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) { }
                });


                us_branch_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    private void addSpecialisation()
    {
        String name=us_cname_editT.getText().toString().trim();
        String branch=us_branch_spinner.getSelectedItem().toString().trim();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(branch)) {
            // String id=dataCourses.push().getKey();
            //Courses_MT17015 course=new Courses_MT17015(id,name,courses);
            String id = Specs.push().getKey();
            Specialisations_MT17015 spe = new Specialisations_MT17015(name, branch);
            Specs.child(id).setValue(spe);
            Toast.makeText(this, "Specialisation Added!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please enter the complete information before proceeding!", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteSpecialisation()
    {
        String name=us_branch_Del_spinner2.getSelectedItem().toString().trim();
        Query remspec = Specs.orderByChild("specialisation").equalTo(name);
        if(!TextUtils.isEmpty(name)) {
            remspec.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null)
                        Toast.makeText(getApplicationContext(), "No such specialisation", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Specialisation Deleted!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please enter the complete information before proceeding!", Toast.LENGTH_SHORT).show();
        }
    }

}
