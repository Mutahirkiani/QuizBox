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

public class NinthQuestion extends Activity {
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton study;
    RadioButton friends;
    RadioButton partner;
    RadioButton no;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String ninthQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ninthquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);
        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText("At University, "+firstName+" "+lastName+" spent more time");
        study=(RadioButton)findViewById(R.id.study);
        friends = (RadioButton) findViewById(R.id.friends);
        partner = (RadioButton) findViewById(R.id.partner);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        no = (RadioButton) findViewById(R.id.no);


        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                if(study.isChecked()||friends.isChecked()||partner.isChecked()||no.isChecked()) {
                    if (study.isChecked()) {
                        ninthQ = study.getText().toString();

                    } else if (friends.isChecked()) {
                        ninthQ = friends.getText().toString();

                    } else if (partner.isChecked()) {
                        ninthQ = partner.getText().toString();

                    } else if (no.isChecked()) {
                        ninthQ = no.getText().toString();

                    }
                    String userName=intent1.getStringExtra("key2");
                    SignUpDataBase nine=new SignUpDataBase();
                    nine.ninthQuestion(userName,ninthQ);
                    myRef.child("Answers/"+userName+"/"+"ninthQuestion").setValue(nine);
                    Intent intent=new Intent(getApplicationContext(),TenthQuestion.class);
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
