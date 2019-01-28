package com.prometheus.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.prometheus.gallery.obj.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;
import java.util.Date;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class Register extends AppCompatActivity {

    private EditText fname;
    private EditText lname;
    private EditText phno;
    private EditText email;
    private EditText pw;
    private EditText conpw;
    private Button btn_register;
    private TextView btn_login;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

//        Picasso.with(getApplicationContext())
//                .load(R.drawable.background_register)
//                .transform(new BlurTransformation(getApplicationContext()))
//                .into((ImageView) findViewById(R.id.background_register));  //for photo blur

        Picasso.get()
                .load(R.drawable.background_register)
                .resize(1440,2560 )
                .onlyScaleDown()
                .into((ImageView) findViewById(R.id.background_register));


        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        phno = findViewById(R.id.phno);
        email = findViewById(R.id.email);
        pw = findViewById(R.id.pw);
        conpw = findViewById(R.id.conpw);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fname.getText().toString().trim().equals("")){
                    fname.setError(fname.getHint()+" is required.");
                    return;
                }
                if(lname.getText().toString().trim().equals("")){
                    lname.setError(lname.getHint()+" is required.");
                    return;
                }
                if(phno.getText().toString().trim().equals("")){
                    phno.setError(phno.getHint()+" is required.");
                    return;
                }
                if(email.getText().toString().trim().equals("")){
                    email.setError(email.getHint()+" is required.");
                    return;
                }
                if(pw.getText().toString().trim().equals("")){
                    pw.setError(pw.getHint()+" is required.");
                    return;
                }
                if(fname.getText().toString().trim().equals("")){
                    conpw.setError(conpw.getHint()+" is required.");
                    return;
                }

                if(!pw.getText().toString().equals(conpw.getText().toString())){
                    Toast.makeText(Register.this, "Password Not Match !", Toast.LENGTH_SHORT).show();
                    return;
                }

                user = new User();
                user.setId(UUID.randomUUID().toString());
                Log.e("userid",user.getId()+"");
                user.setFname(fname.getText().toString());
                user.setLname(lname.getText().toString());
                user.setPhno(phno.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPw(pw.getText().toString());

                SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
                f.setTimeZone(TimeZone.getTimeZone("UTC"));
                f.getCalendar().add(Calendar.MINUTE,390);
//                System.out.println(f.format(new Date(System.currentTimeMillis() + 3600000 * 6 + 1800000)));

                String now = f.format(new Date(System.currentTimeMillis() + 3600000 * 6 + 1800000));

                Toast.makeText(Register.this, now, Toast.LENGTH_SHORT).show();

                user.setCreatedDate(now);
                user.setModifiedDate(now);
                user.setTypeid("71dd0243-9125-48c3-aa63-9660bbbb4829");

                InsertUpdateDB(user,false);

                Toast.makeText(Register.this, "Registration Successful.", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(Register.this, Login.class);
//                MyApplication globlevariable = (MyApplication) getApplicationContext();
//                globlevariable.setLoginstate(true);
//                globlevariable.setId(user.getId());
                startActivity(intent);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public void InsertUpdateDB(final User UserObj , final boolean update) {

        final  User userobj = UserObj;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        Query query = databaseReference.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    int key = 0;
//                    String obj_key = "";
//                    String new_key = "";

                    key = Integer.parseInt(ds.getKey());

                    if(!update){
//                        obj_key = ds.child("id").getValue() + "";
//                        new_key = Util.AutoID(obj_key);
                        key = key+1;
//                        townshipObj.setId(new_key);
                    }




                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("User").child((key) + "").setValue(userobj).addOnSuccessListener(new OnSuccessListener<Void>() {
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
