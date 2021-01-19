package com.example.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

public class LDSpan extends DynamicDrawableSpan {

    private Context context;
    private Person person;

    private Bitmap bitmap;

    public LDSpan(Context context, Person person, float textSize) {
        this.context = context;
        this.person = person;
        this.bitmap = getNameBitmap(person.getName(), textSize);
    }

    @Override
    public Drawable getDrawable() {
        BitmapDrawable drawable = new BitmapDrawable(
                context.getResources(), bitmap);
        drawable.setBounds(0, 0,
                bitmap.getWidth(),
                bitmap.getHeight());
        return drawable;
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 把返回的人名，转换成bitmap
     * <p>
     * 比如返回@李达达
     *
     * @param name
     * @return
     */
    private Bitmap getNameBitmap(String name, float textSize) {
        /* 把@相关的字符串转换成bitmap 然后使用DynamicDrawableSpan加入输入框中 */
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //设置字体画笔的颜色
        paint.setColor(Color.RED);
        //设置字体的大小
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        // 获取字符串在屏幕上的长度
        int width = (int) (paint.measureText(name));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        // 计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        final Bitmap bmp = Bitmap.createBitmap(width, (int) fontHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
//        canvas.drawColor(getResources().getColor(R.color.color_blue));
        // 计算文字baseline
        float textBaseY = fontHeight  - fontMetrics.bottom;
        canvas.drawText(name, width >> 1, textBaseY, paint);
        return bmp;
    }

}
