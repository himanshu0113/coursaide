package com.example.ubantu.coursaide_admin;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Edit_Profile_MT17015 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mCredits;
    private Spinner acSplSpinner, acBranchSpinner;
    private DatabaseReference databaseSpecs,databaseCourse,databaseStudent;
    private RadioButton rb1,rb2;
    private DataSnapshot studentSnapshot;
    private Button mEdit;
    private RadioGroup degree;
    ArrayList<String> sub=new ArrayList<>();
    ArrayList<String> sub1=new ArrayList<>();
    private int mDynamicCourseAddCount;
    private FloatingActionButton add_fab;
    private LinearLayout layout1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.viewprof) {
            startActivity(new Intent(this,View_Profile_MT17015.class));
            finish();
            return true;
        }
        if (id == R.id.sem_course) {
            startActivity(new Intent(this,SemesterCourses_MT17015.class));
            finish();
            return true;
        }
        if(id==R.id.ta_courses){
            Intent I = new Intent(this, TaCourses_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if(id==R.id.get_time_table){
            Intent I = new Intent(this, TimeTable_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if(id==R.id.settings){
            Intent I = new Intent(this, Settings_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if(id==R.id.Logout){
            FirebaseAuth.getInstance().signOut();
            Intent I = new Intent(this, MainActivity_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        mCredits=(EditText)findViewById(R.id.ep_credits_comp_editt);
        acSplSpinner = (Spinner) findViewById(R.id.ep_splz_spinner);
        acBranchSpinner = (Spinner) findViewById(R.id.ep_branch_spinner);
        rb1=(RadioButton) findViewById(R.id.ep_mtech_r_button);
        rb2=(RadioButton) findViewById(R.id.ep_btech_r_button);
        mEdit=(Button)findViewById(R.id.su_save);
        degree=(RadioGroup)findViewById(R.id.degreeMB);
        layout1=(LinearLayout)findViewById(R.id.parent_Linear_Layout3);
        databaseSpecs = FirebaseDatabase.getInstance().getReference("Specialisations_MT17015");
        databaseCourse = FirebaseDatabase.getInstance().getReference("Courses_MT17015");
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student_MT17015");
        mAuth= FirebaseAuth.getInstance();
        final String email=mAuth.getCurrentUser().getEmail();
        Query StuDis = databaseStudent.orderByChild("username").equalTo(email);
        StuDis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                {
                    Toast.makeText(getApplicationContext(), "Invalid user!", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                        Student_MT17015 stu = Snapshot.getValue(Student_MT17015.class);
                        mCredits.setText(stu.getCredits());
                        acSplSpinner.setSelection(getIndex(acSplSpinner, stu.getSpecialization()));
                        acBranchSpinner.setSelection(getIndex(acBranchSpinner, stu.getBranch()));
                        sub=stu.getSubjects();
                        sub1=stu.getSemsubjects();
                        mDynamicCourseAddCount=sub.size();
                        if(stu.getDegree().equals("MTech"))
                            rb1.setChecked(true);
                        else
                            rb2.setChecked(true);
                        studentSnapshot = Snapshot;
                        int id=-1;
                        final TextView[] textViews = new TextView[sub.size()];
                        final Button[] buttons = new Button[sub.size()];
                        for(int i = 0; i<sub.size(); i++)
                        {
                            final int pos=i;
                            final TextView singleTextView = new TextView(getApplicationContext());
                            final Button singlebutton = new Button(getApplicationContext());
                            singlebutton.setId(i+50);
                            singlebutton.setText("Delete");
                            singleTextView.setId(i);
                            singleTextView.setText(sub.get(i));

                            final View child_view = getLayoutInflater().inflate(R.layout.field, null);
                            final AutoCompleteTextView dynamic_cname = (AutoCompleteTextView) child_view.findViewById(R.id.dynamic_text);
                            Button del_Button = (Button) child_view.findViewById(R.id.dynamic_button);

                            dynamic_cname.setText(sub.get(i));

                            del_Button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String val=sub.get(pos);
                                    sub.remove(val);


                                    layout1.removeView(child_view);
                                }
                            });








                           // layout1.addView(singleTextView);
                            layout1.addView(child_view);
                            textViews[i] = singleTextView;
                            buttons[i]=singlebutton;
                            buttons[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String val=sub.get(pos);
                                    sub.remove(val);
                                    layout1.removeView(singleTextView);
                                    layout1.removeView(singlebutton);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cspec = acSplSpinner.getSelectedItem().toString();
                String Cbranch = acBranchSpinner.getSelectedItem().toString();
                String Credits=mCredits.getText().toString().trim();
                String deg="";
                int rid=degree.getCheckedRadioButtonId();
                if(rid==rb1.getId())
                {
                    deg="MTech";
                }
                else
                {
                    deg="BTech";
                }
                if(!Credits.equals(""))
                {
                    studentSnapshot.getRef().removeValue();
                    String id = databaseStudent.push().getKey();
                    int ind=sub1.indexOf("None");
                    if(ind>-1)
                        sub1.remove("None");
                    Student_MT17015 stu=new Student_MT17015(email,sub,Credits,deg,Cspec,Cbranch,sub1);
                    databaseStudent.child(id).setValue(stu);
                    Toast.makeText(getApplicationContext(),"Profile Updated!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please enter valid information!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_fab= (FloatingActionButton)findViewById(R.id.ep_add_course_button);
        final LinearLayout parentLinearLayout=findViewById(R.id.parent_Linear_Layout3);

        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseCourse.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                        final View child_view = inflater.inflate(R.layout.field, null);
                        child_view.setId(mDynamicCourseAddCount);
                        final AutoCompleteTextView dynamic_cname = (AutoCompleteTextView) child_view.findViewById(R.id.dynamic_text);
                        Button del_Button = (Button) child_view.findViewById(R.id.dynamic_button);
                        dynamic_cname.setId(mDynamicCourseAddCount);
                        del_Button.setId(mDynamicCourseAddCount);
                        //del_Button.setText("Delete");
                        final List<String> course = new ArrayList<String>();
                        for (DataSnapshot specSnapshot: dataSnapshot.getChildren()) {
                            String specName = specSnapshot.child("cname").getValue(String.class);
                            course.add(specName);
                        }
                        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(Edit_Profile_MT17015.this, android.R.layout.simple_spinner_item,course);
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

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> specAdapter = new ArrayAdapter<String>(Edit_Profile_MT17015.this, android.R.layout.simple_spinner_item, special);
                specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                acSplSpinner.setAdapter(specAdapter);

                acSplSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ((TextView) parentView.getChildAt(0)).setTextColor(Color.parseColor("#797b7f"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) { }
                });

                acBranchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ((TextView) parentView.getChildAt(0)).setTextColor(Color.parseColor("#797b7f"));
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

}
