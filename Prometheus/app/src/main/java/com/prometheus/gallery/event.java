package com.prometheus.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class event extends MainActivity {
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.events);
        FloatingActionButton fb=findViewById(R.id.floatbtn);
        fb.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigation_event);
    }
}
