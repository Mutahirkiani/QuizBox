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


public class ForthQuestionAnswer extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    ImageView btn_scan_qr;
    DatabaseReference myRef;
    LiquidButton buttonLiquid;
    RadioGroup rg;
    RadioButton humor;
    RadioButton look;
    RadioButton artifical;
    RadioButton natural;
    String forthQ;
    int count;

    int count1;

    String firstQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forthquestionanswer);

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
        count=intent1.getIntExtra("count",0);


        textView.setText("What does " + firstName + " " + lastName + " find the most charming? ");
        rg = (RadioGroup) findViewById(R.id.rg);
        humor = (RadioButton) findViewById(R.id.humor);
        look = (RadioButton) findViewById(R.id.look);
        artifical = (RadioButton) findViewById(R.id.artifical);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        natural = (RadioButton) findViewById(R.id.natural);
        buttonLiquid=(LiquidButton)findViewById(R.id.buttonLiquid);


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
                    myRef=FirebaseDatabase.getInstance().getReference().child("Answers").child(userName).child("forthQuestion");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            SignUpDataBase c = dataSnapshot.getValue(SignUpDataBase.class);
                            String ans = c.getForthQuestion();
                            if(forthQ.equals(c.forthQuestion)){
                                count1=count+1;
                                Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
                                buttonLiquid.performClick();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), FifthQuestionAnswer.class);
                                intent.putExtra("firstName", firstName);
                                intent.putExtra("lastName", lastName);
                                intent.putExtra("pUserName", pUserName);
                                intent.putExtra("userName", userName);
                                intent.putExtra("pFirstName",pFirstName);
                                intent.putExtra("pLastName",pLastName);
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
                Intent intent=new Intent(ForthQuestionAnswer.this,FifthQuestionAnswer.class);
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
