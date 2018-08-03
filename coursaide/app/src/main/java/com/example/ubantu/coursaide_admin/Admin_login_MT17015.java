package com.example.ubantu.coursaide_admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_login_MT17015 extends AppCompatActivity {
    private Button login_button,login_as_student_button,forgot_pass_button;
    private EditText mUser, mPass;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private FirebaseAuth mAuth;
    //private DatabaseReference users,admins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mUser = (EditText) findViewById(R.id.admin_username);
        mPass = (EditText) findViewById(R.id.admin_password);
        login_button = (Button) findViewById(R.id.login);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.remember_me);
        login_as_student_button= (Button)findViewById(R.id.login_as_student_button);
        forgot_pass_button = (Button) findViewById(R.id.forgot_password);
        //admins= FirebaseDatabase.getInstance().getReference("Admins");

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            mUser.setText(loginPreferences.getString("username", ""));
            mPass.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        login_as_student_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Admin_login_MT17015.this, MainActivity_MT17015.class);
                startActivity(I);
            }
        });

        //Code to check whether the user has already logged in or not.
        mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
        {
            //String email=mAuth.getCurrentUser().getEmail();
            //Query AdmExists = admins.orderByChild("name").equalTo(email);
            startActivity(new Intent(getApplicationContext(), Upload_TT_MT17015.class));
            finish();
        }
        mAuth=FirebaseAuth.getInstance();

        //Code to perform login.
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mUser.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "The email field cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(), "No password entered!", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (saveLoginCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", email);
                        loginPrefsEditor.putString("password", pass);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(getApplicationContext(), Upload_TT_MT17015.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Invalid Username or password!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        forgot_pass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=mUser.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Please enter a valid email address!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Password sent to "+email,Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Failed to reset password. Please try again!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
