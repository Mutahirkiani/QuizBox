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


public class SeventhQuestion extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton man;
    RadioButton woman;
    RadioButton both;
    RadioButton chef;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String seventhQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seventhquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);
        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText("In Relationship who can cook according to " + firstName + " " + lastName + " ?");
        rg = (RadioGroup) findViewById(R.id.rg);
        man = (RadioButton) findViewById(R.id.man);
        woman = (RadioButton) findViewById(R.id.woman);
        both = (RadioButton) findViewById(R.id.both);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        chef = (RadioButton) findViewById(R.id.chef);


        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                if(man.isChecked()||woman.isChecked()||both.isChecked()||chef.isChecked()) {

                    if (man.isChecked()) {
                        seventhQ = man.getText().toString();

                    } else if (woman.isChecked()) {
                        seventhQ = woman.getText().toString();

                    } else if (both.isChecked()) {
                        seventhQ = both.getText().toString();

                    } else if (chef.isChecked()) {
                        seventhQ = chef.getText().toString();

                    }
                    String userName=intent1.getStringExtra("key2");
                    SignUpDataBase seven=new SignUpDataBase();
                    seven.seventhQuestion(userName,seventhQ);
                    myRef.child("Answers/"+userName+"/"+"seventhQuestion").setValue(seven);
                    Intent intent=new Intent(getApplicationContext(),EightQuestion.class);
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
