package com.example.artbox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanojpunchihewa.glowbutton.GlowButton;

public class MainActivity extends AppCompatActivity {

    int CAMERA_REQ_CODE = 100;
    int GALLERY_REQ_CODE = 200;
    public static Uri uri;
    GlowButton edit;
    ImageView imageView;
    FloatingActionButton start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        edit = findViewById(R.id.edit);
        imageView = findViewById(R.id.imageView);
        getSupportActionBar().hide();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                imagePickDialog();

                ImagePicker.Companion.with(MainActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .start();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FinalActivity.class));
            }
        });

    }

    public void imagePickDialog(){

        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    //open camera
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQ_CODE);
                }else{
                    //open gallery
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_REQ_CODE);
                }
            }
        }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(resultCode == RESULT_OK){
//            if(requestCode == CAMERA_REQ_CODE){
//
//                uri = (Uri)data.getData();
//                startActivity(new Intent(MainActivity.this, FinalActivity.class));
////                binding.imageView.setImageBitmap(bitmap);
//            }else if(requestCode == GALLERY_REQ_CODE){
////                binding.imageView.setImageURI(data.getData());
//                uri = data.getData();
//                startActivity(new Intent(MainActivity.this, FinalActivity.class));
//            }
//        }

        try {
            uri = data.getData();
            imageView.setImageURI(uri);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}