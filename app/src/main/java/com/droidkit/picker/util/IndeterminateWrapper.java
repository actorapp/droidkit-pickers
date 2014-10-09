package com.droidkit.picker.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.droidkit.file.R;
import com.droidkit.progress.IndeterminateView;

/**
 * Created by kiolt_000 on 09/10/2014.
 */
public class IndeterminateWrapper {


    IndeterminateView mProgressBar;


    public IndeterminateWrapper(Activity activity) {
        Context context = activity;
        View decorView = activity.getWindow().getDecorView();
        mProgressBar = (IndeterminateView) LayoutInflater.from(context).inflate(R.layout.progress, null, false);


        // get the action bar layout
        int actionBarId = context.getResources().getIdentifier("action_bar_container", "id", "android");
        FrameLayout f = (FrameLayout) decorView.findViewById(actionBarId);
        if (f == null) {
            return;
            //f = (FrameLayout) decorView.findViewById(android.R.id.action_bar_container);
        }

        // add the view to that layout
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        f.addView(mProgressBar, params);
    }

    public void show() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mProgressBar.setVisibility(View.GONE);
    }
}
