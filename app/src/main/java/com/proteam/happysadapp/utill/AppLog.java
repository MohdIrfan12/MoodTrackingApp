package com.proteam.happysadapp.utill;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.proteam.happysadapp.R;
import com.tribe.fitness.base.view.BaseView;
import io.github.inflationx.calligraphy3.BuildConfig;

/**
 * API for sending log output.
 * <p>
 * <p>Generally, use the Log.v() Log.d() Log.i() Log.w() and Log.e()
 * methods.
 * <p>
 * <p>The order in terms of verbosity, from least to most is
 * ERROR, WARN, INFO, DEBUG, VERBOSE.  Verbose should never be compiled
 * into an application except during development.  Debug logs are compiled
 * in but stripped at runtime.  Error, warning and info logs are always kept.
 * <p>
 * <p><b>Tip:</b> A good convention is to declare a <code>TAG</code> constant
 * in your class:
 * <p>
 * <pre>private static final String TAG = "MyActivity";</pre>
 * <p>
 * and use that in subsequent calls to the log methods.
 * </p>
 *
 * <p><b>Tip:</b> Don't forget that when you make a call like
 * <pre>Log.v(TAG, "index=" + i);</pre>
 * that when you're building the string to pass into Log.d, the compiler uses a
 * StringBuilder and at least three allocations occur: the StringBuilder
 * itself, the buffer, and the String object.  Realistically, there is also
 * another buffer allocation and copy, and even more pressure on the gc.
 * That means that if your log message is filtered out, you might be doing
 * significant work and incurring significant overhead.
 */
public final class AppLog {
    private static final boolean PRINT = BuildConfig.DEBUG;

    /**
     * Empty Constructor
     * not called
     */
    private AppLog() {
    }

    /**
     * Send a INFO log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *              the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void i(final String tag, final String message) {
        if (PRINT) {
            android.util.Log.i(tag, message);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *               the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void d(final String tag, final String message) {
        if (PRINT) {
            android.util.Log.d(tag, message);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *              the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void e(final String tag, final String message) {
        if (PRINT) {
            android.util.Log.e(tag, message);
        }
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *               the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void v(final String tag, final String message) {
        if (PRINT) {
            android.util.Log.v(tag, message);
        }
    }

    /**
     * Send a WARN log message.
     *
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *              the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void w(final String tag, final String message) {
        if (PRINT) {
            android.util.Log.w(tag, message);
        }
    }

    /**
     * Show snack bar.
     *
     * @param mContext the m context
     * @param message  the message
     */
    public static Snackbar showSnackBar(final Context mContext, final String message) {
        final View view = ((ViewGroup) (((FragmentActivity) mContext).findViewById(android.R.id.content))).getChildAt(0);
        Snackbar mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);

        ViewGroup group = (ViewGroup) mSnackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        //mSnackbar.setActionTextColor(Color.RED);
        View mSnackbarView = mSnackbar.getView();
        TextView tv = (TextView) mSnackbarView.findViewById(R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.color_FFFFFF));
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        mSnackbar.show();
        return mSnackbar;
    }

    /**
     * Show snack bar.
     *
     * @param mContext         the m context
     * @param message          the message
     * @param mOnErrorCallback the m on error callback
     */
    public static Snackbar showSnackBar(final Context mContext, final String message, final BaseView.OnErrorCallback mOnErrorCallback) {
        final View view = ((ViewGroup) (((FragmentActivity) mContext).findViewById(android.R.id.content))).getChildAt(0);
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.text_retry, view1 -> {
                    if (mOnErrorCallback != null) {
                        mOnErrorCallback.onErrorClick();
                    }
                });
        snackbar.setActionTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        snackbar.show();
        return snackbar;
    }
}
