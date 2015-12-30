package com.jhson.view.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class IndicatorView extends View {

    private int count = 0;
    private Paint rectPaint = null;
    private ViewPager viewPager = null;
    private Bitmap norBitmap = null;
    private Bitmap selBitmap = null;
    private boolean isCenter = false;
    private int indicateSpace = 0;
    private ViewPager.OnPageChangeListener mOnPageChangeListener = null;

    private int mPosition = 0;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);
		setIndicateIconRes(R.drawable.indicator_pager_on, R.drawable.indicator_pager_off);


        Display dis = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        dis.getMetrics(metrics);
        indicateSpace = (int) (indicateSpace * metrics.density);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || count == 0 || norBitmap == null || selBitmap == null) {
            return;
        }
        int width = getWidth();
        float itemWidth = (norBitmap.getWidth() + indicateSpace) * count;
        float left = 0;
        if (isCenter) {
            left = (width / 2 - (itemWidth / 2));
        }
        int position = mPosition % count;
        for (int i = 0; i < count; i++) {
            if (i == position) {
                canvas.drawBitmap(selBitmap, left, 0, rectPaint);
            } else {
                canvas.drawBitmap(norBitmap, left, 0, rectPaint);
            }
            left += (norBitmap.getWidth() + indicateSpace);
        }


    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//			viewPager.getParent().requestDisallowInterceptTouchEvent(true);
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                viewPager.setCurrentItem(getPosition(mPosition), false);
//				viewPager.getParent().requestDisallowInterceptTouchEvent(false);
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

    public void setViewPager(ViewPager viewPager, boolean isCenter) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new PageListener());
//		count = viewPager.getAdapter().getCount();
        count = (viewPager.getAdapter().getCount() / 3);
        viewPager.setCurrentItem(count);
        this.isCenter = isCenter;
    }

    public void setIndicateIconRes(int selRes, int norRes) {
        selBitmap = BitmapFactory.decodeResource(getResources(), selRes);
        norBitmap = BitmapFactory.decodeResource(getResources(), norRes);
        invalidate();
    }

    public void setOnPageChangeListener(OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }
}
