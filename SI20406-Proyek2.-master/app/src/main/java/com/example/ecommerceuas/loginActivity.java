package com.example.ecommerceuas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class loginActivity extends AppCompatActivity {
private Button Login;
private Button Register;
private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        assign id object
        Register = findViewById(R.id.btnRegister);
        Login = findViewById(R.id.btnLogin);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();


        if(user != null){
            finish();
            startActivity(new Intent(loginActivity.this, MainActivity.class));
        }
//        aksi ketika user klik login
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this ,loginPageActivity.class));
                finish();
            }
        });
//        aksi ketika user klik register
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, registerPageActivity.class ));
                finish();
            }
        });
    }

}
