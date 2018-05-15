package com.example.alex.receipttrackr;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

//import com.google.android.gms.vision.CameraSource;

public class CaptureReceiptActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {

    SurfaceView cameraView;
    Button captureBtn;
    SurfaceHolder surfaceHolder;
    Camera camera;
    Camera.ShutterCallback shutterCallback;
    PictureCallback jpegCallback;
    ProgressBar progressBar;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_receipt);

        cameraView = findViewById(R.id.surfaceView);
        captureBtn = findViewById(R.id.retryBtn);
        progressBar = findViewById(R.id.progressBar);

        surfaceHolder = cameraView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        shutterCallback = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                progressBar.setVisibility(View.VISIBLE);

            }
        };

        jpegCallback = new PictureCallback() {
            private File imageFile;
            String lines;
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                try {
                    camera.stopPreview();
                    // convert byte array into bitmap
                    Bitmap loadedImage = null;
                    Bitmap rotatedBitmap = null;
                    Bitmap compressedBitmap;
                    loadedImage = BitmapFactory.decodeByteArray(bytes, 0,
                            bytes.length);

                    // rotate Image
                    Matrix rotateMatrix = new Matrix();
                    rotateMatrix.postRotate(90);
                    rotatedBitmap = Bitmap.createBitmap(loadedImage, 0, 0,
                            loadedImage.getWidth(), loadedImage.getHeight(),
                            rotateMatrix, false);

//                    lines = imageToText(rotatedBitmap);

//                    new Receipt(lines);

//                    Log.e("lines", lines);
                    String state = Environment.getExternalStorageState();
                    File folder = null;
                    if (state.contains(Environment.MEDIA_MOUNTED)) {
                        folder = new File(Environment
                                .getExternalStorageDirectory() + "/Demo");
                    } else {
                        folder = new File(Environment
                                .getExternalStorageDirectory() + "/Demo");
                    }

                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdirs();
                    }
                    if (success) {
                        java.util.Date date = new java.util.Date();
                        imageFile = new File(Environment.getExternalStorageDirectory(), "zz.jpeg");
//                        imageFile = new File(folder.getAbsolutePath(),"hello");
//                        imageFile = new File(folder.getAbsolutePath()
//                                + File.separator + "hello"
//                                + DateFormat.getDateTimeInstance().format(new Date()));

                        imageFile.createNewFile();
                    } else {
                        Toast.makeText(getBaseContext(), "Image Not saved",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ByteArrayOutputStream ostream = new ByteArrayOutputStream();

                    // save image into gallery
//                    rotatedBitmap = resizeBitmap(rotatedBitmap, 800, 600);

                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);


                    FileOutputStream fout = new FileOutputStream(imageFile);
                    fout.write(ostream.toByteArray());
                    fout.close();

                    ContentValues values = new ContentValues();

                    values.put(MediaStore.Images.Media.DATE_TAKEN,
                            System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    values.put(MediaStore.MediaColumns.DATA,
                            imageFile.getAbsolutePath());

                    CaptureReceiptActivity.this.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    setResult(Activity.RESULT_OK); //add this
                    finish();


                    Intent cropActivity = new Intent(CaptureReceiptActivity.this, CropActivity.class);
                    startActivity(cropActivity);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        captureBtn.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        camera.takePicture(shutterCallback, null, jpegCallback);
    }

    public Bitmap resizeBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            camera = Camera.open();
        }catch(RuntimeException e){
            Log.v("init_camera:",e.toString());
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(param);

        camera.setDisplayOrientation(90);

//        //modify parameter
//        param.setPreviewFrameRate(20);
//        param.setPreviewSize(176, 144);
//        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            Log.v("init_camera:",e.toString());
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;

    }


}
