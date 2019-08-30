package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;

public class KakaoMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_map);

        MapView map = new MapView(this);
        ViewGroup group = (ViewGroup)findViewById(R.id.mapll);

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.501386,127.039644);
        map.setMapCenterPoint(mapPoint,true);
        group.addView(map);
    }
}
