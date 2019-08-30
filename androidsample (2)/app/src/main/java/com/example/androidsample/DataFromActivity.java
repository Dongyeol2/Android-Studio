package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class DataFromActivity extends AppCompatActivity {

    // 선택된 과일이 뭔지 클래스의 맴버변수로 올린다
    private String selectedItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_from);

        final ArrayList<String> list = new ArrayList<String>();
        list.add("수박");
        list.add("바나나");
        list.add("딸기");
        list.add("멜론");

        // spinner를 찾아오자
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        // adapter가 필요
        // spinner와 arraylist를 결합할수 있도록
        // view와 componet를 합치기 위해서 필요하다
        // applicationcontext 가져오고, layout을 어떻게 잡을건지 style 양식이 나오고, 데이터 나온다
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, list);

        // spinner에 세팅
        spinner.setAdapter(adapter);

        // spinner에서 item을 선택하는 evnet 처리가 필요!!
        // spinner에도  listener를 붙여야함
        // itemSelected 여러개중 item을 선택했을때 event를 잡는것 !

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // int i : 몇번째가 선택되었는지 인자가 들어감
                selectedItem = (String) list.get(i);
                Log.i("selectedTest","선택된 과일 : " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button sendDataBtn = (Button) findViewById(R.id.sendMsgBtn);
        sendDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 클릭되면 현재 선택한 과일이름을 이전 Activity로 전달하고
                // 현재 Activity는 종료

                // data 전달을 위해 intent 생성
                Intent resultIntent = new Intent();

                // DATA라는 key값으로 selectedItem이 넘어감
                resultIntent.putExtra("DATA",selectedItem);

                // 결과값 세팅
                // 현재 Activity에 결과값 세팅
                setResult(5000, resultIntent);

                // 지금 사용하고 있는 Activity 종료되면서 자동적으로 결과 전달된다.
                // DataFromActivity.this.finish()
                finish();

            }
        });

    }
}
