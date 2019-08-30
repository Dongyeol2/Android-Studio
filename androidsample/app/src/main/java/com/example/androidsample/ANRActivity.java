package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

class  MySum implements Runnable{
    // 생성자로 tv를 받으면 됨
    // 필드로 일단 선언하고
    private TextView tv;
    // 생성자를 선언해서 들고오자
    MySum(TextView tv){
        this.tv = tv;
    }
    @Override
    public void run() {
        // Thread가 실행이 되면 수행되는 코드를 여기에 작성
        long sum = 0;
        for (long i=0; i<100000000L; i++){
            sum += i;
        }
        tv.setText("총합은 :" +sum);
    }
}

public class ANRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        //textView 들고오기
        final TextView tv = findViewById(R.id.countTv);

        // Button 들고오기
        Button countBtn = findViewById(R.id.countBtn);
        Button toastBtn = findViewById(R.id.toastBtn);

        // countBtn 은 연산시작이라는 버튼을 눌렀을때 for문을 계속 돌것이다
        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 클릭을 하면 Thread 파생시켜야 해요 !!
                // Thread 생성
                // Thread 만들자, runnable interface를 통해 만든 객체를 인자로 주어서
                MySum mySum = new MySum(tv);
                Thread t = new Thread(mySum);  // thread 생성
                t.start();  // start는 non-blocking method , 메소드 끝나지 않아도 계속 내려가서 수행된다.

            }
        });

        // Toast 버튼 누르면 toast 메세지 뙇 띄움
        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ANRActivity.this,
                                 "Toast가 실행되요!",
                                Toast.LENGTH_SHORT).show();
            }
        });


    }
}
