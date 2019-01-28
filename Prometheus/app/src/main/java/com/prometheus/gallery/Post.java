package com.prometheus.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class Post extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
    }
}
