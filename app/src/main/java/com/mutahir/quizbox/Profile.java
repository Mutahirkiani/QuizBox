package com.mutahir.quizbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Model.SignUpDataBase;


public class Profile extends Activity {

    TextView name;
    TextView userName;
    Button quiz;
    ImageView profile_image;
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    DatabaseReference myRefData;
    DatabaseReference mCategoryRef;
    DatabaseReference mOb;
    DatabaseReference mOb1;
    ArrayList<String> userNameList;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> mAdapter;
    Button list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        name = (TextView) findViewById(R.id.name);
        userName = (TextView) findViewById(R.id.userName);
        quiz = (Button) findViewById(R.id.quiz);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        list=(Button)findViewById(R.id.list);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

        final Intent intent = getIntent();
        final String takeUserName = intent.getStringExtra("key");
        final String takeFirstName = intent.getStringExtra("key1");
        final String takeLastName = intent.getStringExtra("key2");
        final String firstNameA=intent.getStringExtra("firstName");
        final String lastNameA=intent.getStringExtra("lastName");
        final String userNameA=intent.getStringExtra("userName");

        myRefData = FirebaseDatabase.getInstance().getReference();
        mCategoryRef = myRefData.child("Answers").child(takeUserName);
        userName.setText("@" + takeUserName);
        name.setText(takeFirstName + " " + takeLastName);
        userNameList = new ArrayList<String>();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://quizbox-d1ca8.appspot.com").child(takeUserName + "/rivers.jpg");
        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    profile_image.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
        }

        myRefData = FirebaseDatabase.getInstance().getReference();
        mCategoryRef = myRefData.child("Answers").child(takeUserName);

        mCategoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SignUpDataBase c = postSnapshot.getValue(SignUpDataBase.class);
                    String tenth = c.getTenthQuestion();


                    if (tenth != null) {
                        quiz.setText("Edit your Quiz");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        quiz.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FirstQuestion.class);
                intent.putExtra("key", takeFirstName);
                intent.putExtra("key1", takeLastName);
                intent.putExtra("key2", takeUserName);
                startActivity(intent);
            }
        });

        mOb = FirebaseDatabase.getInstance().getReference();
        mOb1 = mOb.child("Accounts");

        mOb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SignUpDataBase c = postSnapshot.getValue(SignUpDataBase.class);
                    String user_Name = c.getUserName();
                    userNameList.add(user_Name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        autoCompleteTextView.setAdapter(mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, userNameList));
        autoCompleteTextView.setAdapter(mAdapter);


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userN=mAdapter.getItem(position).toString();
                Intent intent3=new Intent(getApplicationContext(),FriendProfile.class);
                intent3.putExtra("key",userN);
                intent3.putExtra("pUserName",takeUserName);
                intent3.putExtra("pFirstName",takeFirstName);
                intent3.putExtra("pLastName",takeLastName);
                startActivity(intent3);
            }
        });
        list.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Profile.this,ListOfPeople.class);
                intent1.putExtra("key",takeUserName);
                intent1.putExtra("key1",takeFirstName);
                intent1.putExtra("key2",takeLastName);
                intent1.putExtra("firstName", firstNameA);
                intent1.putExtra("lastName", lastNameA);
                intent1.putExtra("userName", userNameA);
                startActivity(intent1);
            }
        });

        Button logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),Login.class);
                startActivity(intent1);
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
