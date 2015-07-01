package com.feed.views;

import com.example.feed.R;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feed.R;

public class CustomTextViewTitlesApp  extends TextView {

    public CustomTextViewTitlesApp(Context context) {
        super(context);

        applyCustomFont();
    }

    public CustomTextViewTitlesApp(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont();
    }

    public CustomTextViewTitlesApp(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont();
    }

    private void applyCustomFont() {
        setTextColor(getResources().getColor(R.color.white));
        setBackgroundColor(getResources().getColor(R.color.transparent));
        setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setLayoutParamaters();
    }

    private void setLayoutParamaters() {
        if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            layoutParams.topMargin = (int) getResources().getDimension(R.dimen.movie_margin);
            layoutParams.bottomMargin = (int) getResources().getDimension(R.dimen.movie_margin);
            layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.movie_margin);
            setLayoutParams(layoutParams);

            requestLayout();
        }
    }
}

