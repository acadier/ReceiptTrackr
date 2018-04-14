package com.example.alex.receipttrackr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    private com.theartofdev.edmodo.cropper.CropImageView cropImageView;
    private Button btn;
    private ImageView imageView;
    private String[] lines, supermarkets;
    private Integer index = 0;
    private Rect rect;
    private Receipt newReceipt;
    private TextView textView, guideTxtView;
    private Bitmap receiptBitmap;
    private static final long serialVersionUID = 1L;
    private DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        dataStore = new DataStore(this);

        cropImageView = findViewById(R.id.cropImageView);
        imageView = findViewById(R.id.imgView);
        textView = findViewById(R.id.textView);
        guideTxtView = findViewById(R.id.guideTxtView);

        supermarkets = getResources().getStringArray(R.array.supermarkets);

        btn = findViewById(R.id.button);

        btn.setOnClickListener(this);
        lines = new String[3];

        File file =  new File(android.os.Environment.getExternalStorageDirectory(),"zz.jpeg");
        receiptBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        cropImageView.setImageBitmap(receiptBitmap);

        newReceipt = new Receipt();
        newReceipt.setRawText(getTxtfromImg(receiptBitmap));

        cropImageView.setCropRect(new Rect(0,0,1260,4032));

    }


    @Override
    public void onClick(View view) {
        Bitmap cropped = cropImageView.getCroppedImage();
        imageView.setImageBitmap(cropped);

        rect = cropImageView.getCropRect();

        Log.i("square", cropImageView.getCropRect().toString());

        lines[index] = getTxtfromImg(cropped);
        cropImageView.setCropRect(new Rect(1764, rect.top,3024, rect.bottom));
        guideTxtView.setText(getResources().getText(R.string.crop_item_prices));
        index++;

        if (index == 2) {
            setReceiptObj();
            dataStore.saveReceipt(newReceipt);
            showReviewActivity();
            }
    }

    private void showReviewActivity() {
        Intent reviewActivity = new Intent(CropActivity.this, ReviewActivity.class);
        startActivity(reviewActivity);
    }

    private void setReceiptObj() {
        newReceipt.setStoreName(supermarkets);
        try {
            newReceipt.setItemNames(lines[0]);
            newReceipt.setItemPrices(lines[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTxtfromImg(Bitmap bitmap) {
        TextRecognizer txtRecog = new TextRecognizer.Builder(getApplicationContext()).build();

        if (txtRecog.isOperational()) {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlocks = txtRecog.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < textBlocks.size(); i++){
                TextBlock item = textBlocks.valueAt(i);
                stringBuilder.append(item.getValue());
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
        return null;
    }
}
