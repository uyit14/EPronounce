package com.example.uytai.epronounce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestResultActivity extends AppCompatActivity {
    @BindView(R.id.result)
    Button btn_result;
    @BindView(R.id.btn_again)
    ImageView btn_again;
    @BindView(R.id.btn_review)
    ImageView btn_review;
    @BindView(R.id.btn_home)
    ImageView btn_home;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        ButterKnife.bind(this);
        //
        getresult();
        //
        click();
    }

    private void click() {
        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestResultActivity.this, PronouncingAActivity.class));
                finish();
            }
        });
        //
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestResultActivity.this, MainActivity.class));
                finish();
            }
        });
        //
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Function Unfinished", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getresult() {
        result = getIntent().getIntExtra(Constant.KEY_PUT_RESULT, -1);
        btn_result.setText(result + "/10");
    }
}
