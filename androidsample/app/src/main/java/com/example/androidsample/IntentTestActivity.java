package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);

        Button implicitBtn = (Button)findViewById(R.id.implicitBtn);

        implicitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity를 호출
                // Implicit Intent를 이용해서 Activity를 호출
                Intent i = new Intent();
                i.setAction("MY_ACTION");
                i.addCategory("MY_CATEGORY");
                i.putExtra("DATA", "소리없는 아우성");
                startActivity(i);
            }
        });

        Button dialBtn = (Button)findViewById(R.id.dialBtn);

        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 전화걸기 Activity를 호출
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:010-2550-8696"));
                startActivity(i);
            }
        });

        Button browserBtn = (Button)findViewById(R.id.browserBtn);

        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser를 이용해서 특정 URL로 접속
                Uri uri = Uri.parse("http://www.naver.com");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        Button mapBtn = (Button)findViewById(R.id.mapBtn);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            // 지도를 표현할 activity를 실행

            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.KakaoMapActivity");
                i.setComponent(cname);
                startActivity(i);
            }

        });

        Button callBtn = (Button)findViewById(R.id.callBtn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1. 사용자의 안드로이드 버전이 6버전 보다 작은가?
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 추가적인 보안해제가 필요
                    // 현재 앱에 대해 사용자 권한 중 전화걸기 기능이 설정되어 있어야 함
                    int result = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if(result == PackageManager.PERMISSION_DENIED) {
                        // 전화걸기 기능에 대한 보안이 설정 안되어있다.
                        // 한번이라도 전화걸기에 대한 권한 설정을 거부한적 있는가?
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            // 거부한 적이 있다? 다시 dialog 띄워서 물어봄
                            AlertDialog.Builder builder = new AlertDialog.Builder(IntentTestActivity.this);
                            builder.setTitle("권한 필요");
                            builder.setMessage("전화걸기 기능 필요. 수락하시겠습니까?");
                            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                    1000);
                        }
                    } else {
                        // 전화걸기 기능에 대한 보안이 설정되어 있다.
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:01025508696"));
                        startActivity(i);

                    }
                } else {
                    // 이전 안드로이드 버전이기 때문에간단한 설정으로 바로 실행
                    // 직접 calling하는 activity 호출
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:01025508696"));
                    startActivity(i);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:01025508696"));
                startActivity(i);
            }
        }
    }
}
