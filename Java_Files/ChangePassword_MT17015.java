package com.example.ubantu.coursaide_admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword_MT17015 extends AppCompatActivity {
    private Button mChangepass;
    private EditText oldPass,newPass,confirmPass;
    private FirebaseAuth mAuth;
    private DatabaseReference users,admins;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.update_menu){
            Intent I = new Intent(ChangePassword_MT17015.this, UpdateMenu_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.upload_menu ){
            Intent I = new Intent(ChangePassword_MT17015.this, Upload_TT_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.uploadc_menu){
            Intent I = new Intent(ChangePassword_MT17015.this, Upload_Courses_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        //Log-out functionality.
        if (id==R.id.logout_menu){
            FirebaseAuth.getInstance().signOut();
            Intent I = new Intent(ChangePassword_MT17015.this, Admin_login_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.new_admin_menu){
            Intent I = new Intent(ChangePassword_MT17015.this, AddAdmin_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.change_pass_menu){
            return true;
        }
        if (id==R.id.del_account){
            new AlertDialog.Builder(this)
                    .setTitle("Delete Account")
                    .setMessage("All information will be lost if you delete your account. Are you sure you want to delete your account?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            users.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() == null)
                                        Toast.makeText(getApplicationContext(), "Could not get data!", Toast.LENGTH_SHORT).show();
                                    else {
                                        Admin_Count_MT17015 adm=new Admin_Count_MT17015();
                                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                            adm=Snapshot.getValue(Admin_Count_MT17015.class);
                                        }
                                        long count=adm.getAdmcount();
                                        if((count-1)>0)
                                        {
                                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(getApplicationContext(),"Account deleted successfully!",Toast.LENGTH_SHORT).show();
                                                        FirebaseAuth.getInstance().signOut();
                                                        startActivity(new Intent(getApplicationContext(), Admin_login_MT17015.class));
                                                        finish();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getApplicationContext(),"Account could not be deleted!",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                                Snapshot.getRef().removeValue();
                                            }
                                            count--;
                                            adm.setAdmcount(count);
                                            String id = users.push().getKey();
                                            users.child(id).setValue(adm);
                                            String email=user.getEmail();
                                            Query remAdm = admins.orderByChild("name").equalTo(email);
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
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Account could not be deleted!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            return true;
        }
        return true;
    }
}
