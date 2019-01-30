package com.prometheus.gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prometheus.gallery.obj.User;

public class Profile extends AppCompatActivity {

    private TextView name;
    private TextView phno;
    private TextView add;
    private TextView email;
    private TextView createddate;
    private TextView usertype;
    private Button btn_logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        User userobj = ((MyApplication)getApplication()).getUserobj();

        name = findViewById(R.id.name);
        phno = findViewById(R.id.phno);
        add = findViewById(R.id.add);
        email = findViewById(R.id.email);
        createddate = findViewById(R.id.createddate);
        usertype = findViewById(R.id.usertype);
        btn_logout = findViewById(R.id.btn_logout);

        if(userobj!=null){
            name.setText(userobj.getFname() + " " + userobj.getLname());
            phno.setText(userobj.getPhno());
            add.setText(userobj.getAddress().equals("")?"Live in somewhere.":userobj.getAddress());
            email.setText(userobj.getEmail());
            createddate.setText("Created On " + userobj.getCreatedDate());
            usertype.setText("User Type - " + userobj.getUserType());
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication)getApplication()).buttonClick);

                ((MyApplication) getApplication()).setUserobj(null);
                Intent intent = new Intent(Profile.this, home.class);
                startActivity(intent);
            }
        });
    }
}
