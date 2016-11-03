package com.jhson.view.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;

public class IndicatorView extends View {

    private int count = 0;
    private ViewPager viewPager = null;
    private int indicateSpace = 0;
    private ViewPager.OnPageChangeListener mOnPageChangeListener = null;

    private int mPosition = 0;
    private Paint circlePaint = null;
    private int selColor = Color.BLACK;
    private int norColor = Color.GRAY;
    private int indicatorWidth = -1;
    private int radius = -1;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public IndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        norColor = context.getResources().getColor(R.color.black_40);

        indicateSpace = context.getResources().getDimensionPixelOffset(R.dimen.indicator_item_margin);
        indicatorWidth = context.getResources().getDimensionPixelOffset(R.dimen.indicator_item_width);
        radius = indicatorWidth / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = (indicatorWidth + indicateSpace) * count;
        heightMeasureSpec = indicatorWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || count == 0) {
            return;
        }
        float left = radius;
        int position = mPosition % count;
        for (int i = 0; i < count; i++) {
            if (i == position) {
                circlePaint.setColor(selColor);
                canvas.drawCircle(left, radius, radius, circlePaint);
            } else {
                circlePaint.setColor(norColor);
                canvas.drawCircle(left, radius, radius, circlePaint);
            }
            left += (indicatorWidth + indicateSpace);
        }


    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                viewPager.setCurrentItem(getPosition(mPosition), false);
            }
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageSelected(position);
            }
            mPosition = position;
            invalidate();
        }

    }

    public int getPosition(int pos) {
        int position = pos;
        if (position < count) {
            position = position + count;
        } else if (position >= count * 2) {
            position = position - count;
        }
        return position;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new PageListener());
        count = (viewPager.getAdapter().getCount() / 3);
        viewPager.setCurrentItem(count);
    }

    public void setOnPageChangeListener(OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }
}

