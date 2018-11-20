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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gospelware.liquidbutton.LiquidButton;

import Model.SignUpDataBase;

public class FirstQuestionAnswer extends Activity {

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
    LiquidButton buttonLiquid;
    String firstQ;
    int count=0;
    int count1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstquestionanswer);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);

        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("firstName");
        final String lastName=intent1.getStringExtra("lastName");
        final String pUserName=intent1.getStringExtra("pUserName");
        final String userName=intent1.getStringExtra("userName");
        final String pFirstName = intent1.getStringExtra("pFirstName");
        final String pLastName = intent1.getStringExtra("pLastName");
        textView.setText("What kind of person "+firstName +" "+lastName+" is?");

        rude=(RadioButton)findViewById(R.id.rude);
        angry = (RadioButton) findViewById(R.id.angry);
        calm = (RadioButton) findViewById(R.id.calm);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        aggressive = (RadioButton) findViewById(R.id.aggressive);
        buttonLiquid=(LiquidButton)findViewById(R.id.buttonLiquid);

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
                    myRef=FirebaseDatabase.getInstance().getReference().child("Answers").child(userName).child("firstQustion");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            SignUpDataBase c = dataSnapshot.getValue(SignUpDataBase.class);
                            String ans = c.getFirstQuestion();
                            if(firstQ.equals(c.firstQuestion)){
                                count1=count+1;
                                Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
                                buttonLiquid.performClick();

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SecondQuestionAnswer.class);
                                intent.putExtra("firstName", firstName);
                                intent.putExtra("lastName", lastName);
                                intent.putExtra("pUserName", pUserName);
                                intent.putExtra("pFirstName",pFirstName);
                                intent.putExtra("pLastName",pLastName);
                                intent.putExtra("userName", userName);
                                intent.putExtra("count",count);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                else{
                    Toast.makeText(getApplication(),"Select one option must",Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonLiquid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiquidButton btn = (LiquidButton) v;
                btn.startPour();
               buttonLiquid.setAutoPlay(true);
            }
        });

        buttonLiquid.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                Intent intent=new Intent(FirstQuestionAnswer.this,SecondQuestionAnswer.class);
                intent.putExtra("firstName",firstName);
                intent.putExtra("lastName",lastName);
                intent.putExtra("pUserName",pUserName);
                intent.putExtra("userName",userName);
                intent.putExtra("pFirstName",pFirstName);
                intent.putExtra("pLastName",pLastName);
                intent.putExtra("count",count1);
                startActivity(intent);
                finish();
            }
            @Override
            public void onProgressUpdate(float progress) {
                //textView.setText(String.format("%.2f", progress * 100) + "%");
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
