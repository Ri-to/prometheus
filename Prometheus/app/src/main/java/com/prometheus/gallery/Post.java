package com.prometheus.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prometheus.gallery.obj.PostObj;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Post extends AppCompatActivity {

    private ImageView img;
    private Button getimgbtn;
    private Button post;
    private static int RESULT_LOAD_IMAGE = 1;
    private Spinner type_spinner;
    private Spinner category_spinner;
    private Spinner framesize_spinner;
    private RadioButton no;
    private RadioButton yes;
    private Uri filepath;
    private Uri filepathonline;

    private TextView title;
    private TextView description;
    private TextView price;

    FirebaseStorage storage;
    StorageReference storageReference;

//    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

//    MyApplication globalvariable = (MyApplication) getApplication();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        Picasso.get()
                .load(R.drawable.background_register)
                .resize(1440,2560 )
                .onlyScaleDown()
                .into((ImageView) findViewById(R.id.background_register));

        getimgbtn = findViewById(R.id.getimgbtn);
        img = findViewById(R.id.img);
        post = findViewById(R.id.post);
        type_spinner = findViewById(R.id.type_spinner);
        category_spinner = findViewById(R.id.category_spinner);
        framesize_spinner = findViewById(R.id.framesize_spinner);
        no = findViewById(R.id.no);
        yes = findViewById(R.id.yes);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);

//        price.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Choose Type");
        typelist.add("Paintings");
        typelist.add("Photography");

        final ArrayList<String> paintingslist = new ArrayList<>();
        paintingslist.add("Choose Category");
        paintingslist.add("Digital");
        paintingslist.add("Drawings");
        paintingslist.add("Mixed Media");
        paintingslist.add("Classical Paintings");
        paintingslist.add("Prints");
        paintingslist.add("Textiles");
        paintingslist.add("Watercolours");
        paintingslist.add("Nude");

        final ArrayList<String> photographylist = new ArrayList<>();
        photographylist.add("Choose Category");
        photographylist.add("Landscape");
        photographylist.add("Portrait");
        photographylist.add("Historical");
        photographylist.add("Religious");
        photographylist.add("Wildlife");
        photographylist.add("Photojournalist");
        photographylist.add("Nude");

        final ArrayList<String> framesizelist = new ArrayList<>();
        framesizelist.add("Choose Frame Size");
        framesizelist.add("8\" x 11\"");
        framesizelist.add("11\" x 14\"");
        framesizelist.add("16\" x 20\"");
        framesizelist.add("20\" x 24\"");
        framesizelist.add("24\" x 36\"");
        framesizelist.add("30\" x 40\"");
        framesizelist.add("Custom");

        ArrayList<String> nolist = new ArrayList<>();
        nolist.add("Choose Category");
        ArrayAdapter<String> categoryadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, nolist){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the Text color
                tv.setTextColor(Color.WHITE);

                return view;
            }
        };
        // Specify the layout to use when the list of choices appears
        categoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category_spinner.setAdapter(categoryadapter);
        category_spinner.setEnabled(false);


        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> typeadapter = ArrayAdapter.createFromResource(this,
//                R.array.type_spinner, android.R.layout.simple_spinner_item);

        ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, typelist){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the Text color
                tv.setTextColor(Color.WHITE);

                return view;
            }
        };
        // Specify the layout to use when the list of choices appears
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type_spinner.setAdapter(typeadapter);

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                String selectedType = parent.getItemAtPosition(pos).toString();


                if(selectedType.equals("Paintings")){
                    ArrayAdapter<String> categoryadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, paintingslist){
                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;

                            // Set the Text color
                            tv.setTextColor(Color.WHITE);

                            return view;
                        }
                    };
                    categoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category_spinner.setAdapter(categoryadapter);
                    category_spinner.setEnabled(true);
                    category_spinner.setClickable(true);
                }
                else if(selectedType.equals("Photography")){
                    ArrayAdapter<String> categoryadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, photographylist){
                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;

                            // Set the Text color
                            tv.setTextColor(Color.WHITE);

                            return view;
                        }
                    };
                    categoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category_spinner.setAdapter(categoryadapter);
                    category_spinner.setEnabled(true);
                    category_spinner.setClickable(true);
                }
                else{
                    category_spinner.setEnabled(false);
                    category_spinner.setClickable(false);
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });

        ArrayAdapter<String> framesizeadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, framesizelist){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the Text color
                tv.setTextColor(Color.WHITE);

                return view;
            }
        };
        // Specify the layout to use when the list of choices appears
        framesizeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        framesize_spinner.setAdapter(framesizeadapter);
        framesize_spinner.setEnabled(false);

        //radiobuttons event start
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radiobutton was pressed
                if (checked){
                    // Do your coding
                    framesize_spinner.setEnabled(false);
                }
                else{
                    // Do your coding
                }
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radiobutton was pressed
                if (checked){
                    // Do your coding
                    framesize_spinner.setEnabled(true);
                }
                else{
                    // Do your coding
                }
            }
        });
        //radiobuttons event end


        getimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(((MyApplication)getApplication()).buttonClick);
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(((MyApplication)getApplication()).buttonClick);

                if(title.getText().toString().trim().equals("")){
                    title.setError(title.getHint()+" is required.");
                    return;
                }
                if(description.getText().toString().trim().equals("")){
                    description.setError(description.getHint()+" is required.");
                    return;
                }
                if(price.getText().toString().trim().equals("")){
                    price.setError(price.getHint()+" is required.");
                    return;
                }

                if(type_spinner.getSelectedItem().toString().equals("Choose Type")){
                    Toast.makeText(Post.this, "Please Choose Type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(category_spinner.getSelectedItem().toString().equals("Choose Category")){
                    Toast.makeText(Post.this, "Please Choose Category", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(yes.isChecked() && framesize_spinner.getSelectedItem().toString().equals("Choose Frame Size")){
                    Toast.makeText(Post.this, "Please Choose Frame Size", Toast.LENGTH_SHORT).show();
                    return;
                }

                uploadImage();


            }
        });



    }

    private void uploadImage(){

        if(filepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Posting...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
                            Toast.makeText(Post.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
                            Toast.makeText(Post.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                        }
//                    });


            UploadTask uploadTask = ref.putFile(filepath);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
//                        Uri downloadUri = task.getResult();
                        filepathonline = task.getResult();
//                        Log.e("filepathonline",filepathonline+ "");

                        PostObj postobj = new PostObj();
                        postobj.setId(UUID.randomUUID().toString());
                        postobj.setCategory(category_spinner.getSelectedItem().toString());
                        postobj.setTitle(title.getText().toString());
                        postobj.setDescription(description.getText().toString());
                        postobj.setPrice(Double.parseDouble(price.getText().toString()));
                        postobj.setPhotoPath(filepathonline.toString());
                        postobj.setCreatedBy(((MyApplication)getApplication()).getId());

                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
                        f.setTimeZone(TimeZone.getTimeZone("UTC"));
                        f.getCalendar().add(Calendar.MINUTE,390);
                        String now = f.format(new Date(System.currentTimeMillis() + 3600000 * 6 + 1800000));

                        postobj.setCreatedDate(now);
                        postobj.setModifiedDate(now);

                        if(yes.isChecked() && framesize_spinner.getSelectedItem().toString() != "Choose Frame Size"){
                            postobj.setFramesize(framesize_spinner.getSelectedItem().toString());
                        }

                        InsertUpdateDB(postobj,false);

                        progressDialog.dismiss();

                        Intent intent = new Intent(Post.this, Detail.class);
                        intent.putExtra("postid", postobj.getId());
                        startActivity(intent);


                    } else {
                        // Handle failures
                        // ...
                        Toast.makeText(Post.this, "Fail to download image url", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });

        }
        else{
            Toast.makeText(Post.this, "Please Choose an Image", Toast.LENGTH_SHORT).show();
            return;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//        }
//
//
//    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                filepath = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(filepath);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                img.getLayoutParams().width = 700;
                img.getLayoutParams().height = 700;
                img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                img.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Post.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(Post.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    //update post data
    public void InsertUpdateDB(final PostObj postObj , final boolean update) {

        final  PostObj post = postObj;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        Query query = databaseReference.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        int key = 0;
//                    String obj_key = "";
//                    String new_key = "";

                        key = Integer.parseInt(ds.getKey());

                        if(!update){
//                        obj_key = ds.child("id").getValue() + "";
//                        new_key = Util.AutoID(obj_key);
                            key = key + 1;
//                        townshipObj.setId(new_key);
                        }




                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("Post").child((key) + "").setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                else{
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Post").child("1").setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
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
