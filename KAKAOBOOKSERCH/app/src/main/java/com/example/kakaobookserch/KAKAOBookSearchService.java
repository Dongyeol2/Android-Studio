package com.example.kakaobookserch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class KAKAOBookSearchService extends Service {
    // 일반적으로 inner class 형태로 thread 생성을 위한 Runnable interface를 구현한 class를 만들어준다.
    // 그래야 쉽게 access가 가능하고 프로그램을 쉽게 만들어 줄 수 있다.
    private class BookSearchRunnable implements Runnable {
        private  String keyword;

        // 기본 생성자 만들어 주어서 thread 객체에서 받아온 keyword로 네트워크 처리
        BookSearchRunnable(String keyword) {
            this.keyword = keyword;
        }

        @Override
        public void run() {
            // server program URL
            // 사용자가 입력한 query문 url 뒤쪽에 붙여준다.
            String url = "https://dapi.kakao.com/v3/search/book" +
                    "?target=title&query=" + keyword;
            String myKey = "dab33483291995cfa375e02084ca6e0c";
            try {
                // 해당 문자열을 접속가능한 URL 객체로 만들어 준다.
                URL urlObj = new URL(url);
                HttpURLConnection con = (HttpURLConnection)urlObj.openConnection();
                // request 방식을 지정
                con.setRequestMethod("GET");
                // 인증에 대한 설정을 넣어준다.
                con.setRequestProperty("Authorization", "KakaoAK " + myKey);
                // 정상적으로 설정이 되면 server로 접속 후 API 호출이 성송하고 결과를 받아올 수 있다.
                // 연결 통로(stream)을 통해서 결과를 문자열로 얻어냄.
                // 기본적인 stream은 bufferedReader 형태로 생성
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                );
                String line = null;
                StringBuffer sb = new StringBuffer();
                while((line = br.readLine()) != null )  {
                    sb.append(line);
                }
                // 데이터를 모두 읽어 들이면 통로(stream)을 닫아줌
                br.close();

                Log.i("KAKAOBook", sb.toString());
                // 데이터가 JSON형태로 정상적으로 출력되면 외부 API 호출 성공한것임.
                // jackson Library를 이용해서 JSON데이터를 처리
                // { documents : [] }
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(sb.toString(),
                        new TypeReference<Map<String, Object>>() {});

                Object obj = map.get("documents");

                // 해당 object를 다시 json 문자열 형태로 만들어준다.
                // why? 원하는 형태로 json을 handling 하기 위해서
                String resultJsonData = mapper.writeValueAsString(obj);
                Log.i("KAKAOBook", resultJsonData);
                // 결과적으로 우리가 얻은 데이터의 형태는
                // [ {책 1권의 데이터}, {책 1권의 데이터}, {책 1권의 데이터}, ... ]
                // 책 1권의 데이터를 객체화 => KakaoBookVO class 이용
                // 책 여러권의 데이터는 ArrayList로 표현
                // 책 한권의 데이터는 key와 value의 쌍으로 표현됨
                ArrayList<KAKAOBookVO> myObject = mapper.readValue(resultJsonData,
                        new TypeReference<ArrayList<KAKAOBookVO>>() {});

                for(KAKAOBookVO book : myObject) {
                    Log.i("KAKAOBook", book.getTitle());
                }
                // 정상적으로 객체화가 되었으면 intent에 해당 데이터를 붙여서
                // Activity에게 전달해야 한다.
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // 전달할 데이터를 intent에 붙인다.
                //i.putExtra("resultData", myObject);
                i.putParcelableArrayListExtra("resultData", myObject);
                // Activity에게 데이터 전달
                startActivity(i);

            } catch (Exception e) {
                Log.i("KAKAOBook", e.toString());
            }
        }
    }

    public KAKAOBookSearchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    // 서비스 객체가 만들어지는 시점에 1번 호출
    // 사용할 resource를 준비하는 과정.
    public void onCreate() {
        super.onCreate();
    }

    @Override
    // onCreate()후에 자동적으로 호출되어
    // startService()에 의해서 호출됨.
    // 실제 로직처리는 onStartCommand에서 진행
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("KAKAOBook", "onStartCommand 시작됨");
        // 전달된 키워드를 이용해서 외부 네트워크 접속을 위한
        // Thread를 하나 생성해야 함
        String keyword = intent.getExtras().getString("searchKeyword");

        // Thread를 만들기 위한 Runnable객체 생성
        BookSearchRunnable runnable = new BookSearchRunnable(keyword);
        Thread t = new Thread(runnable);
        t.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    // 서비스 객체가 메모리상에서 삭제될 때 1번 호출
    // 사용한 resource를 정리하는 과정.
    public void onDestroy() {
        super.onDestroy();
    }
}
