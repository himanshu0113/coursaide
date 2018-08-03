package com.example.ubantu.coursaide_admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class View_Profile_MT17015 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseStudent;
    private TextView mCredits, mDegree, mSpec, mBranch, mSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__profile);
        mCredits=(TextView)findViewById(R.id.vp_credits_comp_textv);
        mDegree=(TextView)findViewById(R.id.vp_program_textv);
        mSpec=(TextView)findViewById(R.id.vp_splz_textv);
        mBranch=(TextView)findViewById(R.id.vp_branch_textv);
        mSubjects=(TextView)findViewById(R.id.subjectTextView);
        mAuth= FirebaseAuth.getInstance();
        String email=mAuth.getCurrentUser().getEmail();
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student_MT17015");
        Query StuDis = databaseStudent.orderByChild("username").equalTo(email);
        StuDis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                {
                    Toast.makeText(getApplicationContext(), "Invalid Username and Password!!", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    //startActivity(new Intent(getApplicationContext(), MainActivity_MT17015.class));
                    finish();
                }
                else
                {
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren())
                    {
                        Student_MT17015 stu=Snapshot.getValue(Student_MT17015.class);
                        mCredits.setText(stu.getCredits());
                        mDegree.setText(stu.getDegree());
                        mBranch.setText(stu.getBranch());
                        mSpec.setText(stu.getSpecialization());
                        ArrayList<String> sub=stu.getSubjects();
                        String s="";
                        for(int i=0;i<sub.size();i++)
                        {
                            if(i+1==sub.size())
                                s=s+sub.get(i);
                            else
                                s=s+sub.get(i)+", ";
                        }
                        mSubjects.setText(s);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

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
        /*if (id == R.id.viewprof) {
            startActivity(new Intent(this,View_Profile_MT17015.class));
            finish();
            return true;
        }*/
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
        /*if(id==R.id.DelAcc){
            new AlertDialog.Builder(this)
                    .setTitle("Delete Account")
                    .setMessage("All information will be lost if you delete your account. Are you sure you want to delete your account?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                                //Toast.makeText(getApplicationContext(),user.getEmail(),Toast.LENGTH_SHORT).show();
                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(),"Account deleted successfully!",Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                            startActivity(new Intent(View_Profile_MT17015.this, MainActivity_MT17015.class));
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Account could not be deleted!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                String email1=user.getEmail();
                                Query remAdm = databaseStudent.orderByChild("username").equalTo(email1);
                                remAdm.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                            Snapshot.getRef().removeValue();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            return true;
        }*/


}
