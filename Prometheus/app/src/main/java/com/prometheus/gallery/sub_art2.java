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
import android.widget.ScrollView;

public class sub_art2 extends MainActivity {

    private CardView historical;
    private CardView landscape;
    private CardView photojournalist;
    private CardView portrait;
    private CardView religious;
    private CardView wildlife;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.arts_subcategory2);
        ImageView img2=(ImageView)findViewById(R.id.img2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(sub_art2.this, arts.class);
                startActivity(h);
                overridePendingTransition(0, 0);

            }
        });
        ScrollView v=findViewById(R.id.sv1);
        final ImageView img_ani=(ImageView)findViewById(R.id.img2);
        startBottomToTopAnimation(v);
        animationstart(img_ani);

        historical = findViewById(R.id.historical);
        landscape = findViewById(R.id.landscape);
        photojournalist = findViewById(R.id.photojournalist);
        portrait = findViewById(R.id.portrait);
        religious = findViewById(R.id.religious);
        wildlife = findViewById(R.id.wildlife);

        historical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art2.this,SubcatePostList.class);
                i.putExtra("SubCate","Historical");
                startActivity(i);
            }
        });

        landscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art2.this,SubcatePostList.class);
                i.putExtra("SubCate","Landscape");
                startActivity(i);
            }
        });

        photojournalist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art2.this,SubcatePostList.class);
                i.putExtra("SubCate","Photojournalist");
                startActivity(i);
            }
        });

        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art2.this,SubcatePostList.class);
                i.putExtra("SubCate","Portrait");
                startActivity(i);
            }
        });

        religious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art2.this,SubcatePostList.class);
                i.putExtra("SubCate","Religious");
                startActivity(i);
            }
        });

        wildlife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                Intent i = new Intent(sub_art2.this,SubcatePostList.class);
                i.putExtra("SubCate","Wildlife");
                startActivity(i);
            }
        });

//        photographylist.add("Landscape");
//        photographylist.add("Portrait");
//        photographylist.add("Historical");
//        photographylist.add("Religious");
//        photographylist.add("Wildlife");
//        photographylist.add("Photojournalist");
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
