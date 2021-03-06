package com.example.alex.receipttrackr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.net.URI;

public class CaptureResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_result);

        ImageView imgView;
        Bitmap bmp;
        File file;

        Intent intent = getIntent();
        imgView = findViewById(R.id.imgView);

//        byte[] bytes = getIntent().getByteArrayExtra("byteArray");
//        bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"),0, getIntent().getByteArrayExtra("byteArray").length);

//        uri = (URI)intent.getParcelableExtra("uri");

//        imgView.setImageBitmap(bmp);

//        file = getIntent().getExtras("file");

        file =  new File(android.os.Environment.getExternalStorageDirectory(),"12.jpeg");
        bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
        imgView.setImageBitmap(bmp);
    }
}
