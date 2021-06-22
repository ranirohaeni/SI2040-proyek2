package com.example.ecommerceuas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerPageActivity extends AppCompatActivity {
//    modifier data member
    private EditText email;
    private EditText password;
    private Button register;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
// mengambil id dari layout
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        register = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        event ketika tombol di klik user
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ambil text email dan password dari textfield
            String textEmail = email.getText().toString();
            String textPassword = password.getText().toString();
//            toast tampil ketika field kosong
                if(TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword)){
                    Toast.makeText(registerPageActivity.this, "Harap isi field kosong",Toast.LENGTH_SHORT).show();
                }else if(textPassword.length() < 6){
//                    jika password terlalu pendek maka munculkan toast
                    Toast.makeText(registerPageActivity.this, "Password terlalu pendek", Toast.LENGTH_SHORT).show();
                }else{
//                    jika lolos cross check maka masukkan ke halaman
                    registerUser(textEmail, textPassword);
                }
            }
        });
    }
    // hasil autentikasi
    private void registerUser(String textEmail, String textPassword) {
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(registerPageActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(registerPageActivity.this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(registerPageActivity.this,loginActivity.class));
                    finish();
                }else {
                    Toast.makeText(registerPageActivity.this, "Registrasi tidak berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
