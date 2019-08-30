package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class TouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 문자열 만들고(마지막 요소는 떠있는 시간) , 띄우기(show)
        Toast.makeText(this,R.string.toastMsg,Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
    // 스와이프도 해보자 !!
}
