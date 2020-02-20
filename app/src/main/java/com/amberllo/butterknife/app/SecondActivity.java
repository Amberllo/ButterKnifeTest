package com.amberllo.butterknife.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.amberllo.butterknife.Butterknife;
import com.amberllo.butterknife_annotation.BindView;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.textview2)
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Butterknife.bind(this);
    }
}
