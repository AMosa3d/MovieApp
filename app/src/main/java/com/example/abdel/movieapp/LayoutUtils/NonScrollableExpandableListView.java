package com.example.abdel.movieapp.LayoutUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * Created by abdel on 10/22/2017.
 */

public class NonScrollableExpandableListView extends ExpandableListView {
    public NonScrollableExpandableListView(Context context) {
        super(context);
    }

    public NonScrollableExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollableExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
