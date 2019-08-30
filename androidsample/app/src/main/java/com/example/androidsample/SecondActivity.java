package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 나한테 전달된 intent를 가져와야한다. (intent.putExtra("sendMsg",et.getText());)
        Intent i = getIntent();

        // 나한테 전달된 모든 데이터를 땡기고 KEY값을 주고 해당KEY에 대한 데이터를 뽑는다.
        // 오브젝트 형태로 오기때문에 다운케스팅 해줘야한다.
        String msg = (String)i.getExtras().get("sendMsg");

        // text view에 세팅
        TextView tv = (TextView)findViewById(R.id.messageTv);
        tv.setText(msg);

        // Activity Close
        Button closeBtn = (Button)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Activity가 가지고 있는 FINISH method를 호출
                finish();
            }
        });

    }
}
