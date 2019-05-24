package com.dynelm.robotdarttest.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dynelm.robotdarttest.R;

/**
 * Created by Administrator on 2018/4/11 0011.
 */

public class DrawCircle extends View {

    private float currentX = 40;
    private float currentY = 50;
    //定义并创建画笔


    public DrawCircle(Context context) {

        super(context);

        // TODO Auto-generated constructor stub
    }

    public DrawCircle(Context context, AttributeSet set){
        super(context,set);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint p = new Paint();

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pingmian);
        canvas.drawBitmap(zoomImg(bmp,800,400),0,0,p);

        //设置画笔的颜色
        p.setColor(Color.RED);
        //绘制一个小圆（作为小球） 四个参数代表坐标   半径  画笔
//        canvas.drawCircle(currentX, currentY, 10, p);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot_n);
        canvas.drawBitmap(zoomImg(bitmap,120,80),currentX,currentY,p);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//
//        //修改currentX currentY两个属性
//        currentX = event.getX();
//        currentY = event.getY();
//        //通知当前组件重绘自己
//        invalidate();
//        //返回true表明该处理方法已经处理该事件
//        return super.onTouchEvent(event);
//    }


    // 等比缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
    public void Activity_draw(int X,int Y){
        currentX = X;
        currentY = Y;
        invalidate();
    }

}