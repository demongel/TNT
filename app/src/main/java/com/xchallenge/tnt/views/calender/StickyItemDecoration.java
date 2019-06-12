package com.xchallenge.tnt.views.calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    private final int textLeftPadding;
    private Context context;

    private Paint dividerPaint;

    private Paint textPaint;

    private int dividerHeight;

    public StickyItemDecoration(Context context, OnGroupListener listener) {

        this.context = context;

        dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setColor(Color.parseColor("#FFFFFF"));
        dividerPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(new TextView(context).getCurrentTextColor());
        textPaint.setTextSize(sp2px(16));
        textPaint.setFakeBoldText(true);


        dividerHeight = dp2px(40);

        textLeftPadding = dp2px(16);

        this.listener = listener;

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float temp = fontMetrics.top / 2 + fontMetrics.bottom / 2;
        //  current count on screen , not all
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = parent.getChildAt(i);
            int bottom = childView.getTop();
            //  on each item top
            int top = bottom - dividerHeight;
            c.drawRect(0, top, parent.getWidth(), bottom, dividerPaint);

            int childAdapterPosition = parent.getChildAdapterPosition(childView);


//            float baseLine = (top + bottom) / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            if (bottom <= dividerHeight) {
                float baseLineY = bottom / 2f - temp;
                c.drawText(getGroupName(childAdapterPosition), textLeftPadding,
                        baseLineY, textPaint);
            } else {
                float baseLineY = (top + bottom) / 2f - temp;
                c.drawText(getGroupName(childAdapterPosition), textLeftPadding,
                        baseLineY, textPaint);
            }

        }
    }

    //  over  onDraw
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float temp = fontMetrics.top / 2 + fontMetrics.bottom / 2;

        View firstVisibleView = parent.getChildAt(0);
        int firstVisiblePosition = parent.getChildAdapterPosition(firstVisibleView);
        String groupName = getGroupName(firstVisiblePosition);

        int bottom = firstVisibleView.getBottom();

        if (bottom <= dividerHeight) {
            float baseLineY = bottom / 2f - temp;
            c.drawRect(0, 0, parent.getWidth(), bottom, dividerPaint);
            c.drawText(groupName, textLeftPadding,
                    baseLineY, textPaint);
        } else {
            float baseLineY = dividerHeight / 2f - temp;
            c.drawRect(0, 0, parent.getWidth(), dividerHeight, dividerPaint);
            c.drawText(groupName, textLeftPadding,
                    baseLineY, textPaint);
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // top --> divider  on each top
        // bottom --> divider on each bottom
        outRect.top = dividerHeight;
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    private OnGroupListener listener;

    interface OnGroupListener {
        // get Title for each group
        String getGroupTitle(int position);
    }

    public String getGroupName(int position) {
        if (listener != null) {
            return listener.getGroupTitle(position);
        }
        return "";
    }
}
