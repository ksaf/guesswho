package com.velen.guesswho.viewResizer;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

public class ViewResizer {

    private final static double timesBigger = 2.0;

    public final static double TOP_CHAR_WIDTH = 0.07;
    public final static double TOP_CHAR_HEIGHT = TOP_CHAR_WIDTH * timesBigger;
    public final static double MID_CHAR_WIDTH = 0.08;
    public final static double MID_CHAR_HEIGHT = MID_CHAR_WIDTH * timesBigger;
    public final static double BOT_CHAR_WIDTH = 0.09;
    public final static double BOT_CHAR_HEIGHT = BOT_CHAR_WIDTH * timesBigger;

    public final static double SELECTED_CHAR_WIDTH = 0.13;
    public final static double SELECTED_CHAR_HEIGHT = SELECTED_CHAR_WIDTH * timesBigger;

    public final static double TOP_MINIATURE_CHAR_WIDTH = 0.024;
    public final static double TOP_MINIATURE_CHAR_HEIGHT = TOP_MINIATURE_CHAR_WIDTH * timesBigger;
    public final static double MID_MINIATURE_CHAR_WIDTH = 0.028;
    public final static double MID_MINIATURE_CHAR_HEIGHT = MID_MINIATURE_CHAR_WIDTH * timesBigger;
    public final static double BOT_MINIATURE_CHAR_WIDTH = 0.032;
    public final static double BOT_MINIATURE_CHAR_HEIGHT = BOT_MINIATURE_CHAR_WIDTH * timesBigger;


    public static void resizeToDeviceDimensions(AppCompatActivity activity, View view, double heightPercentage, double widthPercentage ) {
        view.getLayoutParams().height = getHeightInPixelsForPercentage(activity, heightPercentage);
        view.getLayoutParams().width = getWidthInPixelsForPercentage(activity, widthPercentage);
    }

    public static void resizeToDeviceDimensions(AppCompatActivity activity, View view, double heightPercentage ) {
        view.getLayoutParams().height = getHeightInPixelsForPercentage(activity, heightPercentage);
    }

    public static int getWidthInPixelsForPercentage(AppCompatActivity activity, double widthPercentage) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        return (int)(screenWidth* widthPercentage);
    }

    public static int getHeightInPixelsForPercentage(AppCompatActivity activity, double heightPercentage) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        return (int)(screenHeight* heightPercentage);
    }

    public static double getHeightPercentageOfScreenForPixels(AppCompatActivity activity, int pixels) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        return (double)pixels / (double)screenHeight;
    }

    public static double getWidthPercentageOfScreenForPixels(AppCompatActivity activity, int pixels) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        return (double)pixels / (double)screenWidth;
    }

    public static int dpToPx(AppCompatActivity activity, int dp) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
