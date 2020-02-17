package com.amberllo.butterknife;

import android.app.Activity;

import com.amberllo.butterknife.butterknife.ViewBinder;

public class MainActivity$$ViewBinder implements ViewBinder {
    @Override
    public void bind(Activity source) {
        MainActivity activity = (MainActivity) source;
        activity.textView = activity.findViewById(R.id.textView);
    }
}
