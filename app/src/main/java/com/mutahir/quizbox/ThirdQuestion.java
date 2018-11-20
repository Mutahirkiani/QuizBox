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

public class ThirdQuestion extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton singer;
    RadioButton writer;
    RadioButton painter;
    RadioButton actor;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String thirdQ;
    @Override
    protected void onCreate(Bundle savedIwritertanceState) {
        super.onCreate(savedIwritertanceState);
        setContentView(R.layout.thirdquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);
        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText("What is " + firstName + " "+ lastName + " artistic side like? ");
        rg = (RadioGroup) findViewById(R.id.rg);
        singer = (RadioButton) findViewById(R.id.singer);
        writer = (RadioButton) findViewById(R.id.writer);
        painter = (RadioButton) findViewById(R.id.painter);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        actor = (RadioButton) findViewById(R.id.actor);


        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                if(singer.isChecked()||writer.isChecked()||painter.isChecked()||actor.isChecked()) {
                    if (singer.isChecked()) {
                        thirdQ = singer.getText().toString();

                    } else if (writer.isChecked()) {
                        thirdQ = writer.getText().toString();

                    } else if (painter.isChecked()) {
                        thirdQ = painter.getText().toString();

                    } else if (actor.isChecked()) {
                        thirdQ = actor.getText().toString();
                    }
                    String username=intent1.getStringExtra("key2");
                    SignUpDataBase third=new SignUpDataBase();
                    third.thirdQuestion(username,thirdQ);
                    myRef.child("Answers/"+username+"/"+"thirdQuestion").setValue(third);

                    Intent intent=new Intent(getApplicationContext(),ForthQuestion.class);
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
