package com.mumu.alarm.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

/**
 * Created by acer on 2016/4/14.
 */
public class CircleTextView extends TextView implements Checkable {
    private boolean isChecked;

    private Paint paint;

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isChecked) {
            int width = getWidth();
            int height = getHeight();
            int radius = Math.min(width, height) / 2;

            canvas.drawCircle(width / 2, height / 2, radius, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        toggle();
        invalidate();
        return true;
    }

    @Override
    public void setChecked(boolean b) {
        isChecked = b;
        invalidate();
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        isChecked = !isChecked;
    }
}
