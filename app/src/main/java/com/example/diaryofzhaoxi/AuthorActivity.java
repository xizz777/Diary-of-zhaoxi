package com.example.diaryofzhaoxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        EditText author = findViewById(R.id.author);
        SharedPreferences pref = getSharedPreferences("Author", MODE_PRIVATE);
        String authorname = pref.getString("author","");
        author.setText(authorname);
        Button queren = findViewById(R.id.queren);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("Author", MODE_PRIVATE).edit();
                EditText author = findViewById(R.id.author);
                editor.putString("author", String.valueOf(author.getText()));
                editor.apply();
                finish();
            }
        });
        Button tuichu = findViewById(R.id.tuichu);
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}