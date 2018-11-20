package com.mutahir.quizbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.SignUpDataBase;

/**
 * Created by mutahir on 5/7/2017.
 */

public class TenthQuestion extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton happy;
    RadioButton money;
    RadioButton success;
    RadioButton family;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String firstQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenthquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);

        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText("The most important thing in "+firstName +" "+lastName+" life is?");
        happy=(RadioButton)findViewById(R.id.happy);
        money = (RadioButton) findViewById(R.id.money);
        success = (RadioButton) findViewById(R.id.success);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        family = (RadioButton) findViewById(R.id.family);


        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                if(happy.isChecked()||money.isChecked()||success.isChecked()||family.isChecked()) {
                    if (happy.isChecked()) {
                        firstQ = happy.getText().toString();

                    } else if (money.isChecked()) {
                        firstQ = money.getText().toString();

                    } else if (success.isChecked()) {
                        firstQ = success.getText().toString();

                    } else if (family.isChecked()) {
                        firstQ = family.getText().toString();

                    }
                    String userName=intent1.getStringExtra("key2");
                    SignUpDataBase ten=new SignUpDataBase();
                    ten.tenthQuestion(userName,firstQ);
                    myRef.child("Answers/"+userName+"/"+"tenthQuestion").setValue(ten);
                    Intent intent=new Intent(getApplicationContext(),QuizSuccess.class);
                    intent.putExtra("key",firstName);
                    intent.putExtra("key1",lastName);
                    intent.putExtra("key2",userName);
                    startActivity(intent);
                    finish();
                }


                else{
                    Toast.makeText(getApplication(),"Select one option must",Toast.LENGTH_SHORT).show();
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
