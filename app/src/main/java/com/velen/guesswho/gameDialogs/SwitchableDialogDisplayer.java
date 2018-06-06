package com.velen.guesswho.gameDialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

public class SwitchableDialogDisplayer {

    private android.view.ViewGroup.LayoutParams alertLayoutParams = null;
    private Dialog alertViewDialog = null;
    private LinearLayout cvPopupView;
    private Context context;

    public SwitchableDialogDisplayer(Context context) {
        this.context = context;
    }

    public void showPopupView(View popupView, boolean cancelable) {
        if (cvPopupView == null) {
            cvPopupView = new LinearLayout(context);
        }
        else
            cvPopupView.removeAllViews();

        cvPopupView.addView(popupView);
        //ViewResizer.resizeToDeviceDimensions((AppCompatActivity) context, popupView, 0.6, 0.6);

        if (alertViewDialog == null) {
            alertViewDialog = new Dialog(context);
            alertViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertViewDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    ((ViewGroup) cvPopupView.getParent()).removeView(cvPopupView);
                }
            });
            alertViewDialog.setCancelable(cancelable);
            alertViewDialog.setCanceledOnTouchOutside(cancelable);
            alertViewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        if (alertLayoutParams == null) {
            alertLayoutParams = new android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        if (!alertViewDialog.isShowing()) {
            alertViewDialog.addContentView(cvPopupView, alertLayoutParams);
            alertViewDialog.show();
        }
    }

    public View showPopupLayout(int layoutRLocation, boolean cancelable) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(layoutRLocation, null);
        showPopupView(view, cancelable);
        return view;
    }

    public Typeface getComicSans() {
        return Typeface.createFromAsset(context.getAssets(), "typefaces/comicsans.ttf");
    }

    public void closeMenu() {
        alertViewDialog.cancel();
    }
}
