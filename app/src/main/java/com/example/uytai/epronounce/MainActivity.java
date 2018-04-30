package com.example.uytai.epronounce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ClickButton();
        //tạo db
        database = new Database(MainActivity.this, "epronounce.sql", null, 1);
        //tạo table PronounceA
        MainActivity.database.QueryData("CREATE TABLE IF NOT EXISTS PronounceA(Id INTEGER PRIMARY KEY AUTOINCREMENT, Content VARCHAR(500))");
        //addData();
    }

    //add dữ liệu mẫu
    private void addData() {
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Absolutely')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'After you')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Almost')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Come here')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Congratulations')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Explain to me why')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Go away')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Go for it')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Good job')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'I guess so')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'I am in a hurry')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Just kidding')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Got a minute')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Say cheese')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Are you American')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'As soon as possible')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Be careful')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Excellent')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Call me')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Follow me')");
        database.QueryData("INSERT INTO PronounceA VALUES(null, 'Happy Birthday')");
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
