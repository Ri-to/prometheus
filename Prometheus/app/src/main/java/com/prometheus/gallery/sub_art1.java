package com.prometheus.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class sub_art1 extends MainActivity {

    private CardView drawing;
    private CardView digital;
    private CardView mix;
    private CardView painting;
    private CardView textile;
    private CardView prints;
    private CardView watercolor;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.arts_subcategory1);
        ImageView img2=(ImageView)findViewById(R.id.img1);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(sub_art1.this, arts.class);
                startActivity(h);
                overridePendingTransition(0, 0);

            }
        });
        ScrollView v=findViewById(R.id.sv1);
        final ImageView img_ani=(ImageView)findViewById(R.id.img1);
        startBottomToTopAnimation(v);
        animationstart(img_ani);

        drawing = findViewById(R.id.drawing);
        digital = findViewById(R.id.digital);
        mix = findViewById(R.id.mix);
        painting = findViewById(R.id.painting);
        textile = findViewById(R.id.textile);
        prints = findViewById(R.id.prints);
        watercolor = findViewById(R.id.watercolor);

        drawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art1.this,SubcatePostList.class);
                i.putExtra("SubCate","Drawings");
                startActivity(i);
            }
        });

        digital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art1.this,SubcatePostList.class);
                i.putExtra("SubCate","Digital");
                startActivity(i);
            }
        });

        mix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art1.this,SubcatePostList.class);
                i.putExtra("SubCate","Mixed Media");
                startActivity(i);
            }
        });

        painting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art1.this,SubcatePostList.class);
                i.putExtra("SubCate","Classical Paintings");
                startActivity(i);
            }
        });

        textile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art1.this,SubcatePostList.class);
                i.putExtra("SubCate","Textiles");
                startActivity(i);
            }
        });

        prints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art1.this,SubcatePostList.class);
                i.putExtra("SubCate","Prints");
                startActivity(i);
            }
        });

        watercolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art1.this,SubcatePostList.class);
                i.putExtra("SubCate","Watercolours");
                startActivity(i);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigation_category);
    }
    private void startBottomToTopAnimation(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation_arts));
    }
    private void animationstart(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.animation_artsmain1));
    }
}
