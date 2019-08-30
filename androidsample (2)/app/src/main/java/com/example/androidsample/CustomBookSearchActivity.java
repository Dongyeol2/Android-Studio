package com.example.androidsample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class BookSearchRunnable implements  Runnable {
    private Handler handler;
    private String keyword;

    BookSearchRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        String url = "http://70.12.115.74:8080/bookSearch/search?search_keyword=" + keyword + "";
        try{
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlObj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input = "";
            StringBuffer sb = new StringBuffer();
            while((input = br.readLine()) != null) {
                sb.append(input);
            }
            //서버에 대한 스트림은 종료
            br.close();
            // 결과적으로 우리가 얻어낸건 JSON형태의 문자열
            // JSON문자열을 원래 객체형태로 복원
            ObjectMapper mapper = new ObjectMapper();

            BookVO[] resultArr = mapper.readValue(sb.toString(),BookVO[].class);

            //최종적으로 얻어낸 객체를 UI Thread(Main Thread, Activity Thread)
            // 에게 전달해야 한다.
            Bundle bundle = new Bundle();
            bundle.putSerializable("BOOKS", resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);

//            for(BookVO vo : resultArr) {
//                Log.i("customListView", vo.getBtitle());
//            }
//
//            Log.i("customListView", sb.toString());
        }catch (Exception e) {
            Log.i("customListView", e.toString());
        }

    }
}
public class CustomBookSearchActivity extends AppCompatActivity {
    @Override
    protected  void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_custom_book_search);

        // 필요한 reference 얻어오기
        // Button, EditText, ListView
        Button customBtn = (Button)findViewById(R.id.customBtn);
        final EditText customEt = (EditText)findViewById(R.id.customEt);
        final ListView customLv = (ListView)findViewById(R.id.customLv);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // 외부 Thread에서 Message를 받으면 호출출
                Bundle bundle = msg.getData();
                BookVO[] list = (BookVO[])bundle.getSerializable("BOOKS");

                // ListVIew에 adapter를 적용해서 ListView를 그리는 작업
                // ListView : 화면에 리스트 형태를 보여주는 widget
                // ListView에 데이터를 가져다가 실제 그려주는 작업 => adapter
                CustomListViewAdapter adapter = new CustomListViewAdapter();
                customLv.setAdapter(adapter);
                // adapter에 그려야하는 데이터를 세팅
                for(BookVO vo : list) {
                    adapter.addItem(vo);
                }
            }
        };

        customBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 검색 버튼이 클릭되면 호출된다.
                // 사용자가 입력한 keyword를 이용해서 newwork 접속을 시도
                // 외부 service web program을 호출해서 결과를 받아와야 한다.
                // Thread를 이용해서 network연결 처리를 해야한다.
                // Thread에 입력 keyword값과 habdler객체가 인자로 넘어가야 한다.
                BookSearchRunnable runnable = new BookSearchRunnable(handler, customEt.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });

    }

}
