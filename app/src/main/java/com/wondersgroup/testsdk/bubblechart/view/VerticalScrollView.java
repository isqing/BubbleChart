package com.wondersgroup.testsdk.bubblechart.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by liyaqing on 2017/5/11.
 */

public class VerticalScrollView  extends ScrollView {

    /** 屏幕高度 */
    private int mScreenHeight;

    private boolean isOpen = false;

    private boolean isFirst = true;

    private boolean mIsContinue = true;

    private View mVwBackground;

    private ViewGroup mViewGroup;

    private ViewGroup mVwContent;

    private int mAnimationTime = 400;

    private int mAlph = 1;

    private int mMaxAlph = 70;

    public VerticalScrollView(Context context) {
        super(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.ScrollView#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isFirst) {
            mViewGroup = (ViewGroup) getChildAt(0);
            mVwBackground = mViewGroup.getChildAt(0);
            mVwContent = (ViewGroup) mViewGroup.getChildAt(1);

            mScreenHeight = getScreemH();
            mVwBackground.getLayoutParams().height = mScreenHeight;
            mVwContent.getLayoutParams().height = mVwContent.getLayoutParams().height;
            setVisible(false);
            isFirst = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.ScrollView#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.smoothScrollTo(0, mScreenHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int i = getScrollY();
                // 如果滑动距离大于屏幕的2/3，则弹出，这里可自定义
                if (i > mScreenHeight * 2 / 3) {
                    smoothScrollTo(0, mScreenHeight);
                    isOpen = true;
                } else {
                    traslateY(0, mScreenHeight);
                    isOpen = false;
                }
                return true;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 关闭
     *
     * @description
     * @date 2014-12-5
     */
    public void closeContent() {
        if (isOpen) {
            traslateY(0, mScreenHeight);
            isOpen = false;
        }
    }

    /**
     * 打开
     *
     * @description
     * @date 2014-12-5
     */
    public void openContent() {
        if (!isOpen) {
            setVisible(true);
            traslateY(mScreenHeight, 0);
            isOpen = true;
        }
    }

    /**
     * 实现滑动动画
     *
     * @description
     * @date 2014-12-5
     * @param oldt
     * @param t
     */
    public void traslateY(int oldt, int t) {

        TranslateAnimation animation = new TranslateAnimation(0, 0, oldt, t);
        animation.setDuration(mAnimationTime);

        animation.setFillAfter(true);
        mVwContent.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                mIsContinue = true;
                if (isOpen) {
                    mAlph = 1;
                }
                new Thread(mRunnable).start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 滑动结束如果满足关闭条件
                if (!isOpen) {
                    setVisible(false);
                    scrollTo(0, mScreenHeight);
                }
                if (!isOpen) {
                    mViewGroup.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
    }

    Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            while (mIsContinue) {
                try {
                    Thread.sleep(mAnimationTime / mMaxAlph);
                    if (isOpen) {
                        mAlph++;
                        if (mAlph <= mMaxAlph) {
                            mHandler.sendEmptyMessage(0);
                        } else {
                            mIsContinue = false;
                        }
                    } else {
                        mAlph--;
                        if (mAlph >= 1) {
                            mHandler.sendEmptyMessage(0);
                        } else {
                            mIsContinue = false;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mViewGroup.setBackgroundColor(Color.argb(mAlph, 0, 0, 0));
                    break;
            }
        };
    };

    /**
     * 调用该方法操作该弹出框
     *
     * @description
     * @date 2014-12-5
     */
    public void toggle() {
        if (isOpen) {
            closeContent();
        } else {
            openContent();
        }
    }

    /** 弹出框是否弹出 */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     *
     * @description
     * @date 2014-12-5
     * @param visible
     */
    public void setVisible(boolean visible) {
        if (visible) {
            mViewGroup.setVisibility(VISIBLE);
            this.setVisibility(VISIBLE);
        } else {
            mViewGroup.setVisibility(GONE);
            this.setVisibility(GONE);
        }

    }

    /**
     * 获得屏幕高度
     *
     * @return int
     */
    @SuppressWarnings("deprecation")
    private int getScreemH() {
        WindowManager wm = ((Activity) getContext()).getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }}

