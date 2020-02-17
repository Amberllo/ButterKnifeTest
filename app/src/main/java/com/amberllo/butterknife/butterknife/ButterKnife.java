package com.amberllo.butterknife.butterknife;

import android.app.Activity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ButterKnife {
    static Map<String, ViewBinder> BINDERS = new LinkedHashMap<>();
    public static ViewBinder bind(Activity target){

        Class clz = target.getClass();

        String bindName = clz.getName()+"$$ViewBinder";

        ViewBinder binder = null;
        if(BINDERS.containsKey(bindName)){
            binder = BINDERS.get(bindName);
        }else{
            try {
                Class binderClass = Class.forName(bindName);
                binder = (ViewBinder) binderClass.newInstance();
                BINDERS.put(bindName, binder);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        if(binder!=null){
            binder.bind(target);
            return binder;
        }else{
            return EMPTY;
        }

    }

    static ViewBinder EMPTY = new ViewBinder() {
        @Override
        public void bind(Activity target) {

        }
    };
}
