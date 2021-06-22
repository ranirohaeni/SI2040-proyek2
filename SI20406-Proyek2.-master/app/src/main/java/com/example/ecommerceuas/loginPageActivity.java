package com.example.ecommerceuas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginPageActivity extends AppCompatActivity {
//    assign id dari object
    private EditText Email;
    private EditText Password;
    private Button Login;
    private LoginButton loginFb;
    private FirebaseAuth auth;
    private CallbackManager mCallbackManager;
    private static final String TAG = "FacebookAuthentication";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
//        panggil id
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.login);
        loginFb = findViewById(R.id.login_button);
        auth = FirebaseAuth.getInstance();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        loginFb.setReadPermissions("email","public_profile");
//        aksi ketika user mengklik tombol login
        loginFb.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                    Log.d(TAG, "facebook:onError", error);
            }
        });

//        jika user telah login sebelumnya, maka langsung saja ke main activity



//        listen to text that input from user
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = Email.getText().toString();
                String passwordText = Password.getText().toString();
                loginUser(emailText, passwordText);
            }


        });
    }
    private void loginUser(String emailText, String passwordText) {
        auth.signInWithEmailAndPassword(emailText, passwordText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(loginPageActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(loginPageActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        jika user berhasil login, maka tampilkan ui
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            updateUI();
        }

    }
//update UI arahkan ke aktivitas berikutnya
    private void updateUI(){
            Toast.makeText(loginPageActivity.this, "You're logged in", Toast.LENGTH_LONG).show();
            Intent accountIntent = new Intent(loginPageActivity.this, MainActivity.class);
            startActivity(accountIntent);
            finish();
    }
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Pass the activity result back to the Facebook SDK
    mCallbackManager.onActivityResult(requestCode, resultCode, data);
}

    // token handler is here
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(loginPageActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI();
                        }
                    }
                });

    }
    //back button
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
