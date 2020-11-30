package com.example.diaryofzhaoxi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class EditDiaryActivity extends AppCompatActivity {
    Context mContext = this;
    String imagepath = null;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.xiugai:
                SetEdit();
                break;
            case R.id.shanchu:
                DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Intent intent = getIntent();
                int id = intent.getIntExtra("id", 0);
                db.delete("Diary1", "id = ?", new String[]{String.valueOf(id)});
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void SetEdit(){
        EditText editTitle = findViewById(R.id.edit_title);
        EditText editAuthor = findViewById(R.id.edit_author);
        EditText editDate = findViewById(R.id.edit_date);
        EditText editContent = findViewById(R.id.edit_content);
        editTitle.setEnabled(true);
        editAuthor.setEnabled(true);
        editContent.setEnabled(true);
        Button buttonqueren = findViewById(R.id.queren);
        buttonqueren.setVisibility(View.VISIBLE);
        Button buttonquxiao = findViewById(R.id.quxiao);
        buttonquxiao.setVisibility(View.VISIBLE);
        Button buttontuichu = findViewById(R.id.tuichu);
        buttontuichu.setVisibility(View.GONE);
    }
    public void SetView(){
        EditText editTitle = findViewById(R.id.edit_title);
        EditText editAuthor = findViewById(R.id.edit_author);
        EditText editDate = findViewById(R.id.edit_date);
        EditText editContent = findViewById(R.id.edit_content);
        editTitle.setEnabled(false);
        editAuthor.setEnabled(false);
        editContent.setEnabled(false);
        Button buttonqueren = findViewById(R.id.queren);
        buttonqueren.setVisibility(View.GONE);
        Button buttonquxiao = findViewById(R.id.quxiao);
        buttonquxiao.setVisibility(View.GONE);
        Button buttontuichu = findViewById(R.id.tuichu);
        buttontuichu.setVisibility(View.VISIBLE);
    }
    private boolean checkDate(String s) {
        return s.matches("^\\d{4}-\\d{1,2}-\\d{1,2}");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);
        EditText editTitle = findViewById(R.id.edit_title);
        EditText editAuthor = findViewById(R.id.edit_author);
        EditText editDate = findViewById(R.id.edit_date);
        EditText editContent = findViewById(R.id.edit_content);
        Button buttonquxiao = findViewById(R.id.quxiao);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        buttonquxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetView();
            }
        });
        ImageButton buttonshow = findViewById(R.id.showpicture);
        buttonshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditDiaryActivity.this,pictureout.class);
                intent.putExtra("imagepath", imagepath);
                startActivity(intent);
            }
        });
        Button buttonqueren =findViewById(R.id.queren);
        buttonqueren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(!checkDate(editDate.getText().toString())) {
                    Toast.makeText(EditDiaryActivity.this, "日期格式错误", Toast.LENGTH_SHORT);
                }*/
                DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title", String.valueOf(editTitle.getText()));
                if(String.valueOf(editTitle.getText()).equals("")){
                    values.put("title","无标题");
                }
                values.put("content", String.valueOf(editContent.getText()));
                values.put("Date", String.valueOf(editDate.getText()));
                values.put("author", String.valueOf(editAuthor.getText()));
                db.update("Diary1", values, "id = ?", new String[]{String.valueOf(id)});
                Toast.makeText(EditDiaryActivity.this, "修改成功", Toast.LENGTH_SHORT);
                finish();
            }
        });
        Button buttontuichu = findViewById(R.id.tuichu);
        buttontuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Diary1", new String[]{"id,title,author,picture,Date,content"}, "id = ?",new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()){
            do {
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String date = cursor.getString(cursor.getColumnIndex("Date"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                imagepath = cursor.getString(cursor.getColumnIndex("picture"));
                editTitle.setText(title);
                editAuthor.setText(author);
                editDate.setText(date);
                editContent.setText(content);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }

}