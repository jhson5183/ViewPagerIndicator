package com.jhson.view.app;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

public class AutoRollingManager {

    private RollingAdapter mAdapter = null;
    private ViewPager mViewPager = null;
    private IndicatorView mIndicatorView = null;
    private boolean isRollingStart = false;
    private long mRollingTime = 5000;

    public AutoRollingManager(ViewPager viewPager, RollingAdapter adapter, IndicatorView indicatorView){
        mAdapter = adapter;
        mViewPager = viewPager;
        mIndicatorView = indicatorView;
    }

    public void onRollingStart(){
        if(mBannerRollingHandler != null){
            isRollingStart = true;
            mBannerRollingHandler.removeMessages(2);
            mBannerRollingHandler.removeMessages(3);
            long time = mRollingTime;
            Message msg = Message.obtain();
            msg.what = 2;
            mBannerRollingHandler.sendMessageDelayed(msg, time);
        }
    }

    public void onRollingStop(){
        isRollingStart = false;
    }

    public void onRollingDestroy(){
        isRollingStart = false;
    }

    private Handler mBannerRollingHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(isRollingStart && mAdapter != null && mAdapter.getCount() > 3 && msg != null){
                if(msg.what == 2){
                    int position = mViewPager.getCurrentItem() + 1;
                    int pos = mIndicatorView.getPosition(position);
                    if(position > pos){
                        mViewPager.setCurrentItem(position);
                        Message message = Message.obtain();
                        message.what = 3;
                        message.arg1 = pos;
                        mBannerRollingHandler.sendMessageDelayed(message, 300);
                    }else{
                        mViewPager.setCurrentItem(pos);
                    }
                    onRollingStart();
                }else if(msg.what == 3){
                    int pos = msg.arg1;
                    mViewPager.setCurrentItem(pos, false);
                }

            }
        }
    };

    public void setRollingTime(long rollingTime) {
        this.mRollingTime = mRollingTime;
    }
}
