package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class MyCounter implements Runnable {
    private TextView tv;

    MyCounter(TextView tv) {
        this.tv = tv;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                tv.setText("count :" + i);
            } catch (Exception e) {

            }
        }
    }
}
public class NoCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_counter);

        final TextView tv = (TextView)findViewById(R.id.counterTv);
        Button startBtn = (Button)findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyCounter counter = new MyCounter(tv);
                Thread t = new Thread(counter);
                t.start();
            }


        });
    }
}
