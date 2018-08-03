package com.example.ubantu.coursaide_admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Change_password_stu_MT17015 extends AppCompatActivity {
    private Button mChangepass;
    private EditText oldPass,newPass,confirmPass;
    private FirebaseAuth mAuth;
    private DatabaseReference users,admins;

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
        setContentView(R.layout.activity_change_password);
        users= FirebaseDatabase.getInstance().getReference("countAdmins");
        mChangepass=(Button)findViewById(R.id.change_password_button);
        oldPass=(EditText)findViewById(R.id.cp_old_pass);
        newPass=(EditText)findViewById(R.id.cp_new_pass);
        confirmPass=(EditText)findViewById(R.id.cp_new_confirm);
        mAuth=FirebaseAuth.getInstance();
        admins= FirebaseDatabase.getInstance().getReference("Admins");
        mChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old = oldPass.getText().toString();
                String new1 = newPass.getText().toString();
                String new2 = confirmPass.getText().toString();
                int flag=0;
                if (TextUtils.isEmpty(old) || TextUtils.isEmpty(new1) || TextUtils.isEmpty(new2)) {
                    Toast.makeText(getApplicationContext(), "All fields are compulsory!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(new1.equals(new2))
                    {
                        FirebaseUser user=mAuth.getCurrentUser();
                        AuthCredential cred= EmailAuthProvider.getCredential(user.getEmail(),old);
                        user.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    FirebaseUser user1=mAuth.getCurrentUser();
                                    String new3=newPass.getText().toString();
                                    user1.updatePassword(new3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(),"Password changed!",Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().signOut();
                                                startActivity(new Intent(getApplicationContext(), MainActivity_MT17015.class));
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(),"Password could not be changed!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Invalid Password!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"The passwords do not match!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



}
