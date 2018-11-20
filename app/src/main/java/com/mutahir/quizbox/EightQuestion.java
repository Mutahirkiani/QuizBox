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

public class EightQuestion extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TextView textView;
    RadioGroup rg;
    RadioButton con;
    RadioButton st;
    RadioButton ex;
    RadioButton an;
    ImageView btn_scan_qr;
    DatabaseReference myRef;

    String eightQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eightquestion);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        myRef = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView2);
        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("key");
        final String lastName=intent1.getStringExtra("key1");
        textView.setText("About future " + firstName + " " + lastName + " is?");
        rg = (RadioGroup) findViewById(R.id.rg);
        con = (RadioButton) findViewById(R.id.con);
        st = (RadioButton) findViewById(R.id.st);
        ex = (RadioButton) findViewById(R.id.ex);
        btn_scan_qr = (ImageView) findViewById(R.id.btn_scan_qr);
        an = (RadioButton) findViewById(R.id.an);


        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                if(con.isChecked()||st.isChecked()||ex.isChecked()||an.isChecked()) {
                    if (con.isChecked()) {
                        eightQ = con.getText().toString();

                    } else if (st.isChecked()) {
                        eightQ = st.getText().toString();

                    } else if (ex.isChecked()) {
                        eightQ = ex.getText().toString();

                    } else if (an.isChecked()) {
                        eightQ = an.getText().toString();

                    }
                    String userName=intent1.getStringExtra("key2");
                    SignUpDataBase eight=new SignUpDataBase();
                    eight.eightQuestion(userName,eightQ);
                    myRef.child("Answers/"+userName+"/"+"eightQuestion").setValue(eight);
                    Intent intent=new Intent(getApplicationContext(),NinthQuestion.class);
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
