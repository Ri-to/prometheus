package com.prometheus.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prometheus.gallery.adapter.SlidingImage_Adapter;
import com.prometheus.gallery.obj.PostObj;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Collections;

public class home extends MainActivity
{
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.event1,R.drawable.event2,R.drawable.event3,R.drawable.event4};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ArrayList<PostObj> TopLovedPosts = new ArrayList<>();
    private ArrayList<PostObj> TopViewedPosts = new ArrayList<>();
    private ImageView imageView1;

    private ImageView imgloved1;
    private ImageView imgloved2;
    private ImageView imgloved3;
    private ImageView imgloved4;
    private ImageView loadingImage;

    private ImageView imgviewed1;
    private ImageView imgviewed2;
    private ImageView imgviewed3;
    private ImageView imgviewed4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        LoadingScreen loadingscreen = new LoadingScreen(loadingImage);
//        loadingscreen.setLoadScreen();
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.home) ;
        init();

        imgloved1 = findViewById(R.id.imgloved1);
        imgloved2 = findViewById(R.id.imgloved2);
        imgloved3 = findViewById(R.id.imgloved3);
        imgloved4 = findViewById(R.id.imgloved4);

        imgviewed1 = findViewById(R.id.imgviewed1);
        imgviewed2 = findViewById(R.id.imgviewed2);
        imgviewed3 = findViewById(R.id.imgviewed3);
        imgviewed4 = findViewById(R.id.imgviewed4);

        fetchTopLovePost();
        fetchTopViewPost();

        ((MyApplication)getApplication()).setComefromhomedetail("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigation_home);
    }

    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(home.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(3 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    //get top love posts
    public void fetchTopLovePost() {


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        Query lovequery = databaseReference.orderByChild("loveCount").limitToLast(4);
        lovequery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {

                    PostObj post = ds.getValue(PostObj.class);
                    TopLovedPosts.add(post);
                }

                java.util.Collections.reverse(TopLovedPosts);
                for (int postcount=0;postcount<TopLovedPosts.size();postcount++){
                    if(postcount==0){
                        final PostObj post = TopLovedPosts.get(0);
                        Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgloved1);
                        Picasso.get().setLoggingEnabled(true);
                        imgloved1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                                ((MyApplication)getApplication()).setComefromhomedetail("home");
                                Intent idetail = new Intent(home.this,Detail.class);
                                idetail.putExtra("postid",post.getId());
                                startActivity(idetail);
                            }
                        });


                    }
                    if(postcount==1){
                        final PostObj post = TopLovedPosts.get(1);
                        Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgloved2);
                        imgloved2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                                ((MyApplication)getApplication()).setComefromhomedetail("home");
                                Intent idetail = new Intent(home.this,Detail.class);
                                idetail.putExtra("postid",post.getId());
                                startActivity(idetail);
                            }
                        });

                    }
                    if(postcount==2){
                        final PostObj post = TopLovedPosts.get(2);
                        Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgloved3);
                        imgloved3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                                ((MyApplication)getApplication()).setComefromhomedetail("home");
                                Intent idetail = new Intent(home.this,Detail.class);
                                idetail.putExtra("postid",post.getId());
                                startActivity(idetail);
                            }
                        });

                    }
                    if(postcount==3){
                        final PostObj post = TopLovedPosts.get(3);
                        Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgloved4);
                        imgloved4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                                ((MyApplication)getApplication()).setComefromhomedetail("home");
                                Intent idetail = new Intent(home.this,Detail.class);
                                idetail.putExtra("postid",post.getId());
                                startActivity(idetail);
                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", databaseError.getMessage());
            }

        });



    }

    public void fetchTopViewPost(){

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        Query viewquery = databaseReference.orderByChild("viewCount").limitToLast(4);
        viewquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {

                    PostObj post = ds.getValue(PostObj.class);
                    TopViewedPosts.add(post);
                }

                java.util.Collections.reverse(TopViewedPosts);
                for (int postcount=0;postcount<TopViewedPosts.size();postcount++){
                    if(postcount==0){
                        final PostObj post = TopViewedPosts.get(0);
                        Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgviewed1);
                        imgviewed1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                                ((MyApplication)getApplication()).setComefromhomedetail("home");
                                Intent idetail = new Intent(home.this,Detail.class);
                                idetail.putExtra("postid",post.getId());
                                startActivity(idetail);
                            }
                        });


                    }
                    if(postcount==1){
                        final PostObj post = TopViewedPosts.get(1);
                        Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgviewed2);
                        imgviewed2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                                ((MyApplication)getApplication()).setComefromhomedetail("home");
                                Intent idetail = new Intent(home.this,Detail.class);
                                idetail.putExtra("postid",post.getId());
                                startActivity(idetail);
                            }
                        });

                    }
                    if(postcount==2){
                        final PostObj post = TopViewedPosts.get(2);
                        Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgviewed3);
                        imgviewed3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                                ((MyApplication)getApplication()).setComefromhomedetail("home");
                                Intent idetail = new Intent(home.this,Detail.class);
                                idetail.putExtra("postid",post.getId());
                                startActivity(idetail);
                            }
                        });

                    }
                    if(postcount==3){
                        final PostObj post = TopViewedPosts.get(3);
                        Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgviewed4);
                        imgviewed4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(((MyApplication) getApplication()).buttonClick);
                                ((MyApplication)getApplication()).setComefromhomedetail("home");
                                Intent idetail = new Intent(home.this,Detail.class);
                                idetail.putExtra("postid",post.getId());
                                startActivity(idetail);
                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", databaseError.getMessage());
            }

        });
    }

    @Override
    public void onBackPressed() {
        // Do nothing
//        finish();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finishAffinity();
//                        finish();
//                        System.exit(0);
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        home.this.startActivity(a);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
