package com.prometheus.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Full_Img_View extends AppCompatActivity {

    private ImageView img;
    private String imgurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullimgview);

        imgurl = getIntent().getStringExtra("Imgurl");

        img = findViewById(R.id.img);

        Picasso.get()
                .load(imgurl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img);
    }
}
