package com.prometheus.gallery;

import android.app.ProgressDialog;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText pw;
    private Button btn_login;
    private TextView btn_register;

    private ProgressDialog progressDialog;

    public void DismissDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Picasso.get()
                .load(R.drawable.background_register)
                .resize(1440,2560 )
                .onlyScaleDown()
                .into((ImageView) findViewById(R.id.background_register));

        email = findViewById(R.id.email);
        pw = findViewById(R.id.pw);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().equals("")){
                    email.setError(email.getHint()+" is required.");
                    return;
                }
                if(pw.getText().toString().trim().equals("")){
                    pw.setError(pw.getHint()+" is required.");
                    return;
                }

                MatchLoginDB(email.getText().toString(), pw.getText().toString());
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    //match the login
    public void MatchLoginDB(final String email, final String pw) {

        if(progressDialog == null){
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Toast.makeText(Login.this, "Email Incorrect!", Toast.LENGTH_SHORT).show();
                    DismissDialog();
                    return;
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String dbpw = ds.child("pw").getValue()+"";

                        if(pw.equals(dbpw)){
//                            Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();

//                            ((MyApplication)getApplication()).setLoginstate(true);
                            ((MyApplication)getApplication()).setId(ds.child("id").getValue()+"");

                            Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                            DismissDialog();

                            Intent intent = new Intent(Login.this, Post.class);
                            startActivity(intent);
                        }
                        else{
                            DismissDialog();
                            Toast.makeText(Login.this, "Password Incorrect!", Toast.LENGTH_SHORT).show();
                        }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", databaseError.getMessage());
                DismissDialog();
            }

        });
    }

}
