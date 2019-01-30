package com.prometheus.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prometheus.gallery.obj.LoveObj;
import com.prometheus.gallery.obj.PostObj;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Detail extends AppCompatActivity {

    private ImageView logo;
    private ImageView cart;
    private ImageView img;
    private ImageView love;
    private ImageView share;
    private TextView title;
    private TextView price;
    private TextView description;

    private String postid;
    private String userid = "";

    private DatabaseReference mDatabase;

    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detail);

        logo = findViewById(R.id.logo);
        cart = findViewById(R.id.cart);
        img = findViewById(R.id.img);
        love = findViewById(R.id.love);
        share = findViewById(R.id.share);
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);

        ((MyApplication)getApplication()).setGobacklogin("");
        ((MyApplication)getApplication()).setPostidforgoback("");

        postid = getIntent().getStringExtra("postid");
//        Log.e("Detail User",((MyApplication)getApplication()).getUserobj().toString());
        if(((MyApplication)getApplication()).getUserobj()!=null){
            userid = ((MyApplication)getApplication()).getUserobj().getId();
            CheckLovedb(postid,userid);
        }
//        final String postid = "3df5422b-f5c8-429e-8ea0-78ff9ac8b610";
//        final String userid = "1466b7d0-e5c2-46d2-82f7-881c45ff8543";
//        Log.e("Postid",postid);

        mDatabase = FirebaseDatabase.getInstance().getReference("Post");
        Query query = mDatabase.orderByChild("id").equalTo(postid);//make connection with firebase
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    Toast.makeText(Detail.this, "This post is not available.", Toast.LENGTH_SHORT).show();
//                    DismissDialog();
                    return;
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

//                    PostObj postObj = ;

                    Picasso.get()
                            .load(ds.child("photoPath").getValue() + "")
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(img);

                    title.setText(ds.child("title").getValue() + "");
                    price.setText(ds.child("price").getValue() + " MMKS");
                    description.setText(ds.child("description").getValue() + "");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", databaseError.getMessage());
//                DismissDialog();
            }

        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((MyApplication)getApplication()).getUserobj()!=null){
                    if (love.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.nolove).getConstantState()) {
                        love.setImageResource(R.drawable.love);
                        LoveObj loveobj = new LoveObj();
                        loveobj.setId(UUID.randomUUID().toString());
                        loveobj.setPostid(postid);
                        loveobj.setUserid(userid);

                        InsertUpdateDB(loveobj,false);

                    } else if (love.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.love).getConstantState()) {
//                    Picasso.get()
//                            .load(R.drawable.nolove)
//                            .error(R.mipmap.ic_launcher)
//                            .into(love);
                        love.setImageResource(R.drawable.nolove);
                        DeleteDB(postid,userid);
                    }
                }
                else{
                    Toast.makeText(Detail.this, "Please Login First", Toast.LENGTH_SHORT).show();
                    ((MyApplication)getApplication()).setGobacklogin("detail");
                    ((MyApplication)getApplication()).setPostidforgoback(postid);
                    Intent i = new Intent(Detail.this,Login.class);
//                    i.putExtra("goback","back");
                    startActivity(i);
                }

            }
        });
    }


    //update love data
    public void InsertUpdateDB(final LoveObj loveobj, final boolean update) {

        final LoveObj love = loveobj;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Love");
        Query query = databaseReference.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        int key = 0;
//                    String obj_key = "";
//                    String new_key = "";

                        key = Integer.parseInt(ds.getKey());

                        if (!update) {
//                        obj_key = ds.child("id").getValue() + "";
//                        new_key = Util.AutoID(obj_key);
                            key = key + 1;
//                        townshipObj.setId(new_key);
                        }


                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("Love").child((key) + "").setValue(love).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e("DB_Commit", "Success!");

                                DatabaseReference postref = FirebaseDatabase.getInstance().getReference().child("Post");
                                Query query = postref.orderByChild("id").equalTo(postid);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                            int key = 0;
                                            key = Integer.parseInt(ds.getKey());

                                            PostObj postobj = ds.getValue(PostObj.class);
                                            postobj.setLoveCount(postobj.getLoveCount()+1);

                                            databaseReference.child("Post").child(key+"").setValue(postobj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.e("DB_Commit", "Success!");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("DB_Commit", "Fail!");
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("DB_Commit", "Fail!");
                            }
                        });


                    }
                } else {

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Love").child("1").setValue(love).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e("DB_Commit", "Success!");

                            DatabaseReference postref = FirebaseDatabase.getInstance().getReference().child("Post");
                            Query query = postref.orderByChild("id").equalTo(postid);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                        int key = 0;
                                        key = Integer.parseInt(ds.getKey());

                                        PostObj postobj = ds.getValue(PostObj.class);
                                        postobj.setLoveCount(postobj.getLoveCount()+1);

                                        databaseReference.child("Post").child(key+"").setValue(postobj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.e("DB_Commit", "Success!");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("DB_Commit", "Fail!");
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("DB_Commit", "Fail!");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    //delete love data
    public void DeleteDB(final String postid, final String userid) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Love");
        Query query = databaseReference.orderByChild("postid").equalTo(postid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    LoveObj lovelove = ds.getValue(LoveObj.class);
                    Log.e("DB_Commit", "Success!"+ds.toString());
                    Log.e("DB_Commit", "Success!"+ds.getKey());

                    if(userid.equals(lovelove.getUserid())){
                        ds.getRef().removeValue();

                        DatabaseReference postref = FirebaseDatabase.getInstance().getReference().child("Post");
                        Query query = postref.orderByChild("id").equalTo(postid);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    int key = 0;
                                    key = Integer.parseInt(ds.getKey());

                                    PostObj postobj = ds.getValue(PostObj.class);
                                    postobj.setLoveCount(postobj.getLoveCount()-1);

                                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                                    dbref.child("Post").child(key+"").setValue(postobj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.e("DB_Commit", "Success!");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("DB_Commit", "Fail!");
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    //check user love or not
    public void CheckLovedb(final String postid, final String userid) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Love");
        Query query = databaseReference.orderByChild("postid").equalTo(postid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    LoveObj lovelove = ds.getValue(LoveObj.class);
                    Log.e("DB_Commit", "Success!"+ds.toString());
                    Log.e("DB_Commit", "Success!"+ds.getKey());

                    if(userid.equals(lovelove.getUserid())){
                        love.setImageResource(R.drawable.love);
                        Log.e("setlove","Success");
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void onBackPressed() {
        // Do nothing
//        finish();

        if(((MyApplication)getApplication()).getComefromhomedetail().equals("home")){
            Intent i = new Intent(this,home.class);
            startActivity(i);
        }

        super.onBackPressed();
    }

}
