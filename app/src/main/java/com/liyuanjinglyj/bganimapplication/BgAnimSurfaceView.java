package com.liyuanjinglyj.bganimapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

public class BgAnimSurfaceView extends SurfaceView {
    private SurfaceHolder surfaceHolder;//控制器
    private boolean flag=false;//线程是否能执行
    private Bitmap bgBitmap;//背景图片
    private float screenWidht,screenHeight;//屏幕宽高
    private int mBgX;//绘制的X坐标
    private Canvas canvas;//画布
    private Thread thread;//线程

    //定义一个枚举类型，判断移动的方向
    private enum State{
        LEFT,RIGHT
    }

    private State state=State.LEFT;//开始向左运动
    private final int MOVE_SIZE=1;//每次移动的距离
    public BgAnimSurfaceView(Context context) {
        super(context);
    }

    /***
     * 开始绘制
     */
    private void drawView(){
        this.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//先清空屏幕
        this.canvas.drawBitmap(this.bgBitmap,this.mBgX,0,null);//绘制图片
        switch (this.state){
            case LEFT:
                this.mBgX-=this.MOVE_SIZE;//向左移动
                break;
            case RIGHT:
                this.mBgX+=this.MOVE_SIZE;//向右移动
                break;
            default:
                break;
        }
        //如果向左移动了1/2，那么更改为向右移动
        if(this.mBgX<=-this.screenWidht/2){
            this.state=State.RIGHT;
        }
        //如果X坐标大于0，向左移动
        if(this.mBgX>=0){
            this.state=State.LEFT;
        }
    }

    /***
     * 执行动画
     */
    private void startAnim(){
        this.screenWidht=getWidth();
        this.screenHeight=getHeight();
        int enlargeWidht=(int) getWidth()*3/2;//放大的倍数
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.background);
        this.bgBitmap=Bitmap.createScaledBitmap(bitmap,enlargeWidht,(int)this.screenHeight,true);
        this.thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    canvas=surfaceHolder.lockCanvas();
                    drawView();//绘制
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.thread.start();
    }

    public BgAnimSurfaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                flag=true;
                startAnim();//执行动画
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                flag=false;
            }
        });
    }

    public BgAnimSurfaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
