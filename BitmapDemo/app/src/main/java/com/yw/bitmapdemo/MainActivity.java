package com.yw.bitmapdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 操作Bitmap
 * 一、基本信息：
 * 长度、宽度、单位像素所占的字节数。利用以上信息就可以计算出Bitmap所占内存的大小。公式为：长度*宽度*单位像素所占的字节数
 * 二、Config：表示图片像素的类型
 * 1.ALPHA_8:只有A通道每个像素占一个字节大小，只有透明度没有颜色值 ps:设置遮盖效果
 * 2.ARGB_8888:四个通道都是8位每个像素占4字节，图片质量是最高的，但是响应的占内存也是最大的，ps：即要透明度，又对图片质量要求高
 * 3.ARGB_4444:四个通道都是4位，每个像素占2个字节，图片失真比较严重ps:基本不用
 * 4.RGB_565:没有A通道，每个像素占2字节，图片失真较小，ps:不需要透明通道时使用
 * <p>
 * 三、Android中的压缩格式总结CompressFormat
 * 1.Bitmap.CompressFormat.JPEG  有损压缩（jpeg2000既可以有损也可以无损）。优点：采用了直色道，有丰富的色彩，适合存储照片和生动图像效果。缺点：有损，不适合存储logo和线框类图
 * 2.Bitmap.CompressFormat.PNG    一种无损压缩。优点：支持透明、无损，主要用于小图标、透明背景等。缺点：若色彩复杂生成图片后文件很大
 * 3.Bitmap.CompressFormat.WEBP   Google新开发的图片格式，同时支持有损和无损压缩，使用直色。无损压缩：相同质量的WebP比PNG小大约26%，有损压缩：相同质量的WebP比jpeg小25%~34%
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivImage;
    private ImageView ivNewImage;
    private Button btnScale;
    private Button btnCrop;
    private Button btnRote;
    private Button btnOffset;
    /**
     * 需要裁减的图片
     */
    private Bitmap source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ivImage = findViewById(R.id.ivImage);
        ivNewImage = findViewById(R.id.ivNewImage);
        btnScale = findViewById(R.id.btnScale);
        btnCrop = findViewById(R.id.btnCrop);
        btnRote = findViewById(R.id.btnRote);
        btnOffset = findViewById(R.id.btnOffset);
        btnScale.setOnClickListener(this);
        btnCrop.setOnClickListener(this);
        btnRote.setOnClickListener(this);
        btnOffset.setOnClickListener(this);
        source = BitmapFactory.decodeResource(getResources(), R.mipmap.girl3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScale://缩放
                Bitmap scaleBitmap = Utils.scaleBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.girl3),300,300);
                ivNewImage.setImageBitmap(scaleBitmap);
                break;
            case R.id.btnCrop://裁减
                Bitmap cropBitmap = Utils.createPathBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.girl3));
                ivNewImage.setImageBitmap(cropBitmap);
                break;
            case R.id.btnRote://旋转
                break;
            case R.id.btnOffset://偏移
                break;
        }
    }
}