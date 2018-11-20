package com.mutahir.quizbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.SignUpDataBase;


public class ListOfPeople extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    List<Integer> images;
     DatabaseReference myRef;
     List<String> userNameList;
    static List<String> score1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);


        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        userNameList=new ArrayList<String>();
        score1=new ArrayList<String>();
        images=new ArrayList<Integer>();
        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("firstName");
        final String lastName=intent1.getStringExtra("lastName");
        final String pUserName=intent1.getStringExtra("key");
        final String userName=intent1.getStringExtra("userName");
        final String pFirstName = intent1.getStringExtra("key1");
        final String pLastName = intent1.getStringExtra("key2");
       final ListView list=(ListView)findViewById(R.id.list);
        myRef= FirebaseDatabase.getInstance().getReference();
        myRef.child("Result").child(pUserName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SignUpDataBase c = postSnapshot.getValue(SignUpDataBase.class);
                   final String user_Name = c.getUserName();
                   String score=c.getScore();
                    userNameList.add(user_Name);
                    score1.add(score);

                    final ListAdapter mutahirAdapter=new CustomList(getApplicationContext(),userNameList);
                    list.setAdapter(mutahirAdapter);




                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Button generate=(Button)findViewById(R.id.geneate);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),chartDisplay.class);
                intent.putStringArrayListExtra("userNameList", (ArrayList<String>) userNameList);
                intent.putStringArrayListExtra("score1",(ArrayList<String>) score1);
                startActivity(intent);
            }
        });
    }
}
