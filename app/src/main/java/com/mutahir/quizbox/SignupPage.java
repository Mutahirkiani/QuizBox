package com.mutahir.quizbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.SignUpDataBase;

public class SignupPage extends Activity {

     EditText userName;
     EditText password;
    EditText firstName;
    EditText lastName;
    Button signUp;
    DatabaseReference databaseSignup;
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    DatabaseReference databaseSignip;
    DatabaseReference mCategoryRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);

        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        databaseSignip = FirebaseDatabase.getInstance().getReference();
        mCategoryRef = databaseSignip.child("Accounts");
        databaseSignup = FirebaseDatabase.getInstance().getReference();
        userName=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        firstName=(EditText)findViewById(R.id.firstName);
        lastName=(EditText)findViewById(R.id.lastName);
        signUp=(Button)findViewById(R.id.upload);

        signUp.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                final String username=userName.getText().toString().trim();
                final String Password=password.getText().toString().trim();
                final String firstname=firstName.getText().toString().trim();
                final String lastname=lastName.getText().toString().trim();
                String id=databaseSignup.push().getKey();

                final SignUpDataBase database=new SignUpDataBase(id,username,Password,firstname,lastname);
                if(!(username.isEmpty()||Password.isEmpty()||firstname.isEmpty()||lastname.isEmpty())) {
                    databaseSignup.child("Accounts/"+username).setValue(database);
                    Intent intent = new Intent(getApplicationContext(), ImageUpload.class);
                    Intent user=intent.putExtra("user",username);
                    startActivity(intent);
                }


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