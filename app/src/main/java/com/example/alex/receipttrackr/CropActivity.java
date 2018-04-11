package com.example.alex.receipttrackr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    com.theartofdev.edmodo.cropper.CropImageView cropImageView;
    Button btn, showBtn;
    ImageView imageView;
    String[] lines, supermarkets;
    Integer index = 0;
    Receipt newReceipt;
    TextView textView, guideTxtView;
    Bitmap receiptBitmap;
    private static final long serialVersionUID = 1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        showBtn = findViewById(R.id.showBtn);
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

        cropImageView.setCropRect(new Rect(300,300,1800,3100));

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Item item: newReceipt.getItems()) {
                    textView.append(item.getItemName() + " : " + item.getItemPrice() + "\n");
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        Bitmap cropped = cropImageView.getCroppedImage();
        imageView.setImageBitmap(cropped);

            lines[index] = getTxtfromImg(cropped);
            cropImageView.setCropRect(new Rect(2200,300,3000,3100));
            guideTxtView.setText(getResources().getText(R.string.crop_item_prices));
            index++;

            if (index == 2) {
                setReceiptData();
                showReviewActivity();
            }

    }

    private void showReviewActivity() {
        Intent reviewActivity = new Intent(CropActivity.this, ReviewActivity.class);

        Parcelable wrapped = Parcels.wrap(newReceipt);
        Bundle bundle = new Bundle();
        bundle.putParcelable("example", wrapped);
        startActivity(reviewActivity);
    }

    private void setReceiptData() {
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
