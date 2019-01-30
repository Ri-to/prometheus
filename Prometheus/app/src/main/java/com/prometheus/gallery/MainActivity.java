package com.prometheus.gallery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prometheus.gallery.obj.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_home);


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
                case R.id.navigation_setting:
//                    mTextMessage.setText(R.string.title_setting);
//                    return true;
                case R.id.navigation_event:
//                    mTextMessage.setText(R.string.title_events);
//                    return true;
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
            case R.id.action_search:
                goToPage("search");
                return true;
            case R.id.action_cart:
                goToPage("cart");

                return true;
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

    //get top love posts
//    public void fetchTopLove() {
//
//        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Love");
//        Query query = databaseReference.orderByChild("email").equalTo(email);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if(!dataSnapshot.exists()){
//                    Toast.makeText(Login.this, "Email Incorrect!", Toast.LENGTH_SHORT).show();
//
//                    return;
//                }
//
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String dbpw = ds.child("pw").getValue()+"";
//
//                    if(pw.equals(dbpw)){
//                        User userobj = ds.getValue(User.class);
////                            Log.e("User",userobj.toString());
////                            Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();
//
////                            ((MyApplication)getApplication()).setLoginstate(true);
////                            ((MyApplication)getApplication()).setId(ds.child("id").getValue()+"");
////                            ((MyApplication)getApplication()).setUserType(ds.child("userType").getValue()+"");
//                        ((MyApplication)getApplication()).setUserobj(userobj);
//                        Log.e("User",((MyApplication)getApplication()).getUserobj().toString());
//
//
//                        Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();
//
//
//
//                        Intent intent = new Intent(Login.this, home.class);
//                        startActivity(intent);
//                    }
//                    else{
//
//                        Toast.makeText(Login.this, "Password Incorrect!", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("DatabaseError", databaseError.getMessage());
//            }
//
//        });
//    }

}
