package com.example.ubantu.coursaide_admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_up_one_MT17015 extends AppCompatActivity {
    private Button next;
    private EditText mUser, mPass1, mPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_one);
        mUser = (EditText) findViewById(R.id.su_email);
        mPass1 = (EditText) findViewById(R.id.su_password);
        mPass2 = (EditText) findViewById(R.id.su_password_confirm);

        next= (Button)findViewById(R.id.su_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mUser.getText().toString().equals("") && mPass1.getText().toString().equals(mPass2.getText().toString()))
                {
                    Intent intent=new Intent(Sign_up_one_MT17015.this,Sign_up_two_MT17015.class);
                    intent.putExtra("email",mUser.getText().toString());
                    intent.putExtra("password",mPass1.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid information!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
