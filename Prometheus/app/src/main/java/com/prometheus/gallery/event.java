package com.prometheus.gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

public class event extends MainActivity {
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.events);
        FloatingActionButton fb=findViewById(R.id.floatbtn);
        fb.setVisibility(View.INVISIBLE);
        CardView cv1=(CardView) findViewById(R.id.cardview1);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(event.this, event_details.class);
                startActivity(h);
                overridePendingTransition(0, 0);

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigation_event);
    }

}
