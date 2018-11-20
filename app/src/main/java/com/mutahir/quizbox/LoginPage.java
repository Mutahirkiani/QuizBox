package com.mutahir.quizbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import Model.SignUpDataBase;

public class LoginPage extends Activity {


    EditText user;
    EditText password;
    Button logIn;
    StorageReference storage;
    DatabaseReference databaseSignip;
    DatabaseReference mCategoryRef;
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        databaseSignip = FirebaseDatabase.getInstance().getReference();
        mCategoryRef = databaseSignip.child("Accounts");
        user = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        logIn = (Button) findViewById(R.id.upload);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        logIn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                logIn();
            }
        });
    }
    private void logIn() {


        mCategoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SignUpDataBase c = postSnapshot.getValue(SignUpDataBase.class);
                    String user_Name=c.getUserName();
                    String first_Name=c.getFirstName();
                    String last_Name=c.getLastName();
                    String password1=c.getPassword();
                    if ((user.getText().toString()).equals(user_Name) && (password.getText().toString()).equals(password1)) {
                        Toast.makeText(getApplication(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        intent.putExtra("key",user_Name);
                        intent.putExtra("key1",first_Name);
                        intent.putExtra("key2",last_Name);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }
}