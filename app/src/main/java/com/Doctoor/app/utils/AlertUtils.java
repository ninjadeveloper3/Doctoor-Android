package com.Doctoor.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.Doctoor.app.ui.interfaces.SnackbarCallBack;


/**
 * Utilities functions for android alert and snack bar showing
 */
public class AlertUtils {

    private static final String TAG = "AlertUtils";

    private static String cActionOK = "OK";
    private static String cActionCancel = "Cancel";

    public static void initActionText(@NonNull String ok, @NonNull String cancel) {
        cActionOK = ok;
        cActionCancel = cancel;
    }

    public static void showConfirmDialog(Context context, String message, DialogInterface.OnClickListener positiveListener) {
        showConfirmDialog(context, null, message, positiveListener, null);
    }

    public static void showConfirmDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveListener) {
        showConfirmDialog(context, title, message, positiveListener, null);
    }

    public static void showConfirmDialog(Context context, String message, DialogInterface.OnClickListener positiveListener,
                                         DialogInterface.OnClickListener negativeListener) {
        showConfirmDialog(context, null, message, positiveListener, negativeListener);
    }

    public static void showConfirmDialog(Context context, @Nullable String title, String message,
                                         DialogInterface.OnClickListener positiveListener,
                                         @Nullable DialogInterface.OnClickListener negativeListener) {
        showAlertDialog(context, title, message, cActionOK, cActionCancel,
                positiveListener, negativeListener);
    }

    public static void showAlertDialog(Context context, String message) {
        showAlertDialog(context, null, message, null);
    }

    public static void showAlertDialog(Context context, String title, String message) {
        showAlertDialog(context, title, message, null);
    }

    public static void showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        showAlertDialog(context, title, message, cActionOK, listener);
    }

    public static void showAlertDialog(Context context, String message, DialogInterface.OnClickListener listener) {
        createAlertDialogBuilderInternal(context, message, cActionOK, listener)
                .show();
    }

    public static void showAlertDialog(Context context, @Nullable String title, String message,
                                       String positive, DialogInterface.OnClickListener listener) {

        AlertDialog.Builder builder = createAlertDialogBuilderInternal(context, message, positive, listener);
        if (title != null) {
            builder.setTitle(title);
        }
        builder.show();
    }

    public static void showAlertDialog(Context context, @Nullable String title, @NonNull String message,
                                       @NonNull String positive,
                                       @Nullable String negative,
                                       DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = createAlertDialogBuilderInternal(context, message, positive, positiveListener);
        if (title != null) {
            builder.setTitle(title);
        }
        if (negative != null) {
            builder.setNegativeButton(negative, negativeListener);
        }
        builder.show();
    }

    private static AlertDialog.Builder createAlertDialogBuilderInternal(Context context,
                                                                        @NonNull String message,
                                                                        @NonNull String positiveButton,
                                                                        DialogInterface.OnClickListener positiveListener) {
        return new AlertDialog.Builder(context).setMessage(message)
                .setPositiveButton(positiveButton, positiveListener)
                .setCancelable(false);
    }

    public static void showToastLongMessage(Context context, String message) {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastShortMessage(Context context, String message) {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void showSnackBarInternal(View layout, String message,
                                             @Nullable String actionText,
                                             @Nullable View.OnClickListener actionClick,
                                             int duration, SnackbarCallBack snackbarCallBack) {
        if (layout != null && !TextUtils.isEmpty(message)) {
            KeyboardKt.hideSoftKeyboard(layout.getContext());
            Snackbar snackbar = Snackbar.make(layout, message, duration);
            if (actionText != null && actionClick != null) {
                snackbar.setAction(actionText, actionClick);
                snackbar.setActionTextColor(Color.WHITE);

                snackbar.addCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        if (snackbarCallBack != null && snackbar != null) {
                            snackbarCallBack.onShown(snackbar);
                        }
                    }
                });
            }
            snackbar.show();
        }
    }

    public static void showSnackBarLongMessage(View layout, String message, String actionText, View.OnClickListener actionClick) {
        showSnackBarInternal(layout, message, actionText, actionClick, Snackbar.LENGTH_LONG, null);
    }

    public static void showSnackBarLongMessage(View layout, String message, String actionText, View.OnClickListener actionClick, SnackbarCallBack snackbarCallBack) {
        showSnackBarInternal(layout, message, actionText, actionClick, Snackbar.LENGTH_LONG, snackbarCallBack);
    }

    public static void showSnackBarShortMessage(View layout, String message, String actionText, View.OnClickListener actionClick) {
        showSnackBarInternal(layout, message, actionText, actionClick, Snackbar.LENGTH_SHORT, null);
    }

    public static void showSnackBarLongMessage(View layout, String message) {
        showSnackBarInternal(layout, message, cActionOK, null, Snackbar.LENGTH_LONG, null);
    }

    public static void showSnackBarShortMessage(View layout, String message) {
        showSnackBarInternal(layout, message, cActionOK, null, Snackbar.LENGTH_SHORT, null);
    }
}