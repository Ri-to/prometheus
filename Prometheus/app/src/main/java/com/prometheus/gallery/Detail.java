package com.prometheus.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
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
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.UUID;

public class Detail extends AppCompatActivity {

    private static final String TAG = "Showing immersive";
    private ImageView logo;
    //    private ImageView cart;
    private ImageView img;
    private ImageView love;
    private ImageView share;
    private TextView kalaungname;
    private TextView title;
    private TextView price;
    private TextView description;
    private TextView lovecount;
    private TextView viewcount;
    private String imgurl;

    private String postid;
    private String userid = "";

    private DatabaseReference mDatabase;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private SharePhoto linkContent;
    private Bitmap imagebitmap;

    private Target target;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);




        logo = findViewById(R.id.logo);
//        cart = findViewById(R.id.cart);
        img = findViewById(R.id.img);
        love = findViewById(R.id.love);
        share = findViewById(R.id.share);
        kalaungname = findViewById(R.id.kalaungname);
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        lovecount = findViewById(R.id.lovecount);
        viewcount = findViewById(R.id.viewcount);

        ((MyApplication) getApplication()).setGobacklogin("");
        ((MyApplication) getApplication()).setPostidforgoback("");

        postid = getIntent().getStringExtra("postid");
//        Log.e("Detail User",((MyApplication)getApplication()).getUserobj().toString());
        if (((MyApplication) getApplication()).getUserobj() != null) {
            userid = ((MyApplication) getApplication()).getUserobj().getId();
            CheckLovedb(postid, userid);
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

                    int key = 0;
                    key = Integer.parseInt(ds.getKey());

                    PostObj postObj = ds.getValue(PostObj.class);

                    imgurl = ds.child("photoPath").getValue() + "";

                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            if(bitmap!=null){
//                                Toast.makeText(Detail.this, "Have Image", Toast.LENGTH_SHORT).show();
                                imagebitmap = bitmap;
                                img.setImageBitmap(bitmap);
                            }
                            else{
                                Toast.makeText(Detail.this, "No Image", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };

                    Picasso.get()
                            .load(imgurl)
                            .placeholder(R.drawable.ic_more_horiz_24dp)
                            .error(R.drawable.ic_image_24dp)
                            .into(target);


                    DatabaseReference userref = FirebaseDatabase.getInstance().getReference();
                    Query userquery = userref.child("User").orderByChild("id").equalTo(ds.child("createdBy").getValue() + "");
                    userquery.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                Toast.makeText(Detail.this, "There is no user.", Toast.LENGTH_SHORT).show();
//                    DismissDialog();
                                return;
                            } else {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    kalaungname.setText(ds.child("fname").getValue() + " " + ds.child("lname").getValue());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    title.setText(ds.child("title").getValue() + "");
                    double finalprice = Double.parseDouble(ds.child("price").getValue() + "") + (Double.parseDouble(ds.child("price").getValue() + "") * 5) / 100;
                    price.setText(finalprice + " MMKS");
                    description.setText(ds.child("description").getValue() + "");
                    lovecount.setText(ds.child("loveCount").getValue() + "");
                    viewcount.setText(ds.child("viewCount").getValue() + "");

                    int viewCount = Integer.parseInt(ds.child("viewCount").getValue() + "") + 1;
                    postObj.setViewCount(viewCount);

                    mDatabase.child(key + "").setValue(postObj);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", databaseError.getMessage());
//                DismissDialog();
            }

        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Detail.this, Full_Img_View.class);
                i.putExtra("Imgurl", imgurl);
                startActivity(i);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((MyApplication) getApplication()).getUserobj() != null) {
                    callbackManager = CallbackManager.Factory.create();
                    shareDialog = new ShareDialog(Detail.this);

                    if(imagebitmap!=null){
                        SharePhoto photo = new SharePhoto.Builder()
                                .setBitmap(imagebitmap)
                                .build();


                        if (ShareDialog.canShow(SharePhotoContent.class)) {
                            SharePhotoContent content = new SharePhotoContent.Builder()
                                    .addPhoto(photo)
                                    .build();
                            shareDialog.show(Detail.this, content);
                        }
                    }
                } else {
                    Toast.makeText(Detail.this, "Please Login First", Toast.LENGTH_SHORT).show();
                    ((MyApplication) getApplication()).setGobacklogin("detail");
                    ((MyApplication) getApplication()).setPostidforgoback(postid);
                    Intent i = new Intent(Detail.this, Login.class);
//                    i.putExtra("goback","back");
                    startActivity(i);
                }
            }
        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((MyApplication) getApplication()).getUserobj() != null) {
                    if (love.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.nolove).getConstantState()) {
                        love.setImageResource(R.drawable.love);
                        LoveObj loveobj = new LoveObj();
                        loveobj.setId(UUID.randomUUID().toString());
                        loveobj.setPostid(postid);
                        loveobj.setUserid(userid);

                        InsertUpdateDB(loveobj, false);
                        lovecount.setText(Integer.toString(Integer.parseInt(lovecount.getText().toString()) + 1));

                    } else if (love.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.love).getConstantState()) {
//                    Picasso.get()
//                            .load(R.drawable.nolove)
//                            .error(R.mipmap.ic_launcher)
//                            .into(love);
                        love.setImageResource(R.drawable.nolove);
                        DeleteDB(postid, userid);
                        lovecount.setText(Integer.toString(Integer.parseInt(lovecount.getText().toString()) - 1));
                    }
                } else {
                    Toast.makeText(Detail.this, "Please Login First", Toast.LENGTH_SHORT).show();
                    ((MyApplication) getApplication()).setGobacklogin("detail");
                    ((MyApplication) getApplication()).setPostidforgoback(postid);
                    Intent i = new Intent(Detail.this, Login.class);
//                    i.putExtra("goback","back");
                    startActivity(i);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void fullScreen() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
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
                                            postobj.setLoveCount(postobj.getLoveCount() + 1);

                                            databaseReference.child("Post").child(key + "").setValue(postobj).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        postobj.setLoveCount(postobj.getLoveCount() + 1);

                                        databaseReference.child("Post").child(key + "").setValue(postobj).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    Log.e("DB_Commit", "Success!" + ds.toString());
                    Log.e("DB_Commit", "Success!" + ds.getKey());

                    if (userid.equals(lovelove.getUserid())) {
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
                                    postobj.setLoveCount(postobj.getLoveCount() - 1);

                                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                                    dbref.child("Post").child(key + "").setValue(postobj).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    Log.e("DB_Commit", "Success!" + ds.toString());
                    Log.e("DB_Commit", "Success!" + ds.getKey());

                    if (userid.equals(lovelove.getUserid())) {
                        love.setImageResource(R.drawable.love);
                        Log.e("setlove", "Success");
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

        if (((MyApplication) getApplication()).getComefromhomedetail().equals("home")) {
            Intent i = new Intent(this, home.class);
            startActivity(i);
        }

        super.onBackPressed();
    }

}
