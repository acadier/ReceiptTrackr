package com.example.alex.receipttrackr;

import android.content.Context;
import android.graphics.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class CameraView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraView(Context context, Camera camera) {
        super(context);

        mCamera = camera;

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
