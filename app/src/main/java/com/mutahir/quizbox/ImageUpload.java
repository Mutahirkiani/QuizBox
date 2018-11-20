package com.mutahir.quizbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;

public class ImageUpload extends Activity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    Uri filePath;
    Button select;
    Button upload;
    ImageView btn_scan_qr;
    ImageView profilePicture;
    private StorageReference mStorageRef;
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageupload);

        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        select=(Button)findViewById(R.id.select);
        upload=(Button)findViewById(R.id.upload);
        profilePicture=(ImageView)findViewById(R.id.profile_image);
        select.setOnClickListener(this);
        upload.setOnClickListener(this);
        btn_scan_qr=(ImageView) findViewById(R.id.btn_scan_qr);
        btn_scan_qr.setOnClickListener(new ImageView.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),signUpSuccessfull.class);
                startActivity(intent);
            }
        });
    }

    public void uploadFile(){
        if(filePath!=null) {
            final ProgressDialog progressdialog=new ProgressDialog(this);
            progressdialog.setTitle("Uploading.....");
            progressdialog.show();
            Intent intent=getIntent();
           String name= intent.getStringExtra("user");
            StorageReference riversRef = mStorageRef.child(name+"/rivers.jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressdialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Upload Successfull",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressdialog.dismiss();
                            Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressdialog.setMessage(((int) progress) + " % Upload...");
                }
            })
            ;
        }else{
        }
    }
    public void showFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose an Image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            filePath=data.getData();
            try {
                Bitmap bit= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                profilePicture.setImageBitmap(bit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View view) {
        if (view==select){
            showFileChooser();
        }
        else if(view==upload){
            uploadFile();
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