package com.covisint.papi.sample.android.openregistration.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Nitin.Khobragade on 2/10/2015.
 */
public class ProgressDisplay {
    private View mSubmissionFormView;
    private View mProgressView;
    private WeakReference<Context> mContext;

    public ProgressDisplay(Context mContext, View mSubmissionFormView, View mProgressView) {
        this.mContext = new WeakReference<Context>(mContext);
        this.mSubmissionFormView = mSubmissionFormView;
        this.mProgressView = mProgressView;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = mContext.get().getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSubmissionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSubmissionFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSubmissionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSubmissionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
