package com.mutahir.quizbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import Model.SignUpDataBase;

public class FriendProfile extends Activity {

    TextView name;
    TextView userName;
    Button quiz;
    ImageView profile_image;
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    DatabaseReference data;
    DatabaseReference data1;
    DatabaseReference question;
    DatabaseReference question1;
    String firstN;
    String lastN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendprofile);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        name = (TextView) findViewById(R.id.name);
        userName = (TextView) findViewById(R.id.userName);
        quiz = (Button) findViewById(R.id.button2);

        profile_image = (ImageView) findViewById(R.id.profile_image);
        data= FirebaseDatabase.getInstance().getReference();

        Intent intent=getIntent();
        final String userN=intent.getStringExtra("key");
        final String pUserName=intent.getStringExtra("pUserName");
        final String pFirstName = intent.getStringExtra("pFirstName");
        final String pLastName = intent.getStringExtra("pLastName");
        userName.setText("@"+userN);
        data= FirebaseDatabase.getInstance().getReference();
        data1=data.child("Accounts").child(userN);
        data1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SignUpDataBase c = dataSnapshot.getValue(SignUpDataBase.class);
                firstN=c.getFirstName();
                lastN=c.getLastName();
                name.setText(firstN+" "+lastN);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        question = FirebaseDatabase.getInstance().getReference();
        question1 = question.child("Answers").child(userN);

        question1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                    boolean user_Name = dataSnapshot.exists();

                    if (user_Name==true) {
                        quiz.setText("Attempt Quiz");
                        quiz.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i=new Intent(getApplicationContext(),FirstQuestionAnswer.class);
                                i.putExtra("userName",userN);
                                i.putExtra("firstName",firstN);
                                i.putExtra("lastName",lastN);
                                i.putExtra("pUserName",pUserName);
                                i.putExtra("pFirstName",pFirstName);
                                i.putExtra("pLastName",pLastName);

                                startActivity(i);
                            }
                        });


                    } else {
                        quiz.setText("Quiz not created yet");
                        quiz.setClickable(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        FirebaseStorage imageRetrive=FirebaseStorage.getInstance();
        StorageReference image1Retrive = imageRetrive.getReferenceFromUrl("gs://quizbox-d1ca8.appspot.com").child(userN + "/rivers.jpg");

        try {
            final File localFile = File.createTempFile("images", "jpg");
            image1Retrive.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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

        DatabaseReference data=FirebaseDatabase.getInstance().getReference();
        data.child("Result").child(userN).child(pUserName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean user_Name = dataSnapshot.exists();
                if(user_Name==true){
                    quiz.setText("Quiz Already Attempt");
                    quiz.setClickable(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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
