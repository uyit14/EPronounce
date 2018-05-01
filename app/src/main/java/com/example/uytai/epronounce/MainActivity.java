package com.example.uytai.epronounce;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_resource_management)
    Button btn_resource;
    @BindView(R.id.btn_pro_a)
    Button btn_pro_a;
    @BindView(R.id.btn_pro_b)
    Button btn_pro_b;
    public static Database database;
    ArrayList<EPronounce> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();
        ClickButton();
        //tạo db
        database = new Database(MainActivity.this, "epronounce.sql", null, 1);
        //tạo table PronounceA
        database.QueryData("CREATE TABLE IF NOT EXISTS PronounceA(Id INTEGER PRIMARY KEY AUTOINCREMENT, Content VARCHAR(500))");
        addData();
    }

    //add dữ liệu mẫu
    private void addData() {
        //truy vấn lấy tất cả dữ liệu đổ vào mảng arr1
        Cursor data = MainActivity.database.getData("SELECT * FROM PronounceA");
        while (data.moveToNext()){
            int id = data.getInt(0);
            String content = data.getString(1);
            arrayList.add(new EPronounce(id, content));
        }
        // add dữ liệu mẫu khi chưa có dữ liệu
        if(arrayList.size()<21){
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'absolutely')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'after you')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'almost')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'come here')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'congratulations')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'explain to me why')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'go away')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'go for it')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'good job')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'i guess so')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'i am in a hurry')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'just kidding')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'got a minute')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'say cheese')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'my name is leo')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'as soon as possible')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'be careful')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'excellent')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'call me')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'follow me')");
            database.QueryData("INSERT INTO PronounceA VALUES(null, 'happy Birthday')");
        }

    }

    //các sự kiện click
    private void ClickButton() {
        //click on resource
        btn_resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResourceActivity.class));
            }
        });

        //click on pronounce a
        btn_pro_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PronouncingAActivity.class);
                startActivity(intent);
            }
        });

        //
        btn_pro_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Function Unfinished", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
