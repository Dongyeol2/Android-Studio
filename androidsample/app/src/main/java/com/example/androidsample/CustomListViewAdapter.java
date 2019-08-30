package com.example.androidsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//ListView안의 내용을 그리는 역할을 하는 adapter
public class CustomListViewAdapter extends BaseAdapter {

    private List<BookVO> list = new ArrayList<BookVO>();
    //반드시 overriding을 해야하는 method가 존재

    public void addItem(BookVO vo) {
        list.add(vo);
    }
    @Override
    public int getCount() {
        // 총 몇개의 component를 그려야 하는지를 return
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        // 몇번째 데이터를 화면에 출력할지를 결정
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        //출력할  View 객체를 생성
        if(view == null) {
            LayoutInflater inflater =
                    (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 생성할 View 객체에 XML Layout을 설정
            view = inflater.inflate(R.layout.listview_item,
                    viewGroup, false);
        }
        // 출력할 View Component의 객체 생성
        ImageView Iv = (ImageView)view.findViewById(R.id.customIv);
        TextView tv1 = (TextView)view.findViewById(R.id.customTv1);
        TextView tv2 = (TextView)view.findViewById(R.id.customTv2);

        BookVO vo = list.get(i); // 화면에 출력할 데이터를 가져옴

        Iv.setImageDrawable(vo.getDrawable());
        tv1.setText(vo.getBtitle());
        tv2.setText(vo.getBauthor());

        return view;
    }
}
