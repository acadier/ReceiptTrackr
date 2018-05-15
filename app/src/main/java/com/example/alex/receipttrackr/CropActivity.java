package com.example.alex.receipttrackr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    private com.theartofdev.edmodo.cropper.CropImageView cropImgView;
    private Button btn;
    private String[] lines, supermarkets, paymentMethods;
    private Integer index = 0, lineCount = 0;
    private Rect cropRect;
    private Receipt newReceipt;
    private TextView textView, guideTxtView;
    private Bitmap receiptBitmap;
    private static final long serialVersionUID = 1L;
    private DataStore dataStore;
    private Boolean firstAttempt = true;
    private Bitmap croppedImg;
    private String croppedTxt, rawTxt;
    private ProgressBar progressBar;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        dataStore = new DataStore(this);

        cropImgView = findViewById(R.id.cropImageView);
        guideTxtView = findViewById(R.id.guideTxtView);
        progressBar = findViewById(R.id.progressBar);

        supermarkets = getResources().getStringArray(R.array.supermarkets);
        paymentMethods = getResources().getStringArray(R.array.paymentMethods);

        btn = findViewById(R.id.button);

        btn.setOnClickListener(this);
        lines = new String[3];

        File file =  new File(android.os.Environment.getExternalStorageDirectory(),"zz.jpeg");
        receiptBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        cropImgView.setImageBitmap(receiptBitmap);

        newReceipt = new Receipt();
        rawTxt = getTxtfromImg(receiptBitmap);

        if (rawTxt != "")  {
            newReceipt.setRawText(rawTxt);
            cropImgView.setCropRect(new Rect(0,0,1260,4032));

        }
        else {
            showErrorDialog();
        }
    }

    public void onClick(View view) {
        progressBar.setVisibility(View.VISIBLE);

        //Crop and get item names

        croppedTxt = getTxtfromImg(getCroppedImg());
        Boolean namesAndPricesMatch;

        try {
            if (firstAttempt) {
                //Set item names
                lineCount = newReceipt.setItemNames(croppedTxt);

                if (lineCount != 0) {
                    //Move crop window to item prices
                    moveCropRect();

                    //Crop and get item prices
                    croppedTxt = getTxtfromImg(getCroppedImg());

                    //Check if prices and names match
                    namesAndPricesMatch = newReceipt.setItemPrices(croppedTxt, lineCount);

                    if (namesAndPricesMatch){
                        setReceiptFields();
                        dataStore.saveReceipt(newReceipt);
                        showReceiptReviewActivity();
                    }
                    else {
                        //They don't match
                        guideTxtView.setText(getResources().getText(R.string.crop_item_prices));
                        firstAttempt = false;
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    showErrorDialog();
                }

            }
            else {
                //Last attempt
                namesAndPricesMatch = newReceipt.setItemPrices(croppedTxt, lineCount);
                if (namesAndPricesMatch) {
                    setReceiptFields();
                    dataStore.saveReceipt(newReceipt);
                    showReceiptReviewActivity();
                }
                else {
                    guideTxtView.setText("Unable to capture receipt");
                    progressBar.setVisibility(View.INVISIBLE);
                    showErrorDialog();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showErrorDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CropActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.capture_error_dialog, null);

        Button retryBtn = mView.findViewById(R.id.retryBtn);
        Button cancelBtn = mView.findViewById(R.id.cancelBtn);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCaptureReceiptActivity();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showViewReceiptsActivity();
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    private void moveCropRect(){
        cropRect = cropImgView.getCropRect();
        cropImgView.setCropRect(new Rect(1764, cropRect.top,3024, cropRect.bottom));
    }

    private Boolean setItemPrices() throws IOException {

        if (newReceipt.setItemPrices(croppedTxt, lineCount)) {
            setReceiptFields();
            dataStore.saveReceipt(newReceipt);
            showReceiptReviewActivity();
            return true;
        }
        return false;
    }

    private Bitmap getCroppedImg() {
        return cropImgView.getCroppedImage();
    }


    private void showReceiptReviewActivity() {
        Intent intent = new Intent(CropActivity.this, ReviewActivity.class);
        startActivity(intent);
    }

    private void showViewReceiptsActivity() {
        Intent intent = new Intent(CropActivity.this, ViewReceiptsActivity.class);
        startActivity(intent);
    }

    private void showCaptureReceiptActivity() {
        Intent intent = new Intent(CropActivity.this, CaptureReceiptActivity.class);
        startActivity(intent);
    }

    private void setReceiptFields() {
        newReceipt.setStoreName(supermarkets);
        newReceipt.setPaymentMethod(paymentMethods);
        newReceipt.setPrintedDate();
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

    private void getItemNamesAndPrices() {

    }
}
