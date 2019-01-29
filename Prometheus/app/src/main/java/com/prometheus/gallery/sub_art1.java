package com.prometheus.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

public class sub_art1 extends MainActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.arts_category2);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigation_category);
    }
}
