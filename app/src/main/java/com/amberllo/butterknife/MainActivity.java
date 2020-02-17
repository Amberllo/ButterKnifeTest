package com.amberllo.butterknife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.amberllo.butterknife.butterknife.BindView;
import com.amberllo.butterknife.butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView.setText("1111");
    }
}
