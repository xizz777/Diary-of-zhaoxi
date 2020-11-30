package com.example.diaryofzhaoxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DiaryAdapter extends ArrayAdapter<Diary> {
    private int resourceId;
    public DiaryAdapter(Context context, int textViewResourceid, List<Diary> objects){
        super(context,textViewResourceid,objects);
        resourceId = textViewResourceid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Diary diary =getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
        TextView diaryName = view.findViewById(R.id.diary_name);
        diaryName.setText(diary.getName());
        return view;
    }
}
