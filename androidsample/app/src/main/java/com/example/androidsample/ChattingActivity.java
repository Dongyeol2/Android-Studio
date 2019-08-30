package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChattingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        // 만들어지는 시점, 사라지는 시점과 사용하는 시점이 달라서 문제가 생김
        // stack에서 날라가서 찾지 못함 -> 지역변수가 아닌 클래스의 맴버로 만들면됨
        // but 맴버로 올리면 힙에서 계속 가지고 있어서 메모리를 잡아먹음
        // => 상수처리를 할것임 ( final ) : method가 끝나도 메모리가 날라가지 않음
        final TextView tv = (TextView)findViewById(R.id.tv);
        final TextView uId = (TextView)findViewById(R.id.userId);
        final EditText et = (EditText)findViewById(R.id.et);
        Button sendBtn = (Button)findViewById(R.id.sendBtn);

        // scroll 가능하게 해준다 , 자동 scroll은 안됨
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uid 가져오고 입력한 문자 가져오고 문자열로 연결해서 tv로 append
                tv.append(uId.getText() + ">>" + et.getText() + "\n");
                // scroll 해야하는지 판단해서
                // 찾아보면 나온다 자동스크롤 공식 
                //tv.scrollTo(0,100);
            }
        });
    }
}
