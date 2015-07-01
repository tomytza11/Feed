package com.feed.views;

import com.example.feed.R;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.feed.R;

public class CustomTextViewTitle extends TextView {

    public CustomTextViewTitle(Context context) {
        super(context);

        applyCustomFont();
    }

    public CustomTextViewTitle(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont();
    }

    public CustomTextViewTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont();
    }

    private void applyCustomFont() {
        setBackgroundColor(getResources().getColor(R.color.transparent));
        setTextColor(getResources().getColor(R.color.blue));
        setTextSize(20);
        setTypeface(Typeface.create("sans-serif", Typeface.BOLD_ITALIC));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setLayoutParamaters();
    }

    private void setLayoutParamaters() {
        if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            layoutParams.topMargin = (int) getResources().getDimension(R.dimen.movie_margin_top);
            layoutParams.bottomMargin = (int) getResources().getDimension(R.dimen.movie_margin_bottom);
            layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.movie_margin);
            setLayoutParams(layoutParams);

            requestLayout();
        }
    }
}
