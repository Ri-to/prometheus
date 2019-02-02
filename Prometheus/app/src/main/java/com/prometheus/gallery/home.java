package com.prometheus.gallery;

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
    private static final Integer[] IMAGES= {R.drawable.bg_signin,R.drawable.bg_signin,R.drawable.bg_signin,R.drawable.bg_signin};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ArrayList<PostObj> TopLovedPosts = new ArrayList<>();
    private ImageView imageView1;

    private ImageView imgloved1;
    private ImageView imgloved2;
    private ImageView imgloved3;
    private ImageView imgloved4;
    private ImageView loadingImage;

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

        fetchTopLovePost();

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
        Query query = databaseReference.orderByChild("loveCount").limitToFirst(4);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {

                    PostObj post = ds.getValue(PostObj.class);
                    TopLovedPosts.add(post);



                    i++;

                }

                java.util.Collections.reverse(TopLovedPosts);
                for (int postcount=0;postcount<TopLovedPosts.size();postcount++){
                    if(postcount==0){
                            final PostObj post = TopLovedPosts.get(0);
                            Picasso.get().load(post.getPhotoPath()).placeholder(R.drawable.ic_more_horiz_24dp).error(R.drawable.ic_image_24dp).into(imgloved1);
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


}
