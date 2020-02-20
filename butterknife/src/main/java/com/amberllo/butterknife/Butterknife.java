package com.amberllo.butterknife;

import android.app.Activity;

import com.amberllo.butterknife_annotation.ViewBinder;

import java.util.LinkedHashMap;
import java.util.Map;

public class Butterknife {

    static Map<String, ViewBinder> BINDERS = new LinkedHashMap<>();

    public static void bind(Activity target){
        String viewBinderName = target.getClass().getName() +"$$ViewBinder";
        ViewBinder binder = BINDERS.get(viewBinderName);
        if(binder == null){
            try {
                Class clz =  Class.forName(viewBinderName);
                binder = (ViewBinder) clz.newInstance();
                BINDERS.put(viewBinderName, binder);
            }catch (Exception e){

            }

        }
        binder.bind(target);
    }
}
