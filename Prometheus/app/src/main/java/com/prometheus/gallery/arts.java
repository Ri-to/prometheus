package com.prometheus.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.motion.MotionLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class arts extends MainActivity {
    private TextView textView;
    private ImageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.arts_cartegory);
        ImageView img=(ImageView)findViewById(R.id.img1);
        final ImageView img1=(ImageView)findViewById(R.id.img1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(arts.this, sub_art1.class);
                startActivity(h);
                overridePendingTransition(0, 0);

            }
        });
        ImageView img2=(ImageView)findViewById(R.id.img2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(arts.this, sub_art2.class);
                startActivity(h);
                overridePendingTransition(0, 0);

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigation_category);
    }
}
