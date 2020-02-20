package com.amberllo.butterknife;

import android.app.Activity;

import com.amberllo.butterknife_annotation.ViewBinder;

import java.util.LinkedHashMap;
import java.util.Map;

public class Butterknife {

    static Map<String, ? extends ViewBinder> BINDERS = new LinkedHashMap<>();

    public static void bind(Activity target){

    }
}
