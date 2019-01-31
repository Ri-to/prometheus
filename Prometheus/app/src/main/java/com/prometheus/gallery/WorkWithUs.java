package com.prometheus.gallery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

public class WorkWithUs extends AppCompatActivity {

    private ImageView background_register;
    private Button btn_workwithus;
    private CheckBox agree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workwithus);

        background_register = findViewById(R.id.background_register);

        background_register.setImageResource(R.drawable.background_register);

        btn_workwithus = findViewById(R.id.btn_workwithus);
        btn_workwithus.setEnabled(false);
        agree = findViewById(R.id.agree);

        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btn_workwithus.setEnabled(true);
                    btn_workwithus.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                }
                else{
                    btn_workwithus.setEnabled(false);
                    btn_workwithus.setBackground(getResources().getDrawable(R.drawable.buttonshapenwws));
                }
            }
        });



        btn_workwithus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);

                User user = ((MyApplication)getApplication()).getUserobj();

                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                Query query = mDatabase.child("User").orderByChild("id").equalTo(user.getId());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!dataSnapshot.exists()){
                            Toast.makeText(WorkWithUs.this, "That account not exit!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {


                            final User userobj = ds.getValue(User.class);
                            userobj.setUserType("Artist");

                            int key = 0;
                            key = Integer.parseInt(ds.getKey());

                            mDatabase.child("User").child(key+"").setValue(userobj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e("DB_Commit", "Success!");
                                    ((MyApplication)getApplication()).setUserobj(userobj);
                                    Toast.makeText(WorkWithUs.this, "You are now Artist in Prometheus Online Art Gallery.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(WorkWithUs.this, home.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("DB_Commit", "Fail!");
                                    Toast.makeText(WorkWithUs.this, "Error Work With Us", Toast.LENGTH_SHORT).show();
                                }
                            });





                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("DatabaseError", databaseError.getMessage());
//                        DismissDialog();
                    }

                });
            }
        });

    }

}
