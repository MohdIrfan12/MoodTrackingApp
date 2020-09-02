package com.proteam.happysadapp.base.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.proteam.happysadapp.R;


/**
 * Progress dialog
 */
public class ProgressDialog {

    /**
     * Instantiates a new Progress dialog.
     */
    public ProgressDialog() {
    }

    /**
     * Show progress dialog.
     *
     * @param context the context
     * @return the dialog
     */
    public Dialog show(final Context context) {
        return show(context, null);
    }

//    /**
//     * Show progress dialog.
//     *
//     * @param context the context
//     * @param message the message
//     * @return the dialog
//     */
//    public Dialog show(final Context context, final int message) {
//        return show(context, context.getString(message));
//    }


    /**
     * Show progress dialog.
     *
     * @param context the context
     * @param message the message
     * @return the dialog
     */
    public Dialog show(final Context context, final String message) {
        Dialog progressDialog = new Dialog(context, R.style.dialog_theme);
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * Show progress dialog.
     *
     * @param context       the context
     * @param drawableResId the message
     * @return the dialog
     */
    @SuppressLint("ResourceType")
    public Dialog show(final Context context, @DrawableRes final int drawableResId) {
        Dialog progressDialog = new Dialog(context, R.style.dialog_theme);
        progressDialog.setContentView(R.layout.dialog_progress);
        if (drawableResId > 0) {
            ImageView imageView = progressDialog.findViewById(R.id.ivExpression);
            Glide.with(context).load(drawableResId).into(imageView);
            imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }
}
