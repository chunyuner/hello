package com.example.chunyun.hello;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by richard on 17-4-27.
 */
//基础的相机预览类
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {


    private SurfaceHolder mHolder;
    private Camera mCamera;
    Context context;


    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        this.context = context;

        //设置 SurfaceHolder.Callback 这样才会在视图被创建和销毁的时候得到通知
        mHolder = getHolder();
        mHolder.addCallback(this);
        //已经被遗弃的设定,Android 3.0之前的设备需要此设定
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(getClass().getName(), "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        mCamera.stopPreview();
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
        // start preview with new settings
        try {

            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(getClass().getName(), "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void handleOneFingerTouch(MotionEvent event) {
        float x, y;
        x = event.getX();
        y = event.getY();
        float windsHeight=getHeight();
        Toast toast;
        if(y<windsHeight/2) {
            //用户触摸屏幕上部
            toast=Toast.makeText(this.context, "上", Toast.LENGTH_SHORT);
        }else {
            //用户触摸屏幕下部
            toast=Toast.makeText(this.context, "下", Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        //一根手指
        if (event.getPointerCount() == 1) {
            handleOneFingerTouch(event);
        }
        return true;
    }
}
