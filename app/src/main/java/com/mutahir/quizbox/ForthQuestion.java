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

public class ForthQuestion extends Activity {

        AnimationDrawable animationDrawable;
        RelativeLayout relativeLayout;
        TextView textView;
        RadioGroup rg;
        RadioButton humor;
        RadioButton look;
        RadioButton artifical;
        RadioButton natural;
        ImageView btn_scan_qr;
        DatabaseReference myRef;

        String forthQ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.forthquestion);

            relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
            animationDrawable.setEnterFadeDuration(5000);
            animationDrawable.setExitFadeDuration(2000);
            myRef = FirebaseDatabase.getInstance().getReference();
            textView = (TextView) findViewById(R.id.textView2);
            final Intent intent1 = getIntent();
            final String firstName=intent1.getStringExtra("key");
            final String lastName=intent1.getStringExtra("key1");
            textView.setText("What does " + firstName + " " + lastName + " find the most charming? ");
            rg = (RadioGroup) findViewById(R.id.rg);
            humor = (RadioButton) findViewById(R.id.humor);
            look = (RadioButton) findViewById(R.id.look);
            artifical = (RadioButton) findViewById(R.id.artifical);
            btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
            natural = (RadioButton) findViewById(R.id.natural);


            btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
                public void onClick(View v) {
                    if(humor.isChecked()||look.isChecked()||artifical.isChecked()||natural.isChecked()) {
                        if (humor.isChecked()) {
                            forthQ = humor.getText().toString();

                        } else if (look.isChecked()) {
                            forthQ = look.getText().toString();

                        } else if (artifical.isChecked()) {
                            forthQ = artifical.getText().toString();

                        } else if (natural.isChecked()) {
                            forthQ = natural.getText().toString();

                        }
                        String userName=intent1.getStringExtra("key2");
                        SignUpDataBase forth=new SignUpDataBase();
                        forth.forthQuestion(userName,forthQ);
                        myRef.child("Answers/"+userName+"/"+"forthQuestion").setValue(forth);
                        Intent intent=new Intent(getApplicationContext(),FifthQuestion.class);
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


