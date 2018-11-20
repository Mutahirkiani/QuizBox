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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.SignUpDataBase;


public class FifthQuestion extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton cofee;
    RadioButton tea;
    RadioButton cig;
    RadioButton food;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String fifthQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifthquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);
        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText(firstName + " " + lastName + " is addicted to?");
        rg = (RadioGroup) findViewById(R.id.rg);
        cofee = (RadioButton) findViewById(R.id.Cofee);
        tea = (RadioButton) findViewById(R.id.tea);
        cig = (RadioButton) findViewById(R.id.cig);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        food = (RadioButton) findViewById(R.id.food);


        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                if (cofee.isChecked()) {
                    fifthQ = cofee.getText().toString();

                } else if (tea.isChecked()) {
                    fifthQ = tea.getText().toString();

                } else if (cig.isChecked()) {
                    fifthQ = cig.getText().toString();

                } else if (food.isChecked()) {
                    fifthQ = food.getText().toString();

                }
                String userName=intent1.getStringExtra("key2");
                SignUpDataBase fifth=new SignUpDataBase();
                fifth.fifthQuestion(userName,fifthQ);
                myRef.child("Answers/"+userName+"/"+"fifthQuestion").setValue(fifth);
                Intent intent=new Intent(getApplicationContext(),SixthQuestion.class);
                intent.putExtra("key",firstName);
                intent.putExtra("key1",lastName);
                intent.putExtra("key2",userName);
                startActivity(intent);
                finish();

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
