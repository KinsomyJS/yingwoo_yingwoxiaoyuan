package com.yingwoo.yingwoxiaoyuan.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yingwoo.yingwoxiaoyuan.R;


/**
 * Created by FJS0420 on 2016/9/12.
 */

public class SwitchButton extends View implements View.OnTouchListener {
    //开关背景图
    private Bitmap bgBitmap;
    //开关按钮图
    private Bitmap btnBitmap;
    private Paint paint;
    //标记开关滑动的值
    private int leftDis=0;
    //标记开关滑动的最大值
    private int slidingMax;
    //设置开关对应的文本
    private final String text1="全部";
    private final String text2="我的";
    //标记开关状态
    private boolean mCurrent;
    //标记点击事件
    private boolean isClickable;
    //标记滑动事件
    private boolean isMove;
    //开关打开的事件监听器
    private SoftFloorListener softFloorListener;
    //开关关闭的事件监听器
    private HydropowerListener hydropowerListener;
    //标记开关文本的宽度
    float width1,width2;
    //记录文本中心点 cx1:绘制文本1的x坐标  cx2:绘制文本2的x坐标
    //cy记录绘制文本的高度
    float cx1,cy,cx2;
    //代码实例化需要的方法
    public SwitchButton(Context context) {
        this(context,null);
    }
    //在xml布局时需要用到的方法
    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化数据
    private void initView() {
        //加载背景图
        bgBitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.huadongbiaoqian_d);
        //加载开关按钮图
        btnBitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.huadongbiaoqian_b);
        paint=new Paint();
        slidingMax=bgBitmap.getWidth()-btnBitmap.getWidth();
        paint.setTextSize(38);
        //测量绘制文本1的宽度
        width1= paint.measureText(text1);
        //测量文本的宽度
        Paint.FontMetricsInt fontMetricsInt=paint.getFontMetricsInt();

        cy=btnBitmap.getHeight()/2+(fontMetricsInt.descent-fontMetricsInt.ascent)/2;
        width2= paint.measureText(text2);
        cx2=(bgBitmap.getWidth()*2-btnBitmap.getWidth())/2-width2/2;
        paint.setAntiAlias(true);
        setOnTouchListener(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //根据加载图片设置控件的大小
        setMeasuredDimension(bgBitmap.getWidth(),bgBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景图
        canvas.drawBitmap(bgBitmap,0,0,paint);
        //绘制按钮图
        canvas.drawBitmap(btnBitmap,leftDis,0,paint);
        //根据不同状态绘制不同颜色，不同位置的文本
        if (mCurrent){
            paint.setColor(Color.WHITE);
            canvas.drawText(text2,cx2,cy,paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(text1,cx1,cy,paint);
        }else {
            paint.setColor(Color.WHITE);
            canvas.drawText(text1,cx1,cy,paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(text2,cx2,cy,paint);
        }


    }



    //根据事件刷新视图
    private void flushView() {
        mCurrent=!mCurrent;
        if (mCurrent){
            leftDis=slidingMax;
            if (hydropowerListener!=null){
                //按钮打开监听器
                hydropowerListener.hydropower();
            }
        }else {
            leftDis=0;
            if (softFloorListener!=null){
                //按钮关闭监听器
                softFloorListener.softFloor();
            }
        }
        invalidate();
    }

    //startX 标记手指按下的X坐标,  lastX 标记移动后的x坐标
    //disX 标记x方向移动的距离
    float startX,lastX,disX;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isClickable=true;
                startX=event.getX();
                isMove=false;
                break;
            case MotionEvent.ACTION_MOVE:
                lastX=event.getX();
                disX=lastX-startX;
                //设置一个移动的阈值
                if (Math.abs(disX)<5) break;
                isMove=true;
                isClickable=false;
                moveBtn();
                startX=event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //点击事件
                if (isClickable){
                    flushView();
                }
                //滑动事件
                if (isMove){
                    if (leftDis>slidingMax/2){
                        mCurrent=false;
                    }else {
                        mCurrent=true;
                    }
                    flushView();
                }
                break;
        }

        return true;
    }


    //按钮滑动时的位置控制
    private void moveBtn() {
        leftDis+=disX;
        if (leftDis>slidingMax){
            leftDis=slidingMax;
        }else if (leftDis<0){
            leftDis=0;
        }
        invalidate();
    }


    //设置按钮打开监听器
    public void setSoftFloorListener(SoftFloorListener softFloorListener){
        this.softFloorListener=softFloorListener;
    }

    //设置按钮关闭监听器
    public void setHydropowerListener(HydropowerListener hydropowerListener){
        this.hydropowerListener=hydropowerListener;
    }

    //设置按钮打开监听器接口
    public interface SoftFloorListener{
        void softFloor();
    }

    //设置按钮打开监听器接口
    public interface HydropowerListener{
        void hydropower();
    }
}
