package com.example.diaryofzhaoxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Diary> diaryList = new ArrayList<>();
    public DatabaseHelper dbHelper;
    Context mContext =this;
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuauthor, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.zuozhe:
                Intent intent = new Intent(MainActivity.this,AuthorActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton createDatabase = findViewById(R.id.create_database);
        FloatingActionButton refresh = findViewById(R.id.refresh);
        initDiarys();
        DiaryAdapter adapter = new DiaryAdapter(MainActivity.this, R.layout.diary_item, diaryList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diary diary = diaryList.get(position);
                String title = diary.getName();
                int diaryid = diary.getId();
                Intent intent = new Intent(MainActivity.this,EditDiaryActivity.class);
                intent.putExtra("id",diaryid);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, diary.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Intent intent = new Intent(MainActivity.this, NewDiaryActivity.class);
                startActivity(intent);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                initDiarys();
                DiaryAdapter adapter = new DiaryAdapter(MainActivity.this, R.layout.diary_item, diaryList);
                listView.setAdapter(adapter);
            }
        });
    }

    private void initDiarys() {
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("Diary1", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    Diary diary = new Diary(title,id);
                    diaryList.add(diary);
                } while (cursor.moveToNext());
            }
            cursor.close();
    }
}
