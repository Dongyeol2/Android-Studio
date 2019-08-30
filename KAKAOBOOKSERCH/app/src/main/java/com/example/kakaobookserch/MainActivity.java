package com.example.kakaobookserch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 상수화 시키는 이유 : event처리하는 시점에 메모리 남아있도록 하기 위해서임.
        final EditText editText = (EditText)findViewById(R.id.keywordEditText);
        Button searchBtn = (Button)this.findViewById(R.id.searchBtn);
        // anaymous inner class를 이용한 Event 처리
        // (Android의 전형적인 event처리방식)
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행
                // Activity, Service, Broadcast 어떤것을 실행하던 intent객체를 선언해줘야함
                Intent i = new Intent();
                // 명시적 Intent를 사용.
                //ComponentName 1번째 인자 : package 명, 2번째 인자 : class명
                ComponentName cname = new ComponentName("com.example.kakaobookserch",
                        "com.example.kakaobookserch.KAKAOBookSearchService");
                //intent 객체에게 해당 이름의 component 찾으라고 전달
                i.setComponent(cname);
                // putExtra를 이용해서 데이터를 다른 component로 전달할 수 있음
                i.putExtra("searchKeyword", editText.getText().toString());
                startService(i);

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("KAKAOBook", "데이터가 정상적으로 Activity에 도달");
        // intent에서 데이터 추출해서 ListView에 출력하는 작업을 진행
        // 만약 그림까지 포함할려면 추가적인 작업이 더 들어가야 한다.
        // ListView에 도서 제목만 일단 먼저 출력해 보고
        // 성공하면 CustomListView를 이용해서 이미지, 제목, 저자, 가격 등의 데이터를 출력
    }
}
