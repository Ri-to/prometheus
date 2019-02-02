package com.prometheus.gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prometheus.gallery.adapter.PostGridAdapter;
import com.prometheus.gallery.obj.PostObj;
import com.prometheus.gallery.obj.User;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private TextView name;
    private TextView phno;
    private TextView add;
    private TextView email;
    private TextView createddate;
    private TextView usertype;
    private Button btn_logout;
    private ImageView background_register;
    private DatabaseReference mDatabase;
    private RecyclerView postlist;

    private PostGridAdapter postGridAdapter;

    private ArrayList<PostObj> postlists = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        background_register = findViewById(R.id.background_register);

        background_register.setImageResource(R.drawable.bg_signin);

        User userobj = ((MyApplication) getApplication()).getUserobj();

        name = findViewById(R.id.name);
        phno = findViewById(R.id.phno);
        add = findViewById(R.id.add);
        email = findViewById(R.id.email);
        createddate = findViewById(R.id.createddate);
        usertype = findViewById(R.id.usertype);
        btn_logout = findViewById(R.id.btn_logout);

        postlist = findViewById(R.id.postlist);

        if (userobj != null) {
            name.setText(userobj.getFname() + " " + userobj.getLname());
            phno.setText(userobj.getPhno());
            add.setText(userobj.getAddress().equals("") ? "Live in somewhere." : userobj.getAddress());
            email.setText(userobj.getEmail());
            createddate.setText("Created On " + userobj.getCreatedDate());
            usertype.setText("User Type - " + userobj.getUserType());
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);

                ((MyApplication) getApplication()).setUserobj(null);
                Intent intent = new Intent(Profile.this, home.class);
                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Post").orderByChild("createdBy").equalTo(userobj.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Toast.makeText(Profile.this, "No Post to display !", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                       PostObj post = ds.getValue(PostObj.class);
                       postlists.add(post);
                    }

                    postGridAdapter = new PostGridAdapter(Profile.this, postlists);

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
