package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// 외부 Thread로 데이터 불러오고 handler를 통해 보내주자

class SearchTitleRunnable implements Runnable{
    private String keyword;
    private Handler handler;
    // 생성자로부터 키워드 받아서 자기 필드에 인자로 들어온걸 넣는다
    SearchTitleRunnable(Handler handler, String keyword){
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        // keyword를 이용해서 web program에 접속한 후 결과를 받아와요!
        // 결과로 받아온 JSON문자열을 이요해서 ListView에 출력해야 해요
        // 그런데 여기서는 ListView를 제어할 수 없어요
        // handler를 이용해서 UI Thread에 listview에 사용할 데이터를 넘겨요
        String url = "http://70.12.115.74:80/bookSearch/searchTitle?USER_KEYWORD= "+ keyword;

        // Network code는 예외처리가 필요!
        try {
            // url 주소 문자를 객체로 만든다
            // 그렇게 하면 connection을 열수 있다
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlObj.openConnection();
            // 위 두줄로 연결 객체를 만들었고

            // network 연결이 성공한 후 데이터를 읽어들이기 위한 데이터 연결통로
            // Stream을 생성해요
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            // 서버로 부터 읽어서 input에 넣고
            String input = "";
            StringBuffer sb = new StringBuffer();
            // null이 아닐때 까지 읽고
            while ((input = br.readLine()) != null){
                // 버퍼에 append
                sb.append(input);
            }
            // data 받아왔는지 확인해보자
            //Log.i("DATA",sb.toString());

            // 얻어온 결과 JSON 문자열을 Jackson library를 이용해서
            // Java 객체형태(String[])로 변형
            ObjectMapper mapper = new ObjectMapper();
            // Jackson library를 이용해서
            // sb.toString() 읽어서 string 배열에 대한 클래스로 지정해주면 그 형태대로 변화되서
            // resultArr 에 떨어지게 된다
            String[] resultArr = mapper.readValue(sb.toString(), String[].class);
            // bundle이라는 바구니에 담자, 나중에 꺼내기 위해 키값을 주고
            Bundle bundle = new Bundle();
            bundle.putStringArray("BOOKARRAY",resultArr);
            // handler를 통해 message를 보내는 거기 때문에 번들을 바로 못보냄
            // 메세지에 번들을 넣자
            Message msg = new Message();
            msg.setData(bundle);
            // handler로 보내자
            handler.sendMessage(msg);

        }catch (Exception e){
            // error 확인 !!!!
            Log.i("DATAError",e.toString());
        }

    }
}

public class BookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        Button searchBtn = (Button)findViewById(R.id.searchBookBtn);
        final EditText keywordEt = (EditText)findViewById(R.id.keywordEt);

        final ListView lv = (ListView)findViewById(R.id.lv);

        // web 서버에 접속해서 데이터를 받아온 후 해당 데이터를 ListView에 세팅
        // UI Thread(Activity Thread, Main Thread)에서는 Network 연결을 할 수 없어요!!


        final Handler handler = new Handler(){
            // handler에게 message가 전달되면 아래의 method가 callback되요 !!
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bundle bundle = msg.getData();
                String[] result = bundle.getStringArray("BOOKARRAY");

                // adapter라는 객체는 데이터를 가져다가 view에 그리는 역활을 담당
                ArrayAdapter adapter = new ArrayAdapter(BookSearchActivity.this,
                        android.R.layout.simple_list_item_1, result);

                // ListView에 찍자
                lv.setAdapter(adapter);

            }
        };


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 keyword를 가지고 Thread를 파생

                SearchTitleRunnable runnable = new SearchTitleRunnable(handler,keywordEt.getText().toString());

                Thread t = new Thread(runnable);

                t.start();

            }
        });
    }
}
