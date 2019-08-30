package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Button imgBtn = (Button)findViewById(R.id.imgBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv = (ImageView)findViewById(R.id.iv);
                iv.setImageResource(R.drawable.hydrangeas); // 내가가진 imageResource로 이미지를 세팅
            }
        });
    }
}
