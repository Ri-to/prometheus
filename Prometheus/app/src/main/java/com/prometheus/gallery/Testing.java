package com.prometheus.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Testing extends AppCompatActivity {

    private ImageView background_register;
//    private ImageView background_registerr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);
//
        background_register = findViewById(R.id.background_register);
        background_register.setImageResource(R.drawable.bg_signin);
//        background_registerr = findViewById(R.id.background_registerr);
//        background_registerr.setImageResource(R.drawable.background_register);
    }
}
