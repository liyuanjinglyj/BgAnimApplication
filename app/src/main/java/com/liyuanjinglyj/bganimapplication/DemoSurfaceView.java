package com.liyuanjinglyj.bganimapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DemoSurfaceView extends SurfaceView {
    private SurfaceHolder surfaceHolder;
    public DemoSurfaceView(Context context) {
        super(context);
    }

    public DemoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    public DemoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
