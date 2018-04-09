package com.example.alex.receipttrackr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 1888;
    com.theartofdev.edmodo.cropper.CropImageView cropImageView;
    Button btn;
    View.OnClickListener listener;
    ImageView imageView;
    String[] receiptLines;
    Integer count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        cropImageView = findViewById(R.id.cropImageView);
        imageView = findViewById(R.id.imgView);
        cropImageView.setAutoZoomEnabled(false);

        btn = findViewById(R.id.button);

        btn.setOnClickListener(this);
        receiptLines = new String[3];

        File file =  new File(android.os.Environment.getExternalStorageDirectory(),"zz.jpeg");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        cropImageView.setImageBitmap(bitmap);
    }


    @Override
    public void onClick(View view) {
        Bitmap cropped = cropImageView.getCroppedImage();
        imageView.setImageBitmap(cropped);
        receiptLines[count] = imageToText(cropped);
        cropImageView.setCropRect(new Rect(800,0,800,800));


        Log.i("sepLines", receiptLines[count].toString());
        count++;
    }

    public String imageToText(Bitmap bitmap) {
        TextRecognizer txtRecog = new TextRecognizer.Builder(getApplicationContext()).build();

        if (txtRecog.isOperational()) {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlocks = txtRecog.detect(frame);
            String blocks = "";
            String lines = "";
            String words = "";
            for (int index = 0; index < textBlocks.size(); index++) {
                TextBlock tBlock = textBlocks.valueAt(index);
                blocks = blocks + tBlock.getValue() + "\n" + "\n";
                for (Text line : tBlock.getComponents()) {
                    lines = lines + line.getValue() + "\n";
                    for (Text element : line.getComponents()) {
                        words = words + element.getValue() + ", ";
                    }
                }
            }
            return blocks;
        }
        return null;
    }
}
