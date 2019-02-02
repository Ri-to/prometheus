package com.prometheus.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prometheus.gallery.adapter.PostGridAdapter;
import com.prometheus.gallery.obj.PostObj;

import java.util.ArrayList;

public class SubcatePostList extends AppCompatActivity {

    private PostGridAdapter postGridAdapter;
    private RecyclerView postlist;
    private String subCate;

    private ArrayList<PostObj> postObjs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcate_post_list);

        postlist = findViewById(R.id.postlist);

        subCate = getIntent().getStringExtra("SubCate");
//        Log.e("SubCate",subCate);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Post").orderByChild("category").equalTo(subCate);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Toast.makeText(SubcatePostList.this, "There is no related Posts", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        PostObj post = ds.getValue(PostObj.class);
//                        Log.e("post",post.toString());
                        postObjs.add(post);
                    }

                    postGridAdapter = new PostGridAdapter(SubcatePostList.this, postObjs);

                    //for grid view
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
                    postlist.setLayoutManager(mLayoutManager);
                    postlist.setItemAnimator(new DefaultItemAnimator());
                    postlist.setAdapter(postGridAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
