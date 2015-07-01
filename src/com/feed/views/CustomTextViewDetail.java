package com.feed.views;

import com.example.feed.R;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomTextViewDetail extends TextView {

    public CustomTextViewDetail(Context context) {
        super(context);

        applyCustomFont();
    }

    public CustomTextViewDetail(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont();
    }

    public CustomTextViewDetail(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont();
    }

    private void applyCustomFont() {
        setBackgroundColor(getResources().getColor(R.color.transparent));
        setTextColor(getResources().getColor(R.color.black));
        setTextSize(20);
        setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setLayoutParamaters();
    }

    private void setLayoutParamaters() {
        if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.movie_margin);
            setLayoutParams(layoutParams);

            requestLayout();
        }
    }
}

