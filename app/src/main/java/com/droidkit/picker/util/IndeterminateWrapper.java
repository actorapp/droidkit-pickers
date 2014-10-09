package com.droidkit.picker.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.droidkit.file.R;
import com.droidkit.progress.IndeterminateView;

/**
 * Created by kiolt_000 on 09/10/2014.
 */
public class IndeterminateWrapper {


    IndeterminateView indeterminateView;


    public IndeterminateWrapper(Activity activity) {
        Context context = activity;
        View decorView = activity.getWindow().getDecorView();
        indeterminateView = (IndeterminateView) LayoutInflater.from(context).inflate(R.layout.progress, null, false);
        indeterminateView.setVisibility(View.GONE);

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
        f.addView(indeterminateView, params);
    }

    public void show() {
        indeterminateView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        indeterminateView.setVisibility(View.GONE);
    }
}
