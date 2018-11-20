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

public class SecondQuestion extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton dT;
    RadioButton nS;
    RadioButton pE;
    RadioButton iK;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String secondQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);
        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText("If " + firstName + " "+ lastName + " could share a meal with one of these people, who would it be? ");
        rg = (RadioGroup) findViewById(R.id.rg);
        dT = (RadioButton) findViewById(R.id.dT);
        nS = (RadioButton) findViewById(R.id.nS);
        pE = (RadioButton) findViewById(R.id.pE);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        iK = (RadioButton) findViewById(R.id.iK);


        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                if(dT.isChecked()||nS.isChecked()||pE.isChecked()||iK.isChecked()) {
                    if (dT.isChecked()) {
                        secondQ = dT.getText().toString();

                    } else if (nS.isChecked()) {
                        secondQ = nS.getText().toString();

                    } else if (pE.isChecked()) {
                        secondQ = pE.getText().toString();

                    } else if (iK.isChecked()) {
                        secondQ = iK.getText().toString();
                    }
                    String username=intent1.getStringExtra("key2");
                    SignUpDataBase second=new SignUpDataBase();
                    second.secondQuestion(username,secondQ);
                    myRef.child("Answers/"+username+"/"+"secondQuestion").setValue(second);

                    Intent intent=new Intent(getApplicationContext(),ThirdQuestion.class);
                    intent.putExtra("key",firstName);
                    intent.putExtra("key1",lastName);
                    intent.putExtra("key2",username);
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
