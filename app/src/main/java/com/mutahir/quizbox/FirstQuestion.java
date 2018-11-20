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

public class FirstQuestion extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton rude;
    RadioButton angry;
    RadioButton calm;
    RadioButton aggressive;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String firstQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);


        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText("What kind of person "+firstName +" "+lastName+" is?");



        rude=(RadioButton)findViewById(R.id.rude);
        angry = (RadioButton) findViewById(R.id.angry);
        calm = (RadioButton) findViewById(R.id.calm);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        aggressive = (RadioButton) findViewById(R.id.aggressive);
        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
               if(rude.isChecked()||angry.isChecked()||calm.isChecked()||aggressive.isChecked()) {
                   if (rude.isChecked()) {
                       firstQ = rude.getText().toString();

                   } else if (angry.isChecked()) {
                       firstQ = angry.getText().toString();

                   } else if (calm.isChecked()) {
                       firstQ = calm.getText().toString();

                   } else if (aggressive.isChecked()) {
                       firstQ = aggressive.getText().toString();

                   }

                   String userName=intent1.getStringExtra("key2");
                   SignUpDataBase first=new SignUpDataBase();
                   first.firstQuestion(userName,firstQ);
                   myRef.child("Answers/"+userName+"/"+"firstQustion").setValue(first);
                   Intent intent=new Intent(getApplicationContext(),SecondQuestion.class);
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
