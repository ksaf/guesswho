package com.velen.guesswho.animations;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

public class TypeWriter extends android.support.v7.widget.AppCompatTextView {

    private CharSequence mText = "";
    private int mIndex;
    private long mDelay = 150; // in ms

    private CharSequence threePartText = "";
    private CharSequence part1;
    private CharSequence part2;
    private CharSequence part3;
    private long delay1;
    private long delay2;
    private long delay3;
    private AnimationEndListener listener;

    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();

    private Runnable characterAdder = new Runnable() {

        @Override
        public void run() {
            if(mText == null) {
                return;
            }
            setText(mText.subSequence(0, mIndex++));

            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            } else if(listener != null){
                performActionAfterTyping();
            }
        }
    };

    private Runnable characterAdderThreeParts = new Runnable() {

        @Override
        public void run() {
            String untilNow = threePartText.subSequence(0, mIndex).toString();
            setText(threePartText.subSequence(0, mIndex++));
            if(part1 == null || part2 == null || part3 == null) {
                return;
            }
            if (mIndex <= part1.length()) {
                mHandler.postDelayed(characterAdderThreeParts, delay1);
            } else if(mIndex <= ("" + part1 + part2).length()) {
                mHandler.postDelayed(characterAdderThreeParts, delay2);
            } else if(mIndex <= ("" + part1 + part2 + part3).length()) {
                mHandler.postDelayed(characterAdderThreeParts, delay3);
            } else if(listener != null){
                performActionAfterTyping();
            }
        }
    };

    public void animateText(CharSequence txt) {
        mText = mText.toString() + txt;
        mIndex = 0;

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void animateThreePartText(long initialDelay, CharSequence part1, CharSequence part2, CharSequence part3, long delay1, long delay2, long delay3) {
        this.part1 = part1;
        this.part2 = part2;
        this.part3 = part3;
        this.delay1 = delay1;
        this.delay2 = delay2;
        this.delay3 = delay3;
        threePartText = "";
        threePartText = threePartText.toString() + part1 + part2 + part3;
        mIndex = 0;

        setText("");
        mHandler.removeCallbacks(characterAdderThreeParts);
        mHandler.postDelayed(characterAdderThreeParts, initialDelay);
    }

    public void resetText() {mText = "";}

    public void cancelRemainingCallbacks() {
        mHandler.removeCallbacks(characterAdderThreeParts);
    }

    public void setCharacterDelay(long m) {
        mDelay = m;
    }

    public void setActionAfterTyping(AnimationEndListener listener) {
        this.listener = listener;
    }

    private void performActionAfterTyping() {
        listener.atAnimationEnd();
    }
}
