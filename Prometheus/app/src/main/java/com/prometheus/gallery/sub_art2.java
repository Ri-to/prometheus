package com.prometheus.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.ImageView;

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
    }
    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigation_category);
    }
}
