package com.velen.guesswho.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;

public class ViewColorAnimator {

    public static void animate(final AnimatedViewHandler handler, final View v, final int startLayout, final int endLayout, int duration) {
        ValueAnimator colorAnimator = ObjectAnimator.ofFloat(0f, 1f);
        colorAnimator.setDuration(duration);
        colorAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                v.setBackgroundResource(endLayout);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setBackgroundResource(startLayout);
                handler.onAnimationEnd();
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        colorAnimator.start();
    }

    public static void animateImageColor(final AnimatedViewHandler handler, final ImageView v) {
        final int orange = Color.parseColor("#e5bb63");

        final ValueAnimator colorAnim = ObjectAnimator.ofFloat(0f, 1f);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mul = (Float) animation.getAnimatedValue();
                int alphaOrange = adjustAlpha(orange, mul);
                v.setColorFilter(alphaOrange, PorterDuff.Mode.SRC_ATOP);
                if (mul > 0.5) {
                    v.setColorFilter(null);
                }
            }
        });
        colorAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                handler.onAnimationEnd();
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        colorAnim.setDuration(500);
        //colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        //colorAnim.setRepeatCount(-1);
        colorAnim.start();

    }

    private static int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
