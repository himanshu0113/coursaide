package com.example.ubantu.coursaide_admin;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Sign_up_two_MT17015 extends AppCompatActivity {
    private Button save_button;
    private Spinner acSplSpinner, acBranchSpinner;
    private FloatingActionButton add_fab;
    private int mDynamicCourseAddCount;
    private DatabaseReference databaseSpecs,databaseCourse,databaseStudent;
    private EditText acCredit;
    private RadioGroup degree;
    private RadioButton rb1;
    private ArrayList<String> sub=new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        mDynamicCourseAddCount=0;
        setContentView(R.layout.activity_sign_up_two);
        save_button=(Button)findViewById(R.id.su_save);
        acSplSpinner = (Spinner) findViewById(R.id.su_splzspinner);
        acBranchSpinner = (Spinner) findViewById(R.id.su_branch_spinner);
        acCredit=(EditText)findViewById(R.id.su_credit_comp_editt);
        databaseSpecs = FirebaseDatabase.getInstance().getReference("Specialisations_MT17015");
        databaseCourse = FirebaseDatabase.getInstance().getReference("Courses_MT17015");
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student_MT17015");
        degree=(RadioGroup)findViewById(R.id.mtech_btech);
        rb1=(RadioButton) findViewById(R.id.su_mtech_r_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mUser=getIntent().getStringExtra("email");
                final String mPass=getIntent().getStringExtra("password");
                //Toast.makeText(getApplicationContext(),mUser,Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),mPass,Toast.LENGTH_SHORT).show();
                mAuth.createUserWithEmailAndPassword(mUser,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String Cspec = acSplSpinner.getSelectedItem().toString();
                            String Cbranch = acBranchSpinner.getSelectedItem().toString();
                            String Credits=acCredit.getText().toString().trim();
                            //Toast.makeText(getApplicationContext(),Cspec,Toast.LENGTH_SHORT).show();
                            int rid=degree.getCheckedRadioButtonId();
                            if(acCredit.equals("") && rid==-1)
                            {
                                Toast.makeText(getApplicationContext(), "Incomplete information. All fields are compulsory!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                String id = databaseStudent.push().getKey();
                                //Toast.makeText(getApplicationContext(),"Hello!",Toast.LENGTH_SHORT).show();
                                if(rid==rb1.getId())
                                {
                                    //Toast.makeText(getApplicationContext(),"Mtech!",Toast.LENGTH_SHORT).show();
                                    Student_MT17015 stu=new Student_MT17015(mUser,sub,Credits,"MTech",Cspec,Cbranch);
                                    databaseStudent.child(id).setValue(stu);
                                }
                                else
                                {
                                    //Toast.makeText(getApplicationContext(),"Btech!",Toast.LENGTH_SHORT).show();
                                    Student_MT17015 stu=new Student_MT17015(mUser,sub,Credits,"BTech",Cspec,Cbranch);
                                    databaseStudent.child(id).setValue(stu);
                                }
                                Toast.makeText(getApplicationContext(),"Registered!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity_MT17015.class));
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"The password might be too short!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        add_fab= (FloatingActionButton)findViewById(R.id.su_add_course_fab);
        final LinearLayout parentLinearLayout=findViewById(R.id.parent_Linear_Layout1);

        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            databaseCourse.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                    final View child_view = inflater.inflate(R.layout.field, null);
                    child_view.setId(mDynamicCourseAddCount);
                    Button del_Button = (Button) child_view.findViewById(R.id.dynamic_button);
                    final AutoCompleteTextView dynamic_cname = (AutoCompleteTextView) child_view.findViewById(R.id.dynamic_text);
                    del_Button.setId(mDynamicCourseAddCount);
                    del_Button.setText("Delete");
                    dynamic_cname.setId(mDynamicCourseAddCount);
                    final List<String> course = new ArrayList<String>();
                    for (DataSnapshot specSnapshot: dataSnapshot.getChildren()) {
                        String specName = specSnapshot.child("cname").getValue(String.class);
                        course.add(specName);
                    }
                    ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(Sign_up_two_MT17015.this, android.R.layout.simple_spinner_item,course);
                    dynamic_cname.setAdapter(courseAdapter);
                    dynamic_cname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String val=parent.getItemAtPosition(position).toString();
                            sub.add(val);
                        }
                    });
                    parentLinearLayout.addView(child_view);
                    mDynamicCourseAddCount += 1;
                    del_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String val=dynamic_cname.getText().toString();
                            Toast.makeText(getApplicationContext(),val,Toast.LENGTH_SHORT).show();
                            sub.remove(val);
                            parentLinearLayout.removeView(child_view);
                            mDynamicCourseAddCount-=1;
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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

                ArrayAdapter<String> specAdapter = new ArrayAdapter<String>(Sign_up_two_MT17015.this, android.R.layout.simple_spinner_item, special);
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
