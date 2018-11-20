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


public class SixthQuestion extends Activity{
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton grow;
    RadioButton weight;
    RadioButton young;
    RadioButton old;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String sixthQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixthquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);
        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText(firstName + " " + lastName + " would choose to?");
        rg = (RadioGroup) findViewById(R.id.rg);
        grow = (RadioButton) findViewById(R.id.grow);
        weight = (RadioButton) findViewById(R.id.weight);
        young = (RadioButton) findViewById(R.id.young);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        old = (RadioButton) findViewById(R.id.old);


        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                if(grow.isChecked()||weight.isChecked()||young.isChecked()||old.isChecked()) {
                    if (grow.isChecked()) {
                        sixthQ = grow.getText().toString();

                    } else if (weight.isChecked()) {
                        sixthQ = weight.getText().toString();

                    } else if (young.isChecked()) {
                        sixthQ = young.getText().toString();

                    } else if (old.isChecked()) {
                        sixthQ = old.getText().toString();

                    }
                    String userName=intent1.getStringExtra("key2");
                    SignUpDataBase six=new SignUpDataBase();
                    six.sixthQuestion(userName,sixthQ);
                    myRef.child("Answers/"+userName+"/"+"sixthQuestion").setValue(six);
                    Intent intent=new Intent(getApplicationContext(),SeventhQuestion.class);
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
