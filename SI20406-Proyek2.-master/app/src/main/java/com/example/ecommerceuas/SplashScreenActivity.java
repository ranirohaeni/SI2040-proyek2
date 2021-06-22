package com.example.ecommerceuas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    TextView tvSplash;
    ImageView Logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Hilangkan ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splashscreen);
//        hide action bar
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();
            }
        }, 4000);
Logo = (ImageView)findViewById(R.id.logo);
tvSplash = (TextView)findViewById(R.id.tvsplash);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.one);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.two );

Logo.startAnimation(animation);
tvSplash.startAnimation(animation1);
    }

}
