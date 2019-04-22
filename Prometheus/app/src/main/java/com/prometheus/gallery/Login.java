package com.prometheus.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.aware.DiscoverySession;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText pw;
    private Button btn_login;
    private TextView btn_register;
    private ImageView background_register;
    private LoginButton loginButton;

    private static final String EMAIL = "email";
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    private String goback = "";

    private ProgressDialog progressDialog;

    private User user;

    public void DismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

//        Picasso.get()
//                .load(R.drawable.background_register)
//                .error(R.mipmap.ic_launcher)
//                .placeholder(R.mipmap.ic_launcher)
//                .into((ImageView) findViewById(R.id.background_register));
//
//        Picasso.get().setLoggingEnabled(true);


        goback = getIntent().getStringExtra("goback") == null ? "" : getIntent().getStringExtra("goback") + "";
        background_register = findViewById(R.id.background_register);

        background_register.setImageResource(R.drawable.background_register);

        email = findViewById(R.id.email);
        pw = findViewById(R.id.pw);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(((MyApplication) getApplication()).buttonClick);

                if (email.getText().toString().trim().equals("")) {
                    email.setError(email.getHint() + " is required.");
                    return;
                }
                if (pw.getText().toString().trim().equals("")) {
                    pw.setError(pw.getHint() + " is required.");
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

        //facebook login starts
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
//                Toast.makeText(Login.this, loginResult.getAccessToken().toString(),Toast.LENGTH_LONG).show();
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setCancelable(true);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
                final com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    final String email = object.getString("email");
//                                    String birthday = object.getString("birthday"); // 01/31/1980 format

//                                    Toast.makeText(Login.this, email + profile.getName(), Toast.LENGTH_LONG).show();

                                    //check for email
                                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                                    Query query = databaseReference.orderByChild("email").equalTo(email);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if (!dataSnapshot.exists()) {

                                                user = new User();
                                                user.setId(UUID.randomUUID().toString());
                                                Log.e("userid", user.getId() + "");
                                                user.setFname(profile.getFirstName());
                                                user.setLname(profile.getLastName());
                                                user.setEmail(email);

                                                SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
                                                f.setTimeZone(TimeZone.getTimeZone("UTC"));
                                                f.getCalendar().add(Calendar.MINUTE, 390);
                                                //System.out.println(f.format(new Date(System.currentTimeMillis() + 3600000 * 6 + 1800000)));

                                                String now = f.format(new Date(System.currentTimeMillis() + 3600000 * 6 + 1800000));

                                                //Toast.makeText(Register.this, now, Toast.LENGTH_SHORT).show();

                                                user.setCreatedDate(now);
                                                user.setModifiedDate(now);
                                                user.setUserType("NormalUser");

                                                Query query = databaseReference.orderByKey().limitToLast(1);
                                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                                int key = 0;
                                                                key = Integer.parseInt(ds.getKey());
                                                                key = key + 1;

                                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                databaseReference.child("User").child((key) + "").setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Log.e("DB_FBCommit", "Success!");
                                                                        ((MyApplication) getApplication()).setUserobj(user);
                                                                        Log.e("User", ((MyApplication) getApplication()).getUserobj().toString());


                                                                        Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                                                                        if (((MyApplication) getApplication()).getGobacklogin().equals("detail")) {
                                                                            Intent intent = new Intent(Login.this, Detail.class);
                                                                            intent.putExtra("postid", ((MyApplication) getApplication()).getPostidforgoback());
                                                                            startActivity(intent);
                                                                        } else {
                                                                            Intent intent = new Intent(Login.this, home.class);
                                                                            startActivity(intent);
                                                                        }
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.e("DB_FBCommit", "Fail!");
                                                                    }
                                                                });


                                                            }
                                                        } else {
                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                            databaseReference.child("User").child("1").setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.e("DB_FBCommit", "Success!");
                                                                    ((MyApplication) getApplication()).setUserobj(user);
                                                                    Log.e("User", ((MyApplication) getApplication()).getUserobj().toString());


                                                                    Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                                                                    if (((MyApplication) getApplication()).getGobacklogin().equals("detail")) {
                                                                        Intent intent = new Intent(Login.this, Detail.class);
                                                                        intent.putExtra("postid", ((MyApplication) getApplication()).getPostidforgoback());
                                                                        startActivity(intent);
                                                                    } else {
                                                                        Intent intent = new Intent(Login.this, home.class);
                                                                        startActivity(intent);
                                                                    }

                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.e("DB_FBCommit", "Fail!");
                                                                }
                                                            });
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        DismissDialog();
                                                    }

                                                });
                                                DismissDialog();
                                            }
                                            else{
                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                    User userobj = ds.getValue(User.class);
                                                    ((MyApplication) getApplication()).setUserobj(userobj);
                                                    Log.e("User", ((MyApplication) getApplication()).getUserobj().toString());


                                                    Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                                                    if (((MyApplication) getApplication()).getGobacklogin().equals("detail")) {
                                                        Intent intent = new Intent(Login.this, Detail.class);
                                                        intent.putExtra("postid", ((MyApplication) getApplication()).getPostidforgoback());
                                                        startActivity(intent);
                                                    } else {
                                                        Intent intent = new Intent(Login.this, home.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                                DismissDialog();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Log.e("DatabaseError", databaseError.getMessage());
                                            DismissDialog();
                                        }

                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    DismissDialog();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(Login.this, "You Cancel Login", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(Login.this, "Facebook Login Error", Toast.LENGTH_LONG).show();
            }
        });

        //facebook login end
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    //match the login
    public void MatchLoginDB(final String email, final String pw) {

        if (progressDialog == null) {
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

                if (!dataSnapshot.exists()) {
                    Toast.makeText(Login.this, "Email Incorrect!", Toast.LENGTH_SHORT).show();
                    DismissDialog();
                    return;
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String dbpw = ds.child("pw").getValue() + "";

                    if (pw.equals(dbpw)) {
                        User userobj = ds.getValue(User.class);
//                            Log.e("User",userobj.toString());
//                            Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();

//                            ((MyApplication)getApplication()).setLoginstate(true);
//                            ((MyApplication)getApplication()).setId(ds.child("id").getValue()+"");
//                            ((MyApplication)getApplication()).setUserType(ds.child("userType").getValue()+"");
                        ((MyApplication) getApplication()).setUserobj(userobj);
                        Log.e("User", ((MyApplication) getApplication()).getUserobj().toString());


                        Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                        DismissDialog();

//                            Intent intent = new Intent(Login.this, Post.class);
//                            if(goback.equals("")){
//                                Intent intent = new Intent(Login.this, home.class);
//                                startActivity(intent);
//                            }
//                            else{
//                                finish();
//                            }
                        if (((MyApplication) getApplication()).getGobacklogin().equals("detail")) {
                            Intent intent = new Intent(Login.this, Detail.class);
                            intent.putExtra("postid", ((MyApplication) getApplication()).getPostidforgoback());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Login.this, home.class);
                            startActivity(intent);
                        }

                    } else {
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
