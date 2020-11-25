package com.yw.bitmapdemo;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @ProjectName: BitmapDemo
 * @Package: com.yw.bitmapdemo
 * @ClassName: Utils
 * @Description: Bitmap工具
 * @Author: wei.yang
 * @CreateDate: 2020/11/25 9:34
 * @UpdateUser: 更新者：wei.yang
 * @UpdateDate: 2020/11/25 9:34
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class Utils {
    /**
     * 缩放Bitmap
     *
     * @param source    原图
     * @param newWidth  新的宽度
     * @param newHeight 新的高度
     * @return 返回缩放后的Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap source, int newWidth, int newHeight) {
        int width = source.getWidth();
        int height = source.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(source, 0, 0, width, height, matrix, false);
        if (!source.isRecycled()) {
            source.recycle();
        }
        return newBitmap;
    }

    /**
     * 裁剪圆形
     * @param source
     * @return
     */
    public static Bitmap cropBitmap(Bitmap source){
        int width = source.getWidth();
        int height = source.getHeight();
        int cropWidth = width>height?height:width;
        cropWidth/=2;
        int cropHeight  = (int)(cropWidth/1.2);
        return Bitmap.createBitmap(source,width/3,0,cropWidth,cropHeight,null,false);
    }

    /**
     * 裁剪圆形
     * @param source
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap source){
        int width = source.getWidth();
        int height = source.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(source, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
        return output;
    }

    /**
     * 裁剪圆形
     * @param resource
     * @return
     */
    public static  Bitmap createCircleBitmap(Bitmap resource)
    {
        //获取图片的宽度
        int width = resource.getWidth();
        int height = resource.getHeight();
        Paint paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);

        //创建一个与原bitmap一样宽度的正方形bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        //以该bitmap为低创建一块画布
        Canvas canvas = new Canvas(circleBitmap);
        //以（width/2, width/2）为圆心，width/2为半径画一个圆
        canvas.drawCircle(height/2, height/2, height/2, paint);

        //设置画笔为取交集模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //裁剪图片
        canvas.drawBitmap(resource, 0, 0, paint);

        return circleBitmap;
    }

    /**
     * 裁剪圆角
     * @param resource
     * @return
     */
    public static  Bitmap createRoundBitmap(Bitmap resource)
    {
        //获取图片的宽度
        int width = resource.getWidth();
        int height = resource.getHeight();
        Paint paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);

        //创建一个与原bitmap一样宽度的正方形bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        //以该bitmap为低创建一块画布
        Canvas canvas = new Canvas(circleBitmap);
        //以（width/2, width/2）为圆心，width/2为半径画一个圆
//        canvas.drawCircle(height/2, height/2, height/2, paint);
        canvas.drawRoundRect(0,0,height,height,50,50,paint);
        //设置画笔为取交集模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //裁剪图片
        canvas.drawBitmap(resource, 0, 0, paint);

        return circleBitmap;
    }

    /**
     * 根据路径裁剪
     * @param resource
     * @return
     */
    public static  Bitmap createPathBitmap(Bitmap resource)
    {
        //获取图片的宽度
        int width = resource.getWidth();
        int height = resource.getHeight();
        Paint paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);

        //创建一个与原bitmap一样宽度的正方形bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        //以该bitmap为低创建一块画布
        Canvas canvas = new Canvas(circleBitmap);
        //以（width/2, width/2）为圆心，width/2为半径画一个圆
//        canvas.drawCircle(height/2, height/2, height/2, paint);
//        canvas.drawRoundRect(0,0,height,height,50,50,paint);
        Path path = new Path();
        path.lineTo(0,0);
        path.lineTo(100,100);
        path.lineTo(150,height+80);
        path.lineTo(200,0);
        canvas.drawPath(path,paint);
        //设置画笔为取交集模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //裁剪图片
        canvas.drawBitmap(resource, 0, 0, paint);

        return circleBitmap;
    }
}
