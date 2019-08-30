package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class MyCounter1 implements Runnable {

    // 인자로 handler를 받아야함
    private Handler handler;
    // injection 해서 필드에 잡자
    MyCounter1(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);

                // UI Thread에 handler를 이용해서 message를 전달

                // Bundle( 데이터 묶음 )을 통해 보낸다
                Bundle bundle = new Bundle();

                // key ,value 쌍으로 저장할수 있음 , map이라 생각하면됨
                bundle.putString("COUNT",i + ""); // i에 공백문자 추가해서 문자열로

                // 메세지에 부착해서 bundle을 보내야 한다
                // 메세지 객체 생성
                Message msg = new Message();

                // message에 bundle 부착
                msg.setData(bundle);

                // handler 로 이렇게 만든 message를 보낸다
                handler.sendMessage(msg);

            } catch (Exception e) {

            }
        }
    }
}

public class CounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        final TextView tv1 = (TextView)findViewById(R.id.counterTv1);
        Button startBtn1 = (Button)findViewById(R.id.startBtn1);

        // 객체 만들면서 오버라이딩 해야하는 메소드 오버라이딩 해주자
        final Handler handler = new Handler() {
            // message를 받는 순간 아래 method가 호출되요 !!

            @Override
            // 외부 Thread가 보낸 message가 일로 들어오게됨
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                // message 안에 있는 bundle 꺼내자
                Bundle b = msg.getData();

                // Key 값으로 뽑아낸다
                tv1.setText(b.getString("COUNT"));
            }
        };

        startBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 버튼을 누르는 순간 handler를 thread에 넘겨줌
                MyCounter1 counter = new MyCounter1( handler );
                Thread t = new Thread(counter);
                t.start();
            }
        });
    }
}
