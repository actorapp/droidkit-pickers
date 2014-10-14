package com.droidkit.picker.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by kiolt_000 on 07/10/2014.
 */
public class MediumTextView extends TextView {
    public MediumTextView(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        this.setTypeface(type);
    }

    public MediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        this.setTypeface(type);
    }

    public MediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        this.setTypeface(type);
    }
}
