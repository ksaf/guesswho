package com.velen.guesswho.playScreen;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.velen.guesswho.R;
import com.velen.guesswho.assetLoader.AssetLoader;

import static com.velen.guesswho.gameStrings.GameStringLiterals.*;
import static com.velen.guesswho.viewResizer.ViewResizer.BOT_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.BOT_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.BOT_MINIATURE_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.BOT_MINIATURE_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.MID_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.MID_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.MID_MINIATURE_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.MID_MINIATURE_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.TOP_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.TOP_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.TOP_MINIATURE_CHAR_HEIGHT;
import static com.velen.guesswho.viewResizer.ViewResizer.TOP_MINIATURE_CHAR_WIDTH;
import static com.velen.guesswho.viewResizer.ViewResizer.dpToPx;
import static com.velen.guesswho.viewResizer.ViewResizer.getHeightPercentageOfScreenForPixels;
import static com.velen.guesswho.viewResizer.ViewResizer.getWidthInPixelsForPercentage;
import static com.velen.guesswho.viewResizer.ViewResizer.getWidthPercentageOfScreenForPixels;
import static com.velen.guesswho.viewResizer.ViewResizer.resizeToDeviceDimensions;


public class GridContainerManager {

    private AppCompatActivity activity;

    public GridContainerManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void createGridContainer() {
        View gridContainer = activity.findViewById(R.id.gridContainer);
        gridContainer.setBackgroundDrawable(AssetLoader.loadDrawableFromAssets(activity, CHARACTER_GROUPS_PATH + BOARD_FILE + PNG_EXTENSION));
        resizeRowGuidelines(gridContainer, getNormalGridTopConstraintPercentage(), getNormalGridMidConstraintPercentage());
        resizeGridContainer(gridContainer);
    }

    public void createMiniatureGridContainer() {
        View gridContainer = activity.findViewById(R.id.miniatureGrid);
        gridContainer.setBackgroundDrawable(AssetLoader.loadDrawableFromAssets(activity, CHARACTER_GROUPS_PATH + BOARD_FILE + PNG_EXTENSION));
        resizeRowGuidelines(gridContainer, getMiniGridTopConstraintPercentage(), getMiniGridMidConstraintPercentage());
        resizeMiniatureGridContainer(gridContainer);
    }

    private void resizeRowGuidelines(View gridContainer, float topPercentage, float midPercentage) {
        Guideline underTop = (Guideline) gridContainer.findViewById(R.id.guideline13);
        ConstraintLayout.LayoutParams lpTop = (ConstraintLayout.LayoutParams) underTop.getLayoutParams();
        lpTop.guidePercent = topPercentage;
        underTop.setLayoutParams(lpTop);
        Guideline underMid = (Guideline) gridContainer.findViewById(R.id.guideline14);
        ConstraintLayout.LayoutParams lpMid = (ConstraintLayout.LayoutParams) underMid.getLayoutParams();
        lpMid.guidePercent = midPercentage;
        underMid.setLayoutParams(lpMid);
    }

    private void resizeGridContainer(View gridContainer) {
        double gridHeight = getNormalGridContainerHeight();
        int extraPixelsForSpaceBetweenChars = dpToPx(activity, 5 * 7);
        double gridWidth = (BOT_CHAR_WIDTH *8) + getWidthPercentageOfScreenForPixels(activity, extraPixelsForSpaceBetweenChars);
        resizeToDeviceDimensions(activity, gridContainer, gridHeight, gridWidth);
    }

    private void resizeMiniatureGridContainer(View gridContainer) {
        double gridHeight = getMiniGridContainerHeight();
        int extraPixelsForSpaceBetweenChars = dpToPx(activity, (2 * 7) + 16 + 4); //+4 is
        double gridWidth = (BOT_MINIATURE_CHAR_WIDTH * 8) + getWidthPercentageOfScreenForPixels(activity, extraPixelsForSpaceBetweenChars);
        resizeToDeviceDimensions(activity, gridContainer, gridHeight, gridWidth);
    }

    private double getNormalGridContainerHeight() {
        int extraDpForMargin = dpToPx(activity, 24);
        return TOP_CHAR_HEIGHT + MID_CHAR_HEIGHT + BOT_CHAR_HEIGHT + getHeightPercentageOfScreenForPixels(activity, extraDpForMargin);
    }

    private float getNormalGridTopConstraintPercentage() {
        int extraDpForMargin = dpToPx(activity, 8);
        return ((float) (TOP_CHAR_HEIGHT + getHeightPercentageOfScreenForPixels(activity, extraDpForMargin)) /
                (float)getNormalGridContainerHeight());
    }

    private float getNormalGridMidConstraintPercentage() {
        int extraDpForMargin = dpToPx(activity, 8);
        return 1 - (float)(BOT_CHAR_HEIGHT + getHeightPercentageOfScreenForPixels(activity, extraDpForMargin))/ (float)getNormalGridContainerHeight();
    }

    private float getMiniGridTopConstraintPercentage() {
        int extraDpForMargin = dpToPx(activity, 8);
        return (float) (TOP_MINIATURE_CHAR_HEIGHT + getHeightPercentageOfScreenForPixels(activity, extraDpForMargin)) /
                (float)getMiniGridContainerHeight();
    }

    private float getMiniGridMidConstraintPercentage() {
        int extraDpForMargin = dpToPx(activity, 8);
        return getMiniGridTopConstraintPercentage() + (float) (MID_MINIATURE_CHAR_HEIGHT + getHeightPercentageOfScreenForPixels(activity, extraDpForMargin)) /
                (float)getMiniGridContainerHeight();
    }

    private double getMiniGridContainerHeight() {
        int extraDpForMargin = dpToPx(activity, 24);
        return TOP_MINIATURE_CHAR_HEIGHT + MID_MINIATURE_CHAR_HEIGHT + BOT_MINIATURE_CHAR_HEIGHT + getHeightPercentageOfScreenForPixels(activity, extraDpForMargin);
    }

    public int getResizedWidthForRow(int row, boolean mini) {
        double width;
        switch (row) {
            case 1:
                width = mini ? TOP_MINIATURE_CHAR_WIDTH : TOP_CHAR_WIDTH;
                break;
            case 2:
                width = mini ? MID_MINIATURE_CHAR_WIDTH : MID_CHAR_WIDTH;
                break;
            case 3:
                width = mini ? BOT_MINIATURE_CHAR_WIDTH : BOT_CHAR_WIDTH;
                break;
            default:
                width = 0;
        }
        return getWidthInPixelsForPercentage(activity, width);
    }

}
