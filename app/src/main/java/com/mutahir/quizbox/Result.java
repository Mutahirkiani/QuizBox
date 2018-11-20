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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import Model.SignUpDataBase;

/**
 * Created by mutahir on 5/11/2017.
 */

public class Result extends Activity {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    ImageView profile_image;
    ImageView profile_image1;
    TextView result;
    Button profile;
    TextView result1;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);


        profile_image=(ImageView)findViewById(R.id.profile_image);
        profile_image1=(ImageView)findViewById(R.id.profile_image1);
        result1=(TextView)findViewById(R.id.result1);
        profile=(Button)findViewById(R.id.logout);
        result=(TextView)findViewById(R.id.result);

        final Intent intent1 = getIntent();
        final String firstName=intent1.getStringExtra("firstName");
        final String lastName=intent1.getStringExtra("lastName");
        final String pUserName=intent1.getStringExtra("pUserName");
        final String userName=intent1.getStringExtra("userName");

        final String pFirstName = intent1.getStringExtra("pFirstName");
        final String pLastName = intent1.getStringExtra("pLastName");
        final int count=intent1.getIntExtra("count",0);

        result1.setText(""+intent1.getIntExtra("count",0)+"/10");

        myRef= FirebaseDatabase.getInstance().getReference();
        SignUpDataBase scores=new SignUpDataBase();
        scores.score(pUserName, String.valueOf(count));
        myRef.child("Result/"+userName+"/"+pUserName).setValue(scores);

        profile.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),Profile.class);
                intent.putExtra("key",pUserName);
                intent.putExtra("key1",pFirstName);
                intent.putExtra("key2",pLastName);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });

        if((result1.getText().toString()).equals("0/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is 0%");
        }
        else if ((result1.getText().toString()).equals("1/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is only 10%");
        }
       else if((result1.getText().toString()).equals("2/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is only 20%");
        }
        else if((result1.getText().toString()).equals("3/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is only 30%");
        }
        else if((result1.getText().toString()).equals("4/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is 40%");
        }
        else if((result1.getText().toString()).equals("5/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is 50%");
        }
        else if((result1.getText().toString()).equals("6/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is 60%");
        }
        else if((result1.getText().toString()).equals("7/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is 70%");
        }
        else if((result1.getText().toString()).equals("8/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is 80%");
        }
        else if((result1.getText().toString()).equals("9/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"only 90%");
        }
        else if((result1.getText().toString()).equals("10/10")){
            result.setText("You know about "+firstName+" "+lastName+" "+"is 1000%");
        }


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://quizbox-d1ca8.appspot.com").child(pUserName + "/rivers.jpg");
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
        FirebaseStorage storage1 = FirebaseStorage.getInstance();

        StorageReference storageRe = storage1.getReferenceFromUrl("gs://quizbox-d1ca8.appspot.com").child(userName + "/rivers.jpg");
        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageRe.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    profile_image1.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e) {
        }



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
