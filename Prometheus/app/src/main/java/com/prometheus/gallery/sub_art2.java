package com.prometheus.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;

public class sub_art2 extends MainActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.arts_subcategory2);
        ImageView img2=(ImageView)findViewById(R.id.img1);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(sub_art2.this, arts.class);
                startActivity(h);
                overridePendingTransition(0, 0);

            }
        });
        ScrollView v=findViewById(R.id.sv1);
        final ImageView img_ani=(ImageView)findViewById(R.id.img1);
        startBottomToTopAnimation(v);
        animationstart(img_ani);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigation_category);
    }
    private void startBottomToTopAnimation(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation_arts2));
    }
    private void animationstart(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.animation_artsmain2));
    }
}
