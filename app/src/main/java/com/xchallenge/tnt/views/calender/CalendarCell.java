package com.xchallenge.tnt.views.calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CalendarCell extends LinearLayout {

    private Paint paint;
    private int height;
    private int width;
    private Path path;
    private RectF rectF;

    private int backgroundColor = Color.parseColor("#22056DAE");

    /**
     * if a day is between start and end
     * normally   draw all
     * if it the first day of month , and also the only day of the fisrt week
     * need draw a round backgound , set ELSE in this case
     */
    public enum State {
        LEFT, RIGHT, NONE, ALL, ELSE
    }

    private State currentState = State.NONE;


    public CalendarCell(Context context) {
        this(context, null);
    }

    public CalendarCell(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarCell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        init();
    }

    private void init() {

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);
        //  if no set, draw RIGHT has a px white line
        paint.setStrokeWidth(1);

        path = new Path();

        rectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        height = h;
        width = w;

        rectF.left = width / 2f - height / 2f;
        rectF.top = 0;
        rectF.right = width / 2f + height / 2f;
        rectF.bottom = height;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        switch (currentState) {
            case LEFT:

                path.moveTo(0, 0);
                path.lineTo(width / 2f, 0);
                path.addArc(rectF, -90, 180);
                path.lineTo(0, height * 1f);
                path.lineTo(0, 0);
                path.close();

                canvas.drawPath(path, paint);
                break;
            case RIGHT:

                path.moveTo(width, 0);
                path.lineTo(width, height);
                path.lineTo(width / 2f, height);
                path.addArc(rectF, 90, 180);
                path.lineTo(width, 0);
                path.close();

                canvas.drawPath(path, paint);

                break;

            case ALL:
                canvas.drawRect(0, 0, width, height, paint);
                break;
            case ELSE:
                canvas.drawCircle(width / 2f, height / 2f, height / 2f, paint);
            case NONE:

                break;
        }

        path.reset();

    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        invalidate();
    }
}
