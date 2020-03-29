package com.example.aviato.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.R;

public class SplashPage extends AppCompatActivity {
    Button sign_in, sign_up;
    AppDatabaseHelper appDatabaseHelper;
    Animation anim_slide_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        anim_slide_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        sign_in = findViewById(R.id.signin);
        sign_up = findViewById(R.id.signup);

        sign_in.setAnimation(anim_slide_down);
        sign_up.setAnimation(anim_slide_down);

        appDatabaseHelper = new AppDatabaseHelper(this);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(getApplicationContext(), SignInPage.class);
                startActivity(signIn);
                finish();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(signUp);
                finish();
            }
        });
    }
}

