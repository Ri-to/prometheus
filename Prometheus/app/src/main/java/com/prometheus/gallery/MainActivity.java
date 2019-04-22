package com.prometheus.gallery;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatbtn;
    private SharedPreferences sharedPreferences;
    private String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.logo_pro);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);
//        floatbtn = findViewById(R.id.floatbtn);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_home);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        s=sharedPreferences.getString("sub","English").toString();
        Log.e("ggwps",s);
        if(s=="English"){
            navigation.getMenu().findItem(R.id.navigation_home).setTitle("Home");
            navigation.getMenu().findItem(R.id.navigation_category ).setTitle("Arts");
            navigation.getMenu().findItem(R.id.navigation_event).setTitle("Events");
            navigation.getMenu().findItem(R.id.navigation_setting).setTitle("Setting");
        }
        else{
            if(Integer.parseInt(s)==2){
                navigation.getMenu().findItem(R.id.navigation_home).setTitle("ပင်မစာမျက်နာ");
                navigation.getMenu().findItem(R.id.navigation_category).setTitle("လက်ရာများ");
                navigation.getMenu().findItem(R.id.navigation_event).setTitle("ပြပွဲများ");
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("အပြင်အဆင်");
            }
            else {
                navigation.getMenu().findItem(R.id.navigation_home).setTitle("Home");
                navigation.getMenu().findItem(R.id.navigation_category ).setTitle("Arts");
                navigation.getMenu().findItem(R.id.navigation_event).setTitle("Events");
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("Setting");
            }
        }


        final FloatingActionButton fab = findViewById(R.id.floatbtn);

        String userType = "NormalUser";

        if(((MyApplication)getApplication()).getUserobj()!=null){
            userType = ((MyApplication)getApplication()).getUserobj().getUserType();
            if(userType.equals("Artist")){
                fab.setImageResource(R.drawable.ic_plus_24);
            }
            else{
                fab.setImageResource(R.drawable.workwithus);
            }
        }
        else{
            fab.setImageResource(R.drawable.workwithus);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication)getApplication()).buttonClick);

                if(((MyApplication)getApplication()).getUserobj()==null){
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                }
                else{
                    if(((MyApplication)getApplication()).getUserobj().getUserType().equals("NormalUser")){
//                        Toast.makeText(MainActivity.this, "Nice", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, WorkWithUs.class);  //change
                        startActivity(i);
                    }
                    else if(((MyApplication)getApplication()).getUserobj().getUserType().equals("Artist")){
                        Intent i = new Intent(MainActivity.this, Post.class);
                        startActivity(i);
                    }
                }

//                Snackbar.make(view, "Work with us", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.action,menu);
        return super.onCreateOptionsMenu(menu);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent h = new Intent(MainActivity.this, home.class);
                    startActivity(h);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_category:
//                    mTextMessage.setText(R.string.title_category);
                    Intent ii = new Intent(MainActivity.this, arts.class);
                    startActivity(ii);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_event:
                    Intent iii = new Intent(MainActivity.this, event.class);
                    startActivity(iii);
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.navigation_setting:
                    Intent s = new Intent(MainActivity.this, Setting.class);
                    startActivity(s);
                    overridePendingTransition(0, 0);
                    return true;
            }
//            transaction.commit();
            return false;
        }
    };
    public View setContentLayout(int layoutID)
    {
        FrameLayout contentLayout = (FrameLayout) findViewById(R.id.flContainer);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(layoutID, contentLayout, true);
    }

    public void setSelected(int optionID)
    {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().findItem(optionID).setChecked(true);
        getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().putInt("selectedNav",optionID).commit();
    }

    public int getSelectedNav()
    {
        return getSharedPreferences(getPackageName(), MODE_PRIVATE).getInt("selectedNav", R.id.navigation_home);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
//            case R.id.action_search:
//                goToPage("search");
//                return true;
//            case R.id.action_cart:
//                goToPage("cart");
//
//                return true;
            case R.id.action_profile:
                goToPage("profile");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToPage(String pagename){
        if(pagename.equals("search")){

        }
        else if(pagename.equals("cart")){

        }
        else if(pagename.equals("profile")){
            if(((MyApplication)getApplication()).getUserobj()==null){
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
            else{
                Intent i = new Intent(MainActivity.this,Profile.class);
                startActivity(i);
            }
        }
    }



    @Override
    protected void onResume() {

        super.onResume();
        final FloatingActionButton fab = findViewById(R.id.floatbtn);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        s=sharedPreferences.getString("sub","English").toString();
        Log.e("ggwps",s);
        if(s=="English"){
            navigation.getMenu().findItem(R.id.navigation_home).setTitle("Home");
            navigation.getMenu().findItem(R.id.navigation_category ).setTitle("Arts");
            navigation.getMenu().findItem(R.id.navigation_event).setTitle("Events");
            navigation.getMenu().findItem(R.id.navigation_setting).setTitle("Setting");
        }
        else{
            if(Integer.parseInt(s)==2){
                navigation.getMenu().findItem(R.id.navigation_home).setTitle("ပင်မစာမျက်နာ");
                navigation.getMenu().findItem(R.id.navigation_category).setTitle("လက်ရာများ");
                navigation.getMenu().findItem(R.id.navigation_event).setTitle("ပြပွဲများ");
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("အပြင်အဆင်");
            }
            else {
                navigation.getMenu().findItem(R.id.navigation_home).setTitle("Home");
                navigation.getMenu().findItem(R.id.navigation_category ).setTitle("Arts");
                navigation.getMenu().findItem(R.id.navigation_event).setTitle("Events");
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("Setting");
            }
        }

        String userType = "NormalUser";
        if(((MyApplication)getApplication()).getUserobj()!=null){
            userType = ((MyApplication)getApplication()).getUserobj().getUserType();
            if(userType.equals("Artist")){
                fab.setImageResource(R.drawable.ic_plus_24);
            }
            else{
                fab.setImageResource(R.drawable.workwithus);
            }
        }
        else{
            fab.setImageResource(R.drawable.workwithus);
        }
    }

}
