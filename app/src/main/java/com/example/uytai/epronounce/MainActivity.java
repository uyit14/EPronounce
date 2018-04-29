package com.example.uytai.epronounce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }

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
    }
}
