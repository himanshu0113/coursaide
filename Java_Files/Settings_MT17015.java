
package com.example.ubantu.coursaide_admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Settings_MT17015 extends AppCompatActivity {
    Button edit_profile_button, change_pswd_button, delete_acc_button;
    private DatabaseReference databaseStudent;

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
        /*if(id==R.id.settings){
            Intent I = new Intent(this, Settings_MT17015.class);
            startActivity(I);
            return true;
        }*/
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
        setContentView(R.layout.activity_settings);

        edit_profile_button= (Button)findViewById(R.id.set_edit_profile_button);
        change_pswd_button=(Button)findViewById(R.id.set_change_pswd_button);
        delete_acc_button=(Button)findViewById(R.id.button2);
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student_MT17015");

        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Settings_MT17015.this, Edit_Profile_MT17015.class);
                startActivity(I);
                finish();
            }
        });

        change_pswd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Settings_MT17015.this, Change_password_stu_MT17015.class);
                startActivity(I);
                finish();
            }
        });
        delete_acc_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Settings_MT17015.this);
                alertDialog.setTitle("Delete Account");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setMessage("All information will be lost if you delete your account. Are you sure you want to delete your account?");
                //alertDialog.setView(v);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                        //Toast.makeText(getApplicationContext(),user.getEmail(),Toast.LENGTH_SHORT).show();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"Account deleted successfully!",Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(Settings_MT17015.this, MainActivity_MT17015.class));
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
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog build = alertDialog.create();
                build.show();
            }

        });

    }
}
