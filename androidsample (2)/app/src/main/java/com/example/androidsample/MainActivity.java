package com.example.androidsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btn1 : Event Source
        Button btn1 = (Button)findViewById(R.id.btn1);

        // 익명 inner 클래스
        // 추상메소드를 오버라이딩 하고
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.LinearLayoutExampleActivity");
                i.setComponent(cname);
                // i 의 정보를 이용해서 새로운 Activity 실행
                startActivity(i);
            }
        });

        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.ChattingActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button btn3 = (Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.ImageActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });


        Button btn4 = (Button)findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.TouchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button btn5 = (Button)findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final EditText et = new EditText(MainActivity.this);

                // AlertDialog를 생성해 보아요 !
                // 객체 생성
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                // 타이틀
                dialog.setTitle("Activity에 데이터 전잘");
                // 내용사용
                dialog.setMessage("전달할 내용을 입력하세요!");
                // 입력상자
                dialog.setView(et); // 입력상자를 dialog view로 설정해 땋 나타나게됨
                // 입력 (글자도 바꿀수 있음), 눌렀을때 그 이벤트를 처리하는 listener객체가 나와야함
                dialog.setPositiveButton("Activity 호출", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 다른 Activity를 호출하는 code
                        Intent intent = new Intent();
                        ComponentName cname = new ComponentName("com.example.androidsample",
                                "com.example.androidsample.SecondActivity");
                        intent.setComponent(cname);
                        // data 전달
                        // key,value형태로 key값을 주고 , 전달하고자 하는것을 뒷쪽에 적어준다
                        intent.putExtra("sendMsg",et.getText().toString());
                        // key값을 다르게해서 여러개의 데이터를 보낼수도 있다.
                        intent.putExtra("anotherMsg","다른데이터!!");
                        // 보내기
                        startActivity(intent);
                    }
                });
                // 취소
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 취소버튼 눌렀을때  수행할 code 작성
                    }
                });
                dialog.show();
            }
        });

        Button btn6 = (Button)findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.DataFromActivity");
                i.setComponent(cname);
                // 실행하는 activity로부터 data를 받아와야 한다.
                // 두번째 인자로 하나의 정수값을 줘야한다. (누가 나한테 무슨 목적으로 보냈는지 판단하기 위해 )
                startActivityForResult(i, 3000);
            }
        });

        Button btn7 = (Button)findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.ANRActivity");
                i.setComponent(cname);

                startActivity(i);
            }
        });

        Button btn8 = (Button)findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.NoCounterActivity");
                i.setComponent(cname);

                startActivity(i);
            }
        });

        Button btn9 = (Button)findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.CounterActivity");
                i.setComponent(cname);

                startActivity(i);
            }
        });

        Button btn10 = (Button)findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.BookSearchActivity");
                i.setComponent(cname);

                startActivity(i);
            }
        });

        Button btn11 = (Button)findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.CustomBookSearchActivity");
                i.setComponent(cname);

                startActivity(i);
            }
        });
    }

    // onCreate 밖에서 onActivityResult 이용
    // DataFromActivity가 종료되면 자동으로 호출된다

    // 첫번째 인자 : requestCode
    // 두번째 인자 : resultCode
    // 세번째 인자 : Intent data

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // data를 받았다면
        if(requestCode == 3000 && resultCode == 5000) {
            // 메세지 저장
            String result = data.getExtras().getString("DATA");
            // 토스트 메세지로
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }
}
